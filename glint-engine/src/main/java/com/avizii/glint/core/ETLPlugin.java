package com.avizii.glint.core;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public interface ETLPlugin extends Plugin {

  /** DataFrame转换操作 */
  Dataset<Row> transform(Dataset<Row> dataframe, String path, Map<String, String> params);
}
