package com.avizii.glint.plugin;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.21
 */
public interface ETLPlugin extends Plugin {

    /**
     * DataFrame转换操作
     */
    Dataset<Row> transform(Dataset<Row> dataframe, String path, Map<String, String> params);

}
