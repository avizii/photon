package com.avizii.photon.datasource;

import com.avizii.photon.util.SparkSimpleSchemaParser;
import org.apache.spark.sql.types.DataType;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class SourceSchema {

  private final DataType sparkSchema;

  private final String schemaStr;

  public SourceSchema(String schemaStr) {
    this.schemaStr = schemaStr;
    this.sparkSchema = SparkSimpleSchemaParser.parse(schemaStr);
  }

  public DataType getSparkSchema() {
    return sparkSchema;
  }

  public String getSchemaStr() {
    return schemaStr;
  }
}
