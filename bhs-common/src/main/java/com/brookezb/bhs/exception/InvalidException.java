package com.brookezb.bhs.exception;

/**
 * 操作失败时抛出
 *
 * @author brooke_zb
 */
public class InvalidException extends RuntimeException {

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }
}
