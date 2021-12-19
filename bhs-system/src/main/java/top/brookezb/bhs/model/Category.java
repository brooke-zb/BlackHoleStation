package top.brookezb.bhs.model;

import lombok.Data;

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
}