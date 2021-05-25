package com.avizii.glint;

import com.avizii.glint.explain.ExplainFactory;
import com.avizii.glint.runtime.SparkRuntime;
import com.avizii.glint.util.ParamsUtil;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
public class GlintEngine {

    public static void main(String[] args) {
        // 解析输入参数
        Map<String, String> params = ParamsUtil.parse(args);

        // 初始化语法解释器
        ExplainFactory.init();

        // 启动Spark
        SparkRuntime.create(params);

        // 启动HttpServer
        GlintApplication.main(new String[0]);
    }
}
