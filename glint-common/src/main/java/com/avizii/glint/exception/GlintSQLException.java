package com.avizii.glint.exception;

import java.sql.SQLException;

/** @Author : Avizii @Create : 2021.05.17 */
public class GlintSQLException extends SQLException {

  public GlintSQLException(String reason, String SQLState, int vendorCode) {
    super(reason, SQLState, vendorCode);
  }

  public GlintSQLException(String reason, String SQLState) {
    super(reason, SQLState);
  }

  public GlintSQLException(String reason) {
    super(reason);
  }

  public GlintSQLException() {
    super();
  }

  public GlintSQLException(Throwable cause) {
    super(cause);
  }

  public GlintSQLException(String reason, Throwable cause) {
    super(reason, cause);
  }

  public GlintSQLException(String reason, String sqlState, Throwable cause) {
    super(reason, sqlState, cause);
  }

  public GlintSQLException(String reason, String sqlState, int vendorCode, Throwable cause) {
    super(reason, sqlState, vendorCode, cause);
  }
}
