package top.brookezb.bhs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * 角色名
     */
    private String name;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 主页链接
     */
    private String link;
}