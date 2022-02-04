package com.brookezb.bhs.exception;

/**
 * 用户没有得到授权（如权限不足）时抛出此错误
 *
 * @author brooke_zb
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }
}
