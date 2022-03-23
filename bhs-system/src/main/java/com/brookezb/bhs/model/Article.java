package com.brookezb.bhs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 文章表
 */
@Data
public class Article {
    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空", groups = Update.class)
    private Long aid;

    /**
     * 作者id
     */
    @Valid
    @NotNull(message = "角色不能为空", groups = Add.class)
    private User user;

    /**
     * 分类id
     */
    @Valid
    @NotNull(message = "分类不能为空", groups = Add.class)
    private Category category;

    /**
     * 文章标签
     */
    private List<Tag> tags = new ArrayList<>();

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空", groups = Add.class)
    @Length(message = "标题长度不符合要求(1 - 64)", min = 1, max = 64, groups = {Add.class, Update.class})
    private String title;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空", groups = Add.class)
    @Length(message = "内容长度不能少于1个字", min = 1, groups = {Add.class, Update.class})
    private String content;

    /**
     * 开启评论
     */
    private boolean commentabled = true;

    /**
     * 开启赞赏
     */
    private boolean appreciatabled = true;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;

    /**
     * 草稿状态
     */
    private Status status;

    /**
     * 浏览量
     */
    private Integer views;

    public enum Status {
        PUBLISHED, DRAFT, INVISIBLE
    }

    public interface Add extends Default {}
    public interface Update extends Default {}
}