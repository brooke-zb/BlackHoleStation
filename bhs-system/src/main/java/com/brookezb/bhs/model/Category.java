package com.brookezb.bhs.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

/**
 * 分类表
 */
@Data
public class Category {
    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空", groups = Update.class)
    private Long cid;

    /**
     * 分类名称
     */
    @NotNull(message = "分类名称不能为空", groups = {Add.class, Update.class})
    private String name;

    /**
     * 父类id
     */
    private Long parent;

    /**
     * 子分类
     */
    private List<Category> children;

    public interface Add extends Default {}
    public interface Update extends Default {}
}