package com.avizii.glint.execute;

import com.avizii.glint.dto.RunScriptRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * environment params
     */
    private final Map<String, String> envMap = new HashMap<>();

    public GlintContext(SparkSession session, RunScriptRequest param, JobInfo jobInfo) {
        this.session = session;
        this.param = param;
        this.jobInfo = jobInfo;
    }

    public void setLastSelectTable(String table) {
        declaredTables.add(table);
        this.lastSelectTable = table;
    }

    public Map<String, String> env() {
        return envMap;
    }

    public void addEnv(String key, String value) {
        envMap.put(key, value);
    }
}
