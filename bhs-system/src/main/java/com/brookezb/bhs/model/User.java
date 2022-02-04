package com.brookezb.bhs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 用户表
 */
@Data
public class User {
    /**
     * 用户id
     */
    private Long uid;

    /**
     * 角色
     */
    private Role role;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    @JsonIgnoreProperties(allowSetters = true)
    private String password;

    /**
     * 邮箱
     */
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