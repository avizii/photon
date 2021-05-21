package com.avizii.glint.plugin;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.21
 */
public interface MLPlugin extends Plugin {

    /**
     * 机器学习训练
     */
    Dataset<Row> train(Dataset<Row> dataframe, String path, Map<String, String> params);

    /**
     * 机器学习模型加载
     */
    Object load(SparkSession session, String path, Map<String, String> params);

    /**
     * 机器学习预测
     */
    Dataset<Row> predict(Dataset<Row> dataframe, String path, Map<String, String> params);

}
