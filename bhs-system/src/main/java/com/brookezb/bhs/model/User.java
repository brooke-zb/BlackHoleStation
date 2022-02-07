package com.brookezb.bhs.model;

import com.brookezb.bhs.validation.UserGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户表
 */
@Data
public class User {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = UserGroup.Update.class)
    private Long uid;

    /**
     * 角色
     */
    @Valid
    @NotNull(message = "角色不能为空", groups = UserGroup.Add.class)
    private Role role;

    /**
     * 用户名
     */
    @NotNull(message = "用户名称不能为空", groups = UserGroup.Add.class)
    private String name;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空", groups = UserGroup.Add.class)
    @JsonIgnoreProperties(allowSetters = true)
    private String password;

    /**
     * 邮箱
     */
    @NotNull(message = "用户邮箱不能为空", groups = UserGroup.Add.class)
    private String mail;

    /**
     * 头像（邮箱hash）
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatar;

    /**
     * 主页链接
     */
    private String link;

    /**
     * 是否启用
     */
    private boolean enabled;
}