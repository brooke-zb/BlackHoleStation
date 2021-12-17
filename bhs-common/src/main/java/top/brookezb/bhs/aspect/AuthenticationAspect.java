package top.brookezb.bhs.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.brookezb.bhs.annotation.RequireAuth;
import top.brookezb.bhs.annotation.RequirePermission;
import top.brookezb.bhs.entity.User;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.exception.ForbiddenException;
import top.brookezb.bhs.utils.ServletUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 鉴权切面类，对 {@code RequireAuth} 和 {@code RequirePermission} 注解进行处理
 *
 * @see RequireAuth
 * @see RequirePermission
 * @author brooke_zb
 */
@Aspect
@Component
public class AuthenticationAspect {
    /**
     * 权限鉴定切点
     */
    @Pointcut("@annotation(top.brookezb.bhs.annotation.RequirePermission)")
    public void requirePermission() {
    }

    @Around("requirePermission()")
    public Object aroundRequirePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 优先获取方法上的注解信息
        RequirePermission requirePermissionAnno = method.getAnnotation(RequirePermission.class);

        // 方法上获取不到，获取类上的注解信息
        if (requirePermissionAnno == null) {
            requirePermissionAnno = method.getDeclaringClass().getAnnotation(RequirePermission.class);
        }
        String[] requirePermissions = requirePermissionAnno.values();
        RequirePermission.Relation relation = requirePermissionAnno.relation();

        // 获取session中的权限信息
        HttpSession session = ServletUtils.getSession();
        User cur_user = (User) session.getAttribute("loginUser");
        if (cur_user == null) {
            throw new AuthenticationException("请登录后再操作");
        }

        // 判断权限
        if (relation == RequirePermission.Relation.OR) {
            for (String requirePermission : requirePermissions) {
                if (cur_user.getRole().getPermissions().contains(requirePermission)) {
                    return joinPoint.proceed();
                }
            }
            throw new ForbiddenException("您没有权限进行此操作");
        }
        else {
            for (String requirePermission : requirePermissions) {
                if (!cur_user.getRole().getPermissions().contains(requirePermission)) {
                    throw new ForbiddenException("您没有权限进行此操作");
                }
            }
            return joinPoint.proceed();
        }
    }

    /**
     * 登录鉴定切点
     */
    @Pointcut("@annotation(top.brookezb.bhs.annotation.RequireAuth)")
    public void requireAuth() {
    }

    @Around("requireAuth()")
    public Object aroundRequireAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取session中的用户信息
        HttpSession session = ServletUtils.getSession();
        if (session.getAttribute("loginUser") == null) {
            throw new AuthenticationException("请登录后再操作");
        }
        return joinPoint.proceed();
    }
}
