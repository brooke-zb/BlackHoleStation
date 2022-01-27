package top.brookezb.bhs.model;

import lombok.Data;

import java.util.List;

/**
 * 分类表
 */
@Data
public class Category {
    /**
     * 分类id
     */
    private Long cid;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父类id
     */
    private Long parent;

    /**
     * 子分类
     */
    private List<Category> children;
}