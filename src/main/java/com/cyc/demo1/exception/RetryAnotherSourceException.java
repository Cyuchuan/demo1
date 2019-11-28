package com.cyc.demo1.exception;

/**
 * @author chenyuchuan
 */
public class RetryAnotherSourceException extends RuntimeException {
    static final long serialVersionUID = -7034897190745768939L;

    public RetryAnotherSourceException() {
        super();

    }

    public RetryAnotherSourceException(String message) {
        super(message);

    }

    public RetryAnotherSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
