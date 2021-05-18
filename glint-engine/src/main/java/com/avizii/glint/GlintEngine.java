package com.avizii.glint;

import com.avizii.glint.runtime.SparkRuntime;
import com.avizii.glint.util.ParamsUtil;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
public class GlintEngine {

    public static void main(String[] args) {
        Map<String, String> params = ParamsUtil.parse(args);
        SparkRuntime.create(params);
        GlintApplication.main(new String[0]);
    }
}
