package com.avizii.glint.exception;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class GlintException extends RuntimeException {

    public GlintException() {
        super();
    }

    public GlintException(String message) {
        super(message);
    }

    public GlintException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlintException(Throwable cause) {
        super(cause);
    }

    protected GlintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
