package com.avizii.glint.runtime;

import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
public class FlinkRuntime extends GlintRuntime<Object> {

    public FlinkRuntime(Map<String, String> params) {
        super(params);
    }

    @Override
    protected Object start() {
        return null;
    }
}
