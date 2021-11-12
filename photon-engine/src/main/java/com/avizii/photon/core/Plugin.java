package com.avizii.photon.core;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public interface Plugin {

  /** 模型注册、UDF注册 */
  default UserDefinedFunction register(
      SparkSession session, Object model, String name, Map<String, String> params) {
    throw new UnsupportedOperationException();
  }
}
