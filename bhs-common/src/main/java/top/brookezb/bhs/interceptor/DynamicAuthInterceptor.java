package top.brookezb.bhs.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.model.User;
import top.brookezb.bhs.service.UserService;
import top.brookezb.bhs.utils.RedisUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

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
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        HttpSession session = request.getSession();
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
            // 获取过期时间
            Long expire = redisUtils.getExpire(AppConstants.REDIS_USER_TOKEN + token);
            redisUtils.delete(AppConstants.REDIS_USER_TOKEN + token);
            Long userId = Long.valueOf(uid);

            // 判断用户是否存在
            User user = userService.selectById(userId);
            if (user == null) {
                // 用户不存在则删除cookie
                Cookie removeToken = new Cookie(AppConstants.AUTH_TOKEN_HEADER, "");
                removeToken.setPath("/");
                removeToken.setMaxAge(0);
                response.addCookie(removeToken);
                return;
            }
            request.getSession().setAttribute(AppConstants.SESSION_USER_KEY, userId);
            putCSRFToken(request, response);

            // 生成新的token
            String newToken = userService.generateAuthToken(userId, expire);
            Cookie cookie = new Cookie(AppConstants.AUTH_TOKEN_HEADER, newToken);
            cookie.setMaxAge(expire.intValue());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
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
