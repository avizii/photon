package com.avizii.glint.runtime;

import com.avizii.glint.common.GlintConstant;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
public abstract class GlintRuntime<ENV> {

    protected Map<String, String> params;

    public GlintRuntime(Map<String, String> params) {
        this.params = params;
    }

    public static GlintRuntime create(Map<String, String> params) {
        String platform = params.getOrDefault(GlintConstant.GLINT_RUNTIME_PLATFORM, GlintConstant.SPARK_PLATFORM);

        if (platform.equals(GlintConstant.SPARK_PLATFORM)) return new SparkRuntime(params);

        if (platform.equals(GlintConstant.FLINK_PLATFORM)) return new FlinkRuntime(params);

        throw new RuntimeException(String.format("暂不支持[%s]引擎!", platform));
    }

    protected abstract ENV start();

}
