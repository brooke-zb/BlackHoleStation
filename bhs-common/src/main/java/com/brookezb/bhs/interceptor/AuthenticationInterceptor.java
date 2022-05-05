package com.brookezb.bhs.interceptor;

import com.brookezb.bhs.annotation.PermitAll;
import com.brookezb.bhs.annotation.RequireAuth;
import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.ForbiddenException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 鉴权拦截器，对鉴权类注解进行处理
 *
 * @author brooke_zb
 * @see RequireAuth
 * @see RequirePermission
 * @see PermitAll
 */
@Slf4j
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private UserService userService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (handler instanceof HandlerMethod method) {
            Class<?> clazz = method.getBeanType();

            // 获取方法上的注解
            RequireAuth requireAuth = method.getMethodAnnotation(RequireAuth.class);
            RequirePermission requirePermission = method.getMethodAnnotation(RequirePermission.class);
            PermitAll permitAll = method.getMethodAnnotation(PermitAll.class);

            // 若方法上没有注解，尝试获取类上的注解
            if (requireAuth == null && requirePermission == null && permitAll == null) {
                requireAuth = clazz.getAnnotation(RequireAuth.class);
                requirePermission = clazz.getAnnotation(RequirePermission.class);
                permitAll = clazz.getAnnotation(PermitAll.class);
            }

            // 处理权限鉴定
            if (requirePermission != null && !checkPermission(request.getSession(), requirePermission)) {
                throw new ForbiddenException("您没有权限进行此操作");
            }
            // 处理登录鉴定
            else if (requireAuth != null && !checkAuth(request.getSession())) {
                throw new AuthenticationException("请登录后再操作");
            }
            // 处理放行
            else if (permitAll != null) {
                return true;
            }
        }
        return true;
    }

    private boolean checkAuth(HttpSession session) {
        getUserFromSession(session);
        return true;
    }

    private boolean checkPermission(HttpSession session, RequirePermission anno) {
        String[] requirePermissions = anno.value();
        RequirePermission.Relation relation = anno.relation();

        // 获取用户信息
        User currentUser = getUserFromSession(session);

        // 判断权限
        if (relation == RequirePermission.Relation.OR) {
            for (String requirePermission : requirePermissions) {
                if (currentUser.getRole().getPermissions().contains(requirePermission)) {
                    return true;
                }
            }
            return false;
        } else {
            for (String requirePermission : requirePermissions) {
                if (!currentUser.getRole().getPermissions().contains(requirePermission)) {
                    return false;
                }
            }
            return true;
        }
    }

    private User getUserFromSession(HttpSession session) {
        Long uid = (Long) session.getAttribute(AppConstants.SESSION_USER_KEY);
        if (uid == null) {
            throw new AuthenticationException("请登录后再操作");
        }
        try {
            User currentUser = userService.selectById(uid);
            if (!currentUser.isEnabled()) {
                removeLoginStatus(session);
                throw new AuthenticationException("用户已被禁用");
            }
            return currentUser;
        } catch (NotFoundException ex) {
            removeLoginStatus(session);
            throw new AuthenticationException("用户不存在");
        }
    }

    private void removeLoginStatus(HttpSession session) {
        session.removeAttribute(AppConstants.SESSION_USER_KEY);
    }
}
