package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Comment;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface CommentService {
    Comment selectById(Long coid, boolean isAdmin);

    List<Comment> selectAll(Long aid, String ip, Comment.Status status);

    List<Comment> selectAllByArticleId(Long aid);

    List<Comment> selectByNickname(String name);

    List<Comment> selectByIp(String ip);

    boolean insert(Comment comment, boolean verify);

    void update(Comment comment);

    void updateStatus(Long coid, Comment.Status status);

    void updateStatusList(List<Long> coids, Comment.Status status);

    void delete(Long coid);

    int deleteList(List<Long> coids);
}
