package top.brookezb.bhs.utils;

import top.brookezb.bhs.constant.AppConstants;

import javax.servlet.http.Cookie;
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
     * @param request 请求
     * @param response 响应
     */
    public static void putToken(HttpServletRequest request, HttpServletResponse response) {
        // 生成token
        String token = UUID.randomUUID().toString();

        // 将token存储到session中
        request.getSession().setAttribute(AppConstants.CSRF_HEADER, token);

        // 将token放入cookie中
        Cookie csrfToken = new Cookie(AppConstants.CSRF_HEADER, token);
        csrfToken.setPath("/");
        response.addCookie(csrfToken);
    }
}
