package top.brookezb.bhs.aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.brookezb.bhs.annotation.*;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.constant.PermissionConstants;
import top.brookezb.bhs.model.Article;
import top.brookezb.bhs.model.User;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.exception.ForbiddenException;
import top.brookezb.bhs.service.ArticleService;
import top.brookezb.bhs.service.UserService;
import top.brookezb.bhs.utils.ServletUtils;

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
    private ArticleService articleService;

    @Around("""
            @annotation(top.brookezb.bhs.annotation.RequirePermission) ||
            @annotation(top.brookezb.bhs.annotation.RequireAuth) ||
            @annotation(top.brookezb.bhs.annotation.PermitAll) ||
            @annotation(top.brookezb.bhs.annotation.RequireAuthor) ||
            @within(top.brookezb.bhs.annotation.RequirePermission) ||
            @within(top.brookezb.bhs.annotation.RequireAuth) ||
            @within(top.brookezb.bhs.annotation.PermitAll) ||
            @within(top.brookezb.bhs.annotation.RequireAuthor)
            """
    )
    public Object aroundRequirePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        // 优先获取方法上的注解信息
        // 处理作者检查
        boolean requireSuperuser = requireAuthor(method.getAnnotation(RequireAuthor.class), joinPoint);
        // 处理权限鉴定
        if (requirePermission(method.getAnnotation(RequirePermission.class), requireSuperuser)) {
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
        // 处理作者检查
        if (!requireSuperuser) {
            requireSuperuser = requireAuthor(clazz.getAnnotation(RequireAuthor.class), joinPoint);
        }
        // 处理权限鉴定
        if (requirePermission(clazz.getAnnotation(RequirePermission.class), requireSuperuser)) {
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
    public boolean requirePermission(RequirePermission anno, boolean requireSuperuser) {
        if (anno == null) {
            return false;
        }
        String[] requirePermissions = anno.values();
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
        if (requireSuperuser && !currentUser.getRole().getPermissions().contains(PermissionConstants.SUPERUSER)) {
            throw new ForbiddenException("您没有权限进行此操作");
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

    public boolean permitAll(PermitAll anno) {
        return anno != null;
    }

    public boolean requireAuthor(RequireAuthor anno, ProceedingJoinPoint joinPoint) {
        if (anno == null) {
            return false;
        }

        // 获取session中的权限信息
        HttpSession session = ServletUtils.getSession();
        Long uid = (Long) session.getAttribute(AppConstants.SESSION_USER_KEY);
        if (uid == null) {
            throw new AuthenticationException("请登录后再操作");
        }

        // 获取文章信息
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Article article) {
                // 如果不是文章作者则需要管理员权限
                if (!articleService.selectById(article.getAid(), false, false).getUid().equals(uid)) {
                    return true;
                }
            }
        }

        return false;
    }
}
