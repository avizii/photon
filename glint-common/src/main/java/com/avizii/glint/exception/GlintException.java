package com.avizii.glint.exception;

/**
 * @author : Avizii
 * @create : 2021.05.21
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

  protected GlintException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
