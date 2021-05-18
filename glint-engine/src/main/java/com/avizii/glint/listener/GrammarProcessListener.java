package com.avizii.glint.listener;

import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.job.GlintContext;
import com.avizii.glint.parse.GlintBaseListener;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class GrammarProcessListener extends GlintBaseListener implements GlintHandler {

    private GlintContext context;

    public GrammarProcessListener(GlintContext context) {
        this.context = context;
    }

    @Override
    public GlintContext handle() {
        RunScriptRequest param = context.getParam();
        if (!param.getSkipGrammarValidate()) {
            // do something
        }
        return context;
    }

    @Override
    public void updateContext(GlintContext context) {
        this.context = context;
    }
}
