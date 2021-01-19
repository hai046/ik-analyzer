package org.wltea.analyzer.exception;

/**
 * @author hai046
 * date 1/19/21
 */
public class ForbiddenException extends Exception {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
