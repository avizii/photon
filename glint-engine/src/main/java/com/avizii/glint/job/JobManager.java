package com.avizii.glint.job;

import cn.hutool.core.lang.UUID;
import com.avizii.glint.dto.ExecutionDto;
import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.listener.ListenerChain;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class JobManager {

    public static <T> ExecutionDto run(GlintContext context, Supplier<T> supplier) {
        return null;
    }

    public static <T> ExecutionDto runAsync(GlintContext context, Supplier<T> supplier) {
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

    public static void runJob(GlintContext context) {
        ListenerChain chain = ListenerChain.of(context);
        chain.handle();
    }
}
