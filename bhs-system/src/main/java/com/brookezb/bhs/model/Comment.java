package com.brookezb.bhs.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
     * 评论者id
     */
    private Long uid;

    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空", groups = Add.class)
    @Length(message = "昵称长度不符合要求(1 - 32)", min = 1, max = 32, groups = {Add.class, Update.class})
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
    @Length(message = "内容长度不符合要求(1 - 1000)", min = 1, max = 1000, groups = {Add.class, Update.class})
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> children;

    /**
     * 回复评论id
     */
    private Long reply;

    /**
     * 回复评论昵称
     */
    private String replyname;

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