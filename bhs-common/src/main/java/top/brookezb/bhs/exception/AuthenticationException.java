package top.brookezb.bhs.exception;

/**
 * 用户无权限（如未登录）时抛出此错误
 *
 * @author brooke_zb
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}