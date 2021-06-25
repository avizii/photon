package com.avizii.glint.datasource;

import com.avizii.glint.util.SparkSimpleSchemaParser;
import org.apache.spark.sql.types.DataType;

/**
 * @Author : Avizii
 * @Create : 2021.06.25
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
