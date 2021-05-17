package com.avizii.glint.listener;

import com.avizii.glint.job.GlintContext;
import com.avizii.glint.parse.GlintBaseListener;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class AuthProcessListener extends GlintBaseListener {

    private final GlintContext context;

    public AuthProcessListener(GlintContext context) {
        this.context = context;
    }
}
