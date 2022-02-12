package com.brookezb.bhs.interceptor;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.UserService;
import com.brookezb.bhs.utils.CsrfUtils;
import com.brookezb.bhs.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 动态登录拦截器，实现免登录功能
 *
 * @author brooke_zb
 */
@AllArgsConstructor
public class DynamicAuthInterceptor implements HandlerInterceptor {
    private RedisUtils redisUtils;
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        HttpSession session = request.getSession();

        // 若session中无用户信息则需动态登录
        if (session.getAttribute(AppConstants.SESSION_USER_KEY) == null && request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals(AppConstants.AUTH_TOKEN_HEADER)) {
                    login(request, response, cookie.getValue());
                }
            }
        }

        return true;
    }

    private void login(HttpServletRequest request, HttpServletResponse response, String token) {
        String uid = redisUtils.getStringValue(AppConstants.REDIS_USER_TOKEN + token);
        if (uid != null) {
            // uid存在，获取过期时间
            Long expire = redisUtils.getExpire(AppConstants.REDIS_USER_TOKEN + token);
            redisUtils.delete(AppConstants.REDIS_USER_TOKEN + token);
            Long userId = Long.valueOf(uid);

            // 判断用户是否存在以及是否被封禁
            try {
                User user = userService.selectById(userId);
                if (!user.isEnabled()) {
                    removeToken(response);
                }
            } catch (NotFoundException ex) {
                removeToken(response);
            }

            // 用户存在且未被封禁，则设置session
            request.getSession().setAttribute(AppConstants.SESSION_USER_KEY, userId);
            CsrfUtils.putToken(request, response);

            // 生成新的token
            String newToken = userService.generateAuthToken(userId, expire);
            ResponseCookie cookie = ResponseCookie.from(AppConstants.AUTH_TOKEN_HEADER, newToken)
                    .maxAge(expire)
                    .path("/")
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
        }
    }

    private void removeToken(HttpServletResponse response) {
        ResponseCookie removeToken = ResponseCookie.from(AppConstants.AUTH_TOKEN_HEADER, "")
                .path("/")
                .maxAge(0L)
                .sameSite("None")
                .secure(true)
                .build();
        response.addHeader("Set-Cookie", removeToken.toString());
    }
}
