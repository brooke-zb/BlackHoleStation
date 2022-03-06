package com.brookezb.bhs.service;

import cn.hutool.extra.servlet.ServletUtil;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.ArticleMapper;
import com.brookezb.bhs.mapper.CommentMapper;
import com.brookezb.bhs.model.Comment;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.utils.IdUtils;
import com.brookezb.bhs.utils.PageUtils;
import com.brookezb.bhs.utils.ServletUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    private ArticleMapper articleMapper;
    private MailService mailService;

    @Override
    public Comment selectById(Long coid, boolean isAdmin) {
        Comment comment = commentMapper.selectById(coid, isAdmin);
        if (comment == null) {
            throw new NotFoundException("评论不存在");
        }
        return comment;
    }

    @Override
    public List<Comment> selectAll(Long aid, String ip, Comment.Status status) {
        return commentMapper.selectAll(aid, ip, status);
    }

    @Override
    public List<Comment> selectAllByArticleId(Long aid) {
        var ids = commentMapper.selectAllByArticleId(aid);
        if (articleMapper.verifyArticle(aid) == null) {
            throw new NotFoundException("文章不存在");
        }
        if (ids.isEmpty()) {
            return List.of();
        }
        return PageUtils.selectPage(ids, commentMapper::selectAllByIdList, Comment.class);
    }

    @Override
    public List<Comment> selectByNickname(String name) {
        return commentMapper.selectAllByNickname(name);
    }

    @Override
    public List<Comment> selectByIp(String ip) {
        return commentMapper.selectAllByIp(ip);
    }

    @Override
    @Transactional
    public boolean insert(Comment comment) {
        // 查询文章是否存在
        if (articleMapper.verifyArticle(comment.getAid()) == null) {
            throw new InvalidException("评论文章不存在");
        }

        // 非顶级评论处理
        if (comment.getReply() != null) {
            Comment reply = commentMapper.selectById(comment.getReply(), true);

            // 回复评论不存在或非审核通过
            if (reply == null || reply.getStatus() != Comment.Status.PUBLISHED || !reply.getAid().equals(comment.getAid())) {
                throw new InvalidException("回复的评论不存在");
            }
            comment.setReplyname(reply.getNickname());

            // 评论层级判断
            if (reply.getParent() == null) {
                comment.setParent(reply.getCoid());
                comment.setReply(null);
            } else {
                comment.setParent(reply.getParent());
                comment.setReply(reply.getCoid());
            }
        }

        // 检查评论者邮箱是否受信任
        if (comment.getUid() == null) {
            if (comment.getEmail() == null || commentMapper.selectTrustEmail(comment.getEmail()) == null) {
                comment.setStatus(Comment.Status.PENDING);
            } else {
                comment.setStatus(Comment.Status.PUBLISHED);
            }
        } else {
            comment.setStatus(Comment.Status.PUBLISHED);
        }

        // 生成评论id
        comment.setCoid(IdUtils.nextId());

        // 插入ip
        comment.setIp(ServletUtil.getClientIP(ServletUtils.getRequest()));

        commentMapper.insert(comment);

        // 发送通知邮件
        User user = articleMapper.selectUserById(comment.getAid());
        if (comment.getStatus() == Comment.Status.PUBLISHED) {
            mailService.sendReplyMail(user.getMail(), comment.getNickname(), "https://blog.brooke-zb.top/article/" + comment.getAid() + "/" + comment.getCoid());
        } else {
            mailService.sendAuditMail("https://blog.brooke-zb.top/admin/comment");
        }

        // 返回审核状态
        return comment.getStatus() == Comment.Status.PUBLISHED;
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        if (commentMapper.verifyComment(comment.getCoid()) == null) {
            throw new NotFoundException("评论不存在");
        }
        commentMapper.update(comment);
    }

    @Override
    @Transactional
    public void updateStatus(Long coid, Comment.Status status) {
        if (commentMapper.verifyComment(coid) == null) {
            throw new NotFoundException("评论不存在");
        }
        commentMapper.updateStatus(coid, status);
    }

    @Override
    public void updateStatusList(List<Long> coids, Comment.Status status) {
        if (coids.isEmpty()) {
            return;
        }
        commentMapper.updateStatusList(coids, status);
    }

    @Override
    @Transactional
    public void delete(Long coid) {
        if (commentMapper.verifyComment(coid) == null) {
            throw new NotFoundException("评论不存在");
        }
        commentMapper.delete(coid);
    }

    @Override
    public int deleteList(List<Long> coids) {
        if (coids.isEmpty()) {
            return 0;
        }
        return commentMapper.deleteList(coids);
    }
}
