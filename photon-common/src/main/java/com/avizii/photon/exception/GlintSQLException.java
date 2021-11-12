package com.avizii.photon.exception;

import java.sql.SQLException;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
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
