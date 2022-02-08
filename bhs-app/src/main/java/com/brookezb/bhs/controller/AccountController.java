package com.brookezb.bhs.controller;

import com.brookezb.bhs.annotation.RequireAuth;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.entity.UpdatePasswordBody;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.model.User;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.brookezb.bhs.entity.LoginBody;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 登录、退出登录接口
 *
 * @author brooke_zb
 */
@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private UserService userService;

    /**
     * 登录接口
     *
     * @param loginBody 用户信息
     * @return 登录结果
     */
    @PostMapping("/token")
    public R<?> login(@RequestBody @Validated LoginBody loginBody, HttpSession session, HttpServletResponse response) {
        // 获取用户
        User user = userService.checkUser(loginBody.getUsername(), loginBody.getPassword());
        session.setAttribute(AppConstants.SESSION_USER_KEY, user.getUid());

        // 设置免登录token
        if (loginBody.isRememberMe()) {
            String token = userService.generateAuthToken(user.getUid(), 60 * 60 * 24 * 7L);
            Cookie auth_token = new Cookie(AppConstants.AUTH_TOKEN_HEADER, token);
            auth_token.setMaxAge(60 * 60 * 24 * 7);
            auth_token.setPath("/");
            auth_token.setHttpOnly(true);
            response.addCookie(auth_token);
        }

        // 设置CSRF token
        String token = UUID.randomUUID().toString();
        session.setAttribute(AppConstants.CSRF_HEADER, token);

        Cookie token_cookie = new Cookie(AppConstants.CSRF_HEADER, token);
        token_cookie.setPath("/");
        response.addCookie(token_cookie);

        return R.success(null, "登录成功");
    }

    /**
     * 退出登录接口
     *
     * @return 登出结果
     */
    @RequireAuth
    @DeleteMapping("/token")
    public R<?> logout(HttpSession session, HttpServletResponse response, @CookieValue(value = AppConstants.AUTH_TOKEN_HEADER, required = false) String authToken) {
        session.removeAttribute(AppConstants.SESSION_USER_KEY);

        // 删除免登录token
        Cookie removeAuthToken = new Cookie(AppConstants.AUTH_TOKEN_HEADER, "");
        removeAuthToken.setMaxAge(0);
        removeAuthToken.setPath("/");
        response.addCookie(removeAuthToken);

        if (authToken != null) {
            userService.removeAuthToken(authToken);
        }

        return R.success(null, "成功退出登录");
    }

    /**
     * 获取账号的用户信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    @RequireAuth
    @GetMapping("")
    public R<?> getUserInfo(@SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid) {
        if (uid == null) {
            throw new AuthenticationException("请先登录后再操作");
        }
        User user = userService.selectById(uid);
        if (user == null || !user.isEnabled()) {
            throw new AuthenticationException("没有找到该账号或账号已被禁用");
        }
        return R.success(user);
    }

    /**
     * 更新用户信息
     *
     * @param uid  用户id
     * @param user 用户信息
     * @return 更新结果
     */
    @RequireAuth
    @PutMapping("")
    public R<?> updateUserInfo(@SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid, @RequestBody User user) {
        user.setUid(uid);
        user.setRole(null);
        userService.update(user);
        return R.success(null, "信息更新成功");
    }

    @RequireAuth
    @PatchMapping("/password")
    public R<?> updatePassword(@SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid, @RequestBody @Validated UpdatePasswordBody body) {
        userService.updatePassword(uid, body.getOldPassword(), body.getNewPassword());
        return R.success(null, "密码更新成功");
    }
}
