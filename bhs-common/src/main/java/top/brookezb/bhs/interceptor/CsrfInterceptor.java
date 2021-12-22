package top.brookezb.bhs.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

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
        // 获取前端提交的token
        String token = request.getHeader("X-CSRF-TOKEN");

        // 未登录或无token则返回403
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || token == null) {
            setFailResponse(response);
            return false;
        }

        // 获取服务端存储的token
        String sessionToken = (String) session.getAttribute("X-CSRF-TOKEN");
        if (sessionToken != null && sessionToken.equals(token)) {
            putCSRFToken(request, response);
            return true;
        }

        setFailResponse(response);
        putCSRFToken(request, response);
        return false;
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
        request.getSession().setAttribute("X-CSRF-TOKEN", token);

        // 将token放入cookie中
        Cookie token_cookie = new Cookie("X-CSRF-TOKEN", token);
        token_cookie.setPath("/");
        response.addCookie(token_cookie);
    }

    /**
     * CSRF校检不通过，返回403
     * @param response 响应
     * @throws Exception IO流异常
     */
    private void setFailResponse(HttpServletResponse response) throws Exception {
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\": false, \"data\": null,\"msg\": \"未通过CSRF检测\"}");
    }
}
