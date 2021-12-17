package top.brookezb.bhs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<R<String>> AuthorizationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(R.fail(ex.getMessage()));
    }

    /**
     * 返回403
     * 处理用户没有权限的异常
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<R<String>> handleShiroException(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(R.fail(ex.getMessage()));
    }
}
