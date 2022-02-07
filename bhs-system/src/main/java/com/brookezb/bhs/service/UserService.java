package com.brookezb.bhs.service;

import com.brookezb.bhs.model.User;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 用户服务
 *
 * @author brooke_zb
 */
public interface UserService {
    /**
     * 根据用户名和密码获取用户
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @NonNull
    User checkUser(String username, String password);

    /**
     * 根据用户id获取用户
     * @param uid 用户id
     * @return 用户信息
     */
    User selectById(Long uid);

    /**
     * 获取用户列表
     * @param username 用户名
     * @param enabled 用户状态
     * @return 用户列表
     */
    List<User> selectAll(String username, Boolean enabled);

    /**
     * 生成免登录token
     * @param uid 用户id
     * @param expire 过期时间，单位秒
     * @return token
     */
    String generateAuthToken(Long uid, Long expire);

    /**
     * 移除用户免登录token
     * @param token token
     */
    void removeAuthToken(String token);

    void insert(User user);

    void update(User user);

    void updatePassword(Long uid, String oldPassword, String newPassword);

    void updateStatus(Long uid, Boolean enabled);

    void delete(Long uid);
}
