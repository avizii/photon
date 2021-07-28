package com.avizii.glint.datasource;

import org.apache.spark.sql.Column;

import java.util.Map;

/** @Author : Avizii @Create : 2021.06.25 */
public class JsonSourceParser extends SourceParser {

  @Override
  public Column parse(Column column, SourceSchema sourceSchema, Map<String, String> options) {
    return null;
  }
}
