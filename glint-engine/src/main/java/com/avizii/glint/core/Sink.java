package com.avizii.glint.core;

import org.apache.spark.sql.DataFrameWriterV2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import java.util.Map;

/** @Author : Avizii @Create : 2021.05.21 */
public interface Sink extends DataSource {

  /** 数据存储 */
  void save(
      DataFrameWriterV2<Row> writer,
      Dataset<Row> dataframe,
      SaveMode mode,
      String path,
      Map<String, String> params);
}
