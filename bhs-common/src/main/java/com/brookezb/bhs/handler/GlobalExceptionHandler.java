package com.brookezb.bhs.handler;

import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.ForbiddenException;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.brookezb.bhs.entity.R;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author brooke_zb
 */
@Slf4j
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
    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> ConstraintViolationException(Exception ex) {
        return R.fail(ex.getMessage().substring(ex.getMessage().indexOf(": ") + 2));
    }

    /**
     * 返回400
     * 处理参数校验异常
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
     * 返回500
     * 处理其他异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public R<String> RuntimeException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return R.fail("操作出现异常，执行失败");
    }
}