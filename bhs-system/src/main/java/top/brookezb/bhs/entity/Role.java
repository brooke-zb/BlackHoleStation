package top.brookezb.bhs.entity;

import lombok.Data;

import java.util.Set;

/**
 * 角色表
 */
@Data
public class Role {
    /**
     * 角色id
     */
    private Long rid;

    /**
     * 角色名
     */
    private String name;

    /**
     * 拥有权限
     */
    private Set<String> permissions;
}