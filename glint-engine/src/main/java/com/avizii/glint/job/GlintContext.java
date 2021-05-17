package com.avizii.glint.job;

import com.avizii.glint.dto.RunScriptRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.SparkSession;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
@AllArgsConstructor
@Getter
@Setter
public class GlintContext {

    private SparkSession session;

    private RunScriptRequest param;

    private JobInfo jobInfo;
}
