package top.brookezb.bhs.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 文章表
 */
@Data
public class Article {
    /**
     * 文章id
     */
    private Long aid;

    /**
     * 作者id
     */
    private Long uid;

    /**
     * 分类id
     */
    private Category category;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 头图
     */
    private String picture;

    /**
     * 内容
     */
    private String content;

    /**
     * 开启评论
     */
    private Boolean commentabled;

    /**
     * 开启赞赏
     */
    private Boolean appreciatabled;

    /**
     * 发布时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;

    /**
     * 文章状态
     */
    private Boolean enabled;
}