package com.brookezb.bhs.service;

import cn.hutool.extra.servlet.ServletUtil;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.ArticleMapper;
import com.brookezb.bhs.mapper.CommentMapper;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.utils.IdUtils;
import com.brookezb.bhs.utils.ServletUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brookezb.bhs.model.Comment;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    private ArticleMapper articleMapper;
    private MailService mailService;

    @Override
    public Comment selectById(Long coid) {
        Comment comment = commentMapper.selectById(coid);
        if (comment == null) {
            throw new NotFoundException("评论不存在");
        }
        return comment;
    }

    @Override
    public List<Comment> selectAll(Comment.Status status) {
        return commentMapper.selectAll(status);
    }

    @Override
    @Cacheable(key = "'aid_' + #aid")
    public List<Comment> selectAllByArticleId(Long aid) {
        var ids = commentMapper.selectAllByArticleId(aid);
        if (ids.isEmpty()) {
            return List.of();
        }
        return commentMapper.selectAllByIdList(ids);
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
    @CacheEvict(key = "'aid_' + #comment.getAid()", condition = "#withPend == false")
    public void insert(Comment comment, boolean withPend) {
        // 查询文章是否存在
        if (articleMapper.verifyArticle(comment.getAid()) == null) {
            throw new InvalidException("评论文章不存在");
        }

        // 查询父评论是否存在
        if (comment.getParent() != null && commentMapper.verifyComment(comment.getParent()) == null) {
            throw new InvalidException("父评论不存在");
        }

        // 检查评论者邮箱是否受信任
        if (withPend) {
            if (commentMapper.selectTrustEmail(comment.getEmail()) == null) {
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
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(Comment comment) {
        if (commentMapper.verifyComment(comment.getCoid()) == null) {
            throw new NotFoundException("评论不存在");
        }
        commentMapper.update(comment);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void updateStatus(Comment comment) {
        if (commentMapper.verifyComment(comment.getCoid()) == null) {
            throw new NotFoundException("评论不存在");
        }
        commentMapper.updateStatus(comment.getCoid(), comment.getStatus());
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
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
