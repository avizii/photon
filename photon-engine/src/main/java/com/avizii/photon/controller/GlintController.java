package com.avizii.photon.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.avizii.photon.common.GlintConstant;
import com.avizii.photon.dto.ExecutionResponse;
import com.avizii.photon.dto.RunScriptRequest;
import com.avizii.photon.dto.ScriptResult;
import com.avizii.photon.exception.GlintException;
import com.avizii.photon.execute.GlintContext;
import com.avizii.photon.execute.GlintExecutor;
import com.avizii.photon.execute.JobManager;
import com.avizii.photon.listener.ListenerChain;
import com.avizii.photon.session.SessionManager;
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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@RestController
@CrossOrigin
@Slf4j
@Validated
public class GlintController {

  @PostMapping("/runScript")
  public ExecutionResponse runScript(@RequestBody @Valid RunScriptRequest request) {
    // 1.获取spark session
    SparkSession session =
        SessionManager.getSessionByType(request.getSessionType(), request.getToken());

    Preconditions.checkArgument(
        !(request.getAsync() && StringUtils.isEmpty(request.getCallbackUrl())),
        "异步执行必须设置[callbackUrl]参数!");

    // 通过 ThreadLocal 传递 context 信息
    GlintContext context = new GlintContext(session, request, JobManager.createJobInfo(request));
    GlintExecutor.setContext(context);
    try {
      switch (request.getExecuteMode()) {
        case GlintConstant.GLINT_EXECUTE_MODE_ANALYZE:
          return null;

        case GlintConstant.GLINT_EXECUTE_MODE_QUERY:
          ScriptResult executionResult =
              request.getAsync()
                  ? JobManager.runAsync(createAsyncSupplier())
                  : JobManager.run(createSupplier());

          return new ExecutionResponse(executionResult);

        default:
          throw new RuntimeException();
      }
    } catch (Throwable e) {
      e.printStackTrace();
      throw new GlintException(e); // 全局异常处理
    } finally {
      GlintExecutor.removeContext();
    }
  }

  private Supplier<ScriptResult> createSupplier() {
    return () -> {
      ListenerChain chain = ListenerChain.of();
      chain.handle();
      return getScriptResult();
    };
  }

  private Supplier<ScriptResult> createAsyncSupplier() {
    return () -> {
      GlintContext context = GlintExecutor.getContext();
      RunScriptRequest param = context.getParam();
      try {
        ListenerChain chain = ListenerChain.of();
        chain.handle();
        ScriptResult result = getScriptResult();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stat", "success");
        paramMap.put("res", JSONUtil.toJsonStr(result));
        paramMap.put("job", JSONUtil.toJsonStr(context.getJobInfo()));
        HttpUtil.post(param.getCallbackUrl(), paramMap);
      } catch (Throwable e) {
        e.printStackTrace();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stat", "fail");
        paramMap.put("msg", ExceptionUtil.stacktraceToString(e));
        paramMap.put("job", JSONUtil.toJsonStr(context.getJobInfo()));
        HttpUtil.post(param.getCallbackUrl(), paramMap);
      }
      return null;
    };
  }

  private ScriptResult getScriptResult() {
    GlintContext context = GlintExecutor.getContext();
    RunScriptRequest param = context.getParam();
    ScriptResult result = new ScriptResult();
    if (StringUtils.isNotBlank(context.getLastSelectTable())) {
      SparkSession session = context.getSession();
      Dataset<Row> dataframe = session.table(context.getLastSelectTable());

      if (param.getIncludeSchema()) {
        String schemaJson = dataframe.schema().json();
        result.setSchemaJson(schemaJson);
      }

      Row[] rows =
          "collect".equals(param.getFetchType())
              ? (Row[]) dataframe.collect()
              : (Row[]) dataframe.take(param.getFetchSize());
      result.setFetchType(param.getFetchType());
      result.setFetchSize((long) rows.length);

      String dataJson = Arrays.stream(rows).map(Row::toString).collect(Collectors.joining(","));
      result.setDataJson(dataJson);
    }
    return result;
  }
}
