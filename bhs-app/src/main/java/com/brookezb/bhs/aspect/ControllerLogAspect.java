package com.brookezb.bhs.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.brookezb.bhs.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author brooke_zb
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    @Pointcut("execution(public * com.brookezb.bhs.controller.*.*(..))")
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
        String method = joinPoint.getSignature().getDeclaringType().getName() + "#" + joinPoint.getSignature().getName();
        String ip = ServletUtil.getClientIP(request);

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
