package com.brookezb.bhs.model;

import com.brookezb.bhs.validation.RoleGroup;
import com.brookezb.bhs.validation.UserGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 角色表
 */
@Data
public class Role {
    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空", groups = {UserGroup.Add.class, RoleGroup.Update.class})
    private Long rid;

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空", groups = {RoleGroup.Add.class, RoleGroup.Update.class})
    private String name;

    /**
     * 拥有权限
     */
    @NotNull(message = "角色权限不能为空", groups = {RoleGroup.Add.class, RoleGroup.Update.class})
    private Set<String> permissions;
}