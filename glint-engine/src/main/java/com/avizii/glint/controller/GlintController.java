package com.avizii.glint.controller;

import com.avizii.glint.common.GlintConstant;
import com.avizii.glint.dto.ExecutionDto;
import com.avizii.glint.dto.ExecutionResponse;
import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.job.GlintContext;
import com.avizii.glint.job.JobManager;
import com.avizii.glint.session.SessionManager;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.SparkSession;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Supplier;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
@RestController
@CrossOrigin
@Slf4j
@Validated
public class GlintController {

    @PostMapping("/runScript")
    public ExecutionResponse runScript(@RequestBody @Valid RunScriptRequest request) {
        // 1.获取spark session
        SparkSession session = SessionManager.getSessionByType(request.getSessionType(), request.getToken());

        Preconditions.checkArgument(!(request.getAsync() && StringUtils.isEmpty(request.getCallbackUrl())), "异步执行必须设置[callbackUrl]参数!");

        GlintContext context = new GlintContext(session, request, JobManager.createJobInfo(request));

        switch (request.getExecuteMode()) {
            case GlintConstant.GLINT_EXECUTE_MODE_ANALYZE:
                return null;

            case GlintConstant.GLINT_EXECUTE_MODE_QUERY:
                Supplier<ExecutionDto> supplier = () -> null; // TODO
                ExecutionDto executionResult = request.getAsync() ? JobManager.runAsync(context, supplier) : JobManager.run(context, supplier);
                return new ExecutionResponse(executionResult);

            default:
                throw new RuntimeException();
        }
    }


}
