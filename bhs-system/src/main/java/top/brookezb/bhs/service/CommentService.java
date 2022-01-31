package top.brookezb.bhs.service;

import top.brookezb.bhs.model.Comment;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface CommentService {
    Comment selectById(Long coid);

    List<Comment> selectAll(Comment.Status status);

    List<Comment> selectAllByArticleId(Long aid);

    List<Comment> selectByNickname(String name);

    List<Comment> selectByIp(String ip);

    void insert(Comment comment, boolean verify);

    void update(Comment comment);

    void updateStatus(Comment comment);

    void delete(Long coid);

    int deleteList(List<Long> coids);
}
