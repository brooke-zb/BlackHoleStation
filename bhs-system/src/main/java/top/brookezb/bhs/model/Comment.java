package top.brookezb.bhs.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 评论表
 */
@Data
public class Comment {
    /**
     * 评论id
     */
    private Long coid;

    /**
     * 文章id
     */
    private Long aid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 网址
     */
    private String site;

    /**
     * IP
     */
    private String ip;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private LocalDateTime created;

    /**
     * 评论状态
     */
    private Status status;

    /**
     * 父级评论id
     */
    private Long parent;

    /**
     * 子级评论
     */
    private List<Comment> children;

    /**
     * 回复评论id
     */
    private Long reply;

    public enum Status {
        /**
         * 已发布
         */
        PUBLISHED,
        /**
         * 待审核
         */
        PENDING,
        /**
         * 不可见
         */
        INVISIBLE
    }
}