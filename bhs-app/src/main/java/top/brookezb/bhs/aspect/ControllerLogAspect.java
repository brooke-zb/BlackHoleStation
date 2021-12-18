package top.brookezb.bhs.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.brookezb.bhs.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author brooke_zb
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    @Pointcut("execution(public * top.brookezb.bhs.controller.*.*(..))")
    public void controller() {
    }

    /**
     * 记录请求日志
     *
     * @param joinPoint 切点
     */
    @Before("controller()")
    public void log(JoinPoint joinPoint) {
        HttpServletRequest request = ServletUtils.getRequest();
        String method = joinPoint.getSignature().getDeclaringType().getName() + "." + joinPoint.getSignature().getName();
        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        log.info("{} Request: {url: {}, ip: {}, method: {}, args: {}}", request.getMethod(), request.getRequestURI(), ip, method, joinPoint.getArgs());
    }

    /**
     * 记录异常日志
     *
     * @param ex 异常
     */
    @AfterThrowing(value = "controller()", throwing = "ex")
    public void warn(Throwable ex) {
        log.warn("Request Fail: {}", ex.getMessage());
    }
}
