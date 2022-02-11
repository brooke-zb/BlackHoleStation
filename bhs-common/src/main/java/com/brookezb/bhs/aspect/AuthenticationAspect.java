package com.brookezb.bhs.aspect;

import com.brookezb.bhs.annotation.PermitAll;
import com.brookezb.bhs.annotation.RequireAuth;
import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.ForbiddenException;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.UserService;
import com.brookezb.bhs.utils.ServletUtils;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 鉴权切面处理类，对鉴权类注解进行处理
 *
 * @author brooke_zb
 * @see RequireAuth
 * @see RequirePermission
 * @see PermitAll
 */
@Aspect
@Component
@AllArgsConstructor
public class AuthenticationAspect {
    private UserService userService;

    @Around("""
            @annotation(com.brookezb.bhs.annotation.RequirePermission) ||
            @annotation(com.brookezb.bhs.annotation.RequireAuth) ||
            @annotation(com.brookezb.bhs.annotation.PermitAll) ||
            @within(com.brookezb.bhs.annotation.RequirePermission) ||
            @within(com.brookezb.bhs.annotation.RequireAuth) ||
            @within(com.brookezb.bhs.annotation.PermitAll)
            """
    )
    public Object aroundRequirePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        // 优先获取方法上的注解信息
        // 处理权限鉴定
        if (requirePermission(method.getAnnotation(RequirePermission.class))) {
            return joinPoint.proceed();
        }
        // 处理登录鉴定
        if (requireAuth(method.getAnnotation(RequireAuth.class))) {
            return joinPoint.proceed();
        }
        // 处理放行
        if (permitAll(method.getAnnotation(PermitAll.class))) {
            return joinPoint.proceed();
        }

        // 方法上获取不到，获取类上的注解信息
        // 处理权限鉴定
        if (requirePermission(clazz.getAnnotation(RequirePermission.class))) {
            return joinPoint.proceed();
        }
        // 处理登录鉴定
        requireAuth(clazz.getAnnotation(RequireAuth.class));

        return joinPoint.proceed();
    }

    /**
     * 权限鉴定
     *
     * @param anno 注解
     * @return true: 通过鉴定； false: 没有该注解
     */
    public boolean requirePermission(RequirePermission anno) {
        if (anno == null) {
            return false;
        }
        String[] requirePermissions = anno.value();
        RequirePermission.Relation relation = anno.relation();

        // 获取session中的用户id
        HttpSession session = ServletUtils.getSession();
        Long uid = (Long) session.getAttribute(AppConstants.SESSION_USER_KEY);
        if (uid == null) {
            throw new AuthenticationException("请登录后再操作");
        }

        // 获取用户信息
        User currentUser = userService.selectById(uid);
        if (currentUser == null || !currentUser.isEnabled()) {
            throw new AuthenticationException("未找到用户或用户已被禁用");
        }

        // 判断权限
        if (relation == RequirePermission.Relation.OR) {
            for (String requirePermission : requirePermissions) {
                if (currentUser.getRole().getPermissions().contains(requirePermission)) {
                    return true;
                }
            }
            throw new ForbiddenException("您没有权限进行此操作");
        } else {
            for (String requirePermission : requirePermissions) {
                if (!currentUser.getRole().getPermissions().contains(requirePermission)) {
                    throw new ForbiddenException("您没有权限进行此操作");
                }
            }
            return true;
        }
    }

    /**
     * 登录鉴定
     * @param anno 注解
     * @return true: 通过鉴定； false: 没有该注解
     */
    public boolean requireAuth(RequireAuth anno) {
        if (anno == null) {
            return false;
        }

        // 获取session中的权限信息
        HttpSession session = ServletUtils.getSession();
        Long uid = (Long) session.getAttribute(AppConstants.SESSION_USER_KEY);
        if (uid == null) {
            throw new AuthenticationException("请登录后再操作");
        }
        return true;
    }

    /**
     * 放行
     * @param anno 注解
     * @return true: 放行； false: 没有该注解
     */
    public boolean permitAll(PermitAll anno) {
        return anno != null;
    }
}
