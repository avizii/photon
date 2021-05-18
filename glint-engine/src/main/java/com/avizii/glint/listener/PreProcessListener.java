package com.avizii.glint.listener;

import com.avizii.glint.job.GlintContext;
import com.avizii.glint.parse.GlintBaseListener;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class PreProcessListener extends GlintBaseListener implements GlintHandler {

    private GlintContext context;

    public PreProcessListener(GlintContext context) {
        this.context = context;
    }

    @Override
    public GlintContext handle() {
        return null;
    }

    @Override
    public void updateContext(GlintContext context) {
        this.context = context;
    }
}
