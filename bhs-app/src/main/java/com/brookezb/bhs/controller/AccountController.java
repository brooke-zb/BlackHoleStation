package com.brookezb.bhs.controller;

import com.brookezb.bhs.annotation.RequireAuth;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.entity.LoginBody;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.entity.UpdatePasswordBody;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.UserService;
import com.brookezb.bhs.utils.CookieUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            ResponseCookie auth_token = CookieUtils.from(AppConstants.AUTH_TOKEN_HEADER, token)
                    .httpOnly(true)
                    .maxAge(60 * 60 * 24 * 7L)
                    .build();
            response.addHeader("Set-Cookie", auth_token.toString());
        }

        // 设置CSRF token
        String token = UUID.randomUUID().toString();
        session.setAttribute(AppConstants.CSRF_HEADER, token);

        ResponseCookie token_cookie = CookieUtils.from(AppConstants.CSRF_HEADER, token).build();
        response.addHeader("Set-Cookie", token_cookie.toString());

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
        ResponseCookie removeAuthToken = CookieUtils.from(AppConstants.AUTH_TOKEN_HEADER, "")
                .maxAge(0L)
                .build();
        response.addHeader("Set-Cookie", removeAuthToken.toString());

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
    @GetMapping("")
    public R<?> getUserInfo(@SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid) {
        if (uid == null) {
            return R.fail("请先登录后再操作");
        }
        try {
            User user = userService.selectById(uid);
            if (!user.isEnabled()) {
                throw new AuthenticationException("该账号已被禁用");
            }
            return R.success(user);
        }
        catch (NotFoundException ex) {
            throw new AuthenticationException("没有找到该账号");
        }
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

    /**
     * 更新用户密码
     *
     * @param uid  用户id
     * @param body 密码信息
     * @return 更新结果
     */
    @RequireAuth
    @PatchMapping("/password")
    public R<?> updatePassword(@SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid, @RequestBody @Validated UpdatePasswordBody body) {
        userService.updatePassword(uid, body.getOldPassword(), body.getNewPassword());
        return R.success(null, "密码更新成功");
    }
}
