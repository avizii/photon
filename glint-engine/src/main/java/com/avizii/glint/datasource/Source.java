package com.avizii.glint.datasource;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.21
 */
public interface Source extends DataSource {

    /**
     * 数据加载
     */
    Dataset<Row> load(DataFrameReader reader, Dataset<Row> dataframe, String path, Map<String, String> params);

}
