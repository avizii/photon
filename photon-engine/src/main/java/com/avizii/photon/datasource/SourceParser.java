package com.avizii.photon.datasource;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.catalyst.expressions.Expression;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public abstract class SourceParser implements Serializable {

  public abstract Column parse(
      Column column, SourceSchema sourceSchema, Map<String, String> options);

  public Column withExpr(Expression expr) {
    return new Column(expr);
  }

  public static SourceParser getSourceParser(String name)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (name.contains(".")) return (SourceParser) Class.forName(name).newInstance();
    else {
      switch (name) {
        case "json":
          return new JsonSourceParser();
        case "csv":
          return new CsvSourceParser();
        default:
          return null;
      }
    }
  }
}
