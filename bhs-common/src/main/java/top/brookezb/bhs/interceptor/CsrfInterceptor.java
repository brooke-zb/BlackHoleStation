package top.brookezb.bhs.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.exception.ForbiddenException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 防止CSRF攻击
 *
 * @author brooke_zb
 */
@Slf4j
public class CsrfInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 不拦截GET和OPTIONS请求
        if (request.getMethod().equals("GET") || request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 获取前端提交的token
        String token = request.getHeader(AppConstants.CSRF_HEADER);

        // 未登录或无token则返回403
        HttpSession session = request.getSession();
        if (session.getAttribute(AppConstants.SESSION_USER_KEY) == null || token == null) {
            throw new ForbiddenException("未通过CSRF检测");
        }

        // 获取服务端存储的token
        String sessionToken = (String) session.getAttribute(AppConstants.CSRF_HEADER);
        if (sessionToken != null && sessionToken.equals(token)) {
            putCSRFToken(request, response);
            return true;
        }

        putCSRFToken(request, response);
        throw new ForbiddenException("未通过CSRF检测");
    }

    /**
     * 设置新的CSRF token
     * @param request 请求
     * @param response 响应
     */
    private void putCSRFToken(HttpServletRequest request, HttpServletResponse response) {
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
