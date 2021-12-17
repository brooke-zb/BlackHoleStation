package top.brookezb.bhs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.exception.ForbiddenException;

/**
 * 全局异常处理器
 *
 * @author brooke_zb
 */
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
    public R<String> handleShiroException(Exception ex) {
        return R.fail(ex.getMessage());
    }
}
