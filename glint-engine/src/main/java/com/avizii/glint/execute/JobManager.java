package com.avizii.glint.execute;

import cn.hutool.core.lang.UUID;
import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.dto.ScriptResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.SparkSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class JobManager {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(50);

    public static <T> ScriptResult run(Supplier<T> supplier) {
        GlintContext context = GlintExecutor.getContext();
        SparkSession session = context.getSession();
        JobInfo job = context.getJobInfo();
        try {
            session.sparkContext().setJobGroup(job.getJobId(), job.getJobName() + " - " + job.getJobContent(), true);
            return (ScriptResult) supplier.get();
        } finally {
            session.sparkContext().clearJobGroup();
        }
    }

    public static <T> ScriptResult runAsync(Supplier<T> supplier) {
        GlintContext context = GlintExecutor.getContext();
        threadPool.execute(() -> {
            try {
                GlintExecutor.setContext(context);
                JobManager.run(supplier);
            } finally {
                GlintExecutor.removeContext();
            }
        });
        return null;
    }

    public static JobInfo createJobInfo(RunScriptRequest request) {
        return JobInfo.builder()
                .token(request.getToken())
                .jobContent(request.getSql())
                .jobName(request.getJobName())
                .jobId(StringUtils.isEmpty(request.getJobId()) ? UUID.fastUUID().toString(true) : request.getJobId())
                .startTime(System.currentTimeMillis())
                .timeout(request.getTimeout())
                .build();
    }

}
