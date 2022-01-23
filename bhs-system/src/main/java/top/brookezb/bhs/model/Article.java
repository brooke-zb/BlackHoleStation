package top.brookezb.bhs.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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
     * 文章标签
     */
    private List<String> tags;

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
     * 草稿状态
     */
    private Status status;

    /**
     * 文章密码
     */
    private String password;

    /**
     * 浏览量
     */
    private Integer views;

    public enum Status {
        PUBLISHED, DRAFT, INVISIBLE,
    }
}