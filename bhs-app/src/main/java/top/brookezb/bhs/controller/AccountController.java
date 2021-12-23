package top.brookezb.bhs.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.entity.LoginBody;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.model.User;
import top.brookezb.bhs.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 登录、注册、退出登录接口
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
    public R<?> login(@RequestBody LoginBody loginBody, HttpSession session, HttpServletResponse response) {
        // 获取用户
        User user = userService.checkUser(loginBody.getUsername(), loginBody.getPassword());
        session.setAttribute("user", user);

        // 设置免登录token
        if (loginBody.isRememberMe()) {
            String token = userService.generateAuthToken(user.getUid());
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
    @DeleteMapping("/token")
    public R<?> logout(HttpSession session, HttpServletResponse response, @CookieValue(value = AppConstants.AUTH_TOKEN_HEADER, required = false) String authToken) {
        session.removeAttribute("user");

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
}