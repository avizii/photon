package com.avizii.glint.job;

import com.avizii.glint.dto.RunScriptRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
@AllArgsConstructor
@Getter
public class GlintContext {

    @Setter
    private RunScriptRequest param;

    private final SparkSession session;

    private final JobInfo jobInfo;

    /**
     * last spark sql tempView name in a job
     */
    private String lastSelectTable = null;

    /**
     * all Spark SQL tempView name in a job
     */
    private final List<String> declaredTables = new ArrayList<>(3);

    public GlintContext(SparkSession session, RunScriptRequest param, JobInfo jobInfo) {
        this.session = session;
        this.param = param;
        this.jobInfo = jobInfo;
    }

    public void setLastSelectTable(String table) {
        declaredTables.add(table);
        this.lastSelectTable = table;
    }
}
