package com.avizii.glint.datasource;

import org.apache.spark.sql.Column;

import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class CsvSourceParser extends SourceParser {

  @Override
  public Column parse(Column column, SourceSchema sourceSchema, Map<String, String> options) {
    return null;
  }
}
