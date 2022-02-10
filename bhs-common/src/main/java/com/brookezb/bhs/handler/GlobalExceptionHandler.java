package com.brookezb.bhs.handler;

import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.ForbiddenException;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author brooke_zb
 */
@Slf4j(topic = "api-error")
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 返回401
     * 处理用户未登录的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public R<String> AuthorizationException(Exception ex) {
        return R.fail(ex.getMessage());
    }

    /**
     * 返回403
     * 处理用户没有权限的异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public R<String> ForbiddenException(Exception ex) {
        return R.fail(ex.getMessage());
    }

    /**
     * 返回400
     * 处理参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public R<String> ConstraintViolationException(Exception ex) {
        if (ex instanceof BindException bex) {
            assert bex.getBindingResult().getFieldError() != null;
            return R.fail(bex.getBindingResult().getFieldError().getDefaultMessage());
        }
        return R.fail(ex.getMessage().substring(ex.getMessage().indexOf(": ") + 2));
    }

    /**
     * 返回404
     * 处理资源不存在异常
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public R<String> NotFoundException(Exception ex) {
        return R.fail(ex.getMessage());
    }

    /**
     * 返回400
     * 处理失败异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    public R<String> InvalidException(Exception ex) {
        return R.fail(ex.getMessage());
    }

    /**
     * 返回404
     * 处理找不到该接口情况
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public R<String> NoHandlerFoundException() {
        return R.fail("接口不存在");
    }

    /**
     * 返回400
     * 处理参数缺失异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public R<String> HttpMessageConversionException() {
        return R.fail("接口必要参数缺失");
    }

    /**
     * 返回500
     * 处理其他异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<String> RuntimeException(Exception ex) {
        log.error("操作出现异常，执行失败", ex);
        return R.fail("操作出现异常，执行失败");
    }
}
