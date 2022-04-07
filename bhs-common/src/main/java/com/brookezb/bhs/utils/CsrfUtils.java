package com.brookezb.bhs.utils;

import com.brookezb.bhs.constant.AppConstants;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * CSRF防御工具类
 *
 * @author brooke_zb
 */
public class CsrfUtils {
    /**
     * 设置新的CSRF token
     *
     * @param request  请求
     * @param response 响应
     */
    public static void generateToken(HttpServletRequest request, HttpServletResponse response) {
        // 生成token
        String token = UUID.randomUUID().toString();

        // 将token存储到session中
        request.getSession().setAttribute(AppConstants.CSRF_HEADER, token);

        // 将token放入cookie中
        ResponseCookie csrfToken = CookieUtils.from(AppConstants.CSRF_HEADER, token).build();
        response.addHeader("Set-Cookie", csrfToken.toString());
    }
}
