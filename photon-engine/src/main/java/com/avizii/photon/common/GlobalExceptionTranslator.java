package com.avizii.photon.common;

import com.avizii.photon.api.BaseResponse;
import com.avizii.photon.api.ResultCode;
import com.avizii.photon.exception.GlintException;
import com.avizii.photon.exception.GlintSQLException;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@RestControllerAdvice
public class GlobalExceptionTranslator {

  static final ILogger logger = SLoggerFactory.getLogger(GlobalExceptionTranslator.class);

  @ExceptionHandler(GlintException.class)
  public BaseResponse handleError(GlintException e) {
    logger.error("Glint Error", e);
    return BaseResponse.builder()
        .code(ResultCode.INTERNAL_SERVER_ERROR)
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(GlintSQLException.class)
  public BaseResponse handleError(GlintSQLException e) {
    logger.error("Glint SQL Error", e);
    return BaseResponse.builder()
        .code(ResultCode.INTERNAL_SERVER_ERROR)
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(Throwable.class)
  public BaseResponse handleError(Throwable e) {
    logger.error("Internal Server Error", e);
    return BaseResponse.builder()
        .code(ResultCode.INTERNAL_SERVER_ERROR)
        .message(e.getMessage())
        .build();
  }
}
