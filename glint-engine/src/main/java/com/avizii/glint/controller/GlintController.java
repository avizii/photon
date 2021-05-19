package com.avizii.glint.controller;

import com.avizii.glint.common.GlintConstant;
import com.avizii.glint.dto.ExecutionDto;
import com.avizii.glint.dto.ExecutionResponse;
import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.job.GlintContext;
import com.avizii.glint.job.JobManager;
import com.avizii.glint.listener.ListenerChain;
import com.avizii.glint.session.SessionManager;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        // 通过 ThreadLocal 传递 context 信息
        GlintContext context = new GlintContext(session, request, JobManager.createJobInfo(request));
        GlintExecutor.setContext(context);

        switch (request.getExecuteMode()) {
            case GlintConstant.GLINT_EXECUTE_MODE_ANALYZE:
                return null;

            case GlintConstant.GLINT_EXECUTE_MODE_QUERY:
                Supplier<ExecutionDto> supplier = createSupplier();
                ExecutionDto executionResult = request.getAsync() ? JobManager.runAsync(supplier) : JobManager.run(supplier);
                return new ExecutionResponse(executionResult);

            default:
                throw new RuntimeException();
        }
    }

    private Supplier<ExecutionDto> createSupplier() {
        return () -> {
            GlintContext context = GlintExecutor.getContext();
            RunScriptRequest param = context.getParam();

            ListenerChain chain = ListenerChain.of(context);
            chain.handle();

            ExecutionDto dto = new ExecutionDto();
            if (!param.getSilence()) {
                if (StringUtils.isNotBlank(context.getLastSelectTable())) {
                    SparkSession session = context.getSession();
                    Dataset<Row> dataframe = session.table(context.getLastSelectTable());

                    if (param.getIncludeSchema()) {
                        String schemaJson = dataframe.schema().json();
                        dto.setSchemaJson(schemaJson);
                    }

                    Row[] rows = "collect".equals(param.getFetchType()) ? dataframe.collect() : dataframe.take(param.getFetchSize());
                    dto.setFetchType(param.getFetchType());
                    dto.setFetchSize((long) rows.length);

                    String dataJson = Arrays.stream(rows).map(Row::toString).collect(Collectors.joining(","));
                    dto.setDataJson(dataJson);
                }
            }
            return dto;
        };
    }

}
