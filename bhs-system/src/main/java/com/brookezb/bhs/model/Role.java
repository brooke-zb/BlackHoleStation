package com.brookezb.bhs.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * 角色表
 */
@Data
public class Role {
    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空", groups = {User.Add.class, Update.class})
    private Long rid;

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空", groups = {Add.class})
    private String name;

    /**
     * 拥有权限
     */
    @NotNull(message = "角色权限不能为空", groups = {Add.class, Update.class})
    private Set<String> permissions;

    public interface Add extends Default {}
    public interface Update extends Default {}
}