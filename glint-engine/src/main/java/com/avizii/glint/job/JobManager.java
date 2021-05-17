package com.avizii.glint.job;

import cn.hutool.core.lang.UUID;
import com.avizii.glint.dto.ExecutionDto;
import com.avizii.glint.dto.RunScriptRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class JobManager {

    public static ExecutionDto run(GlintContext context) {
        return null;
    }

    public static ExecutionDto runAsync(GlintContext context) {
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