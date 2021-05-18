package com.avizii.glint.listener;

import com.avizii.glint.job.GlintContext;

/**
 * @Author : Avizii
 * @Create : 2021.05.18
 */
public interface GlintHandler {

    GlintContext handle();

    void updateContext(GlintContext context);
}
