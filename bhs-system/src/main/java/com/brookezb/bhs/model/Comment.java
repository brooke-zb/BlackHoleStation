package com.brookezb.bhs.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 评论表
 */
@Data
public class Comment {
    /**
     * 评论id
     */
    @NotNull(message = "评论id不能为空", groups = Update.class)
    private Long coid;

    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空", groups = Add.class)
    private Long aid;

    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空", groups = Add.class)
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像（邮箱hash）
     */
    private String avatar;

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
    @NotNull(message = "内容不能为空", groups = Add.class)
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

    public interface Add extends Default {}
    public interface Update extends Default {}
}