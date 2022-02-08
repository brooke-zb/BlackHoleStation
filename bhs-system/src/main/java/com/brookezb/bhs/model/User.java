package com.brookezb.bhs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 用户表
 */
@Data
@JsonIgnoreProperties(value = "password", allowSetters = true)
public class User {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = Update.class)
    private Long uid;

    /**
     * 角色
     */
    @Valid
    @NotNull(message = "角色不能为空", groups = Add.class)
    private Role role;

    /**
     * 用户名
     */
    @NotNull(message = "用户名称不能为空", groups = Add.class)
    private String name;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空", groups = Add.class)
    private String password;

    /**
     * 邮箱
     */
    @NotNull(message = "用户邮箱不能为空", groups = Add.class)
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

    public interface Add extends Default {}
    public interface Update extends Default {}
}