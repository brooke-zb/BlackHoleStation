package com.brookezb.bhs.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.brookezb.bhs.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * API日志记录
 *
 * @author brooke_zb
 */
@Aspect
@Component
@Slf4j(topic = "api")
public class ControllerLogAspect {
    @Pointcut("execution(public * com.brookezb.bhs.controller..*.*(..))")
    public void controller() {}

    /**
     * 记录请求日志
     *
     * @param joinPoint 切点
     */
    @Around("controller()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        String method = joinPoint.getSignature().getDeclaringType().getName() + "#" + joinPoint.getSignature().getName();
        String ip = ServletUtil.getClientIP(request);

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("""
                status: success
                method: {}
                url: {}
                ip: {}
                function: {}
                args: {}
                """, request.getMethod(), request.getRequestURI(), ip, method, joinPoint.getArgs());
            return result;
        } catch (Throwable ex) {
            log.warn("""
                    status: fail
                    method: {}
                    url: {}
                    ip: {}
                    function: {}
                    args: {}
                    """, request.getMethod(), request.getRequestURI(), ip, method, joinPoint.getArgs());
            throw ex;
        }
    }
}
