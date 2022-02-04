package com.brookezb.bhs.interceptor;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.ForbiddenException;
import com.brookezb.bhs.utils.CsrfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        // 未登录则返回401
        HttpSession session = request.getSession();
        if (session.getAttribute(AppConstants.SESSION_USER_KEY) == null) {
            throw new AuthenticationException("请登录后再操作");
        }

        // 获取服务端token
        Object sessionToken = session.getAttribute(AppConstants.CSRF_HEADER);

        // 校验失败则返回403
        if (sessionToken == null || !sessionToken.equals(token)) {
            CsrfUtils.putToken(request, response);
            throw new ForbiddenException("未通过CSRF检测");
        }

        return true;
    }
}
