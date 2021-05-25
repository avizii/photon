package com.avizii.glint.listener;

import com.avizii.glint.core.Explain;
import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.execute.GlintContext;
import com.avizii.glint.explain.ExplainFactory;
import com.avizii.glint.parse.GlintBaseListener;
import com.avizii.glint.parse.GlintParser;

import java.util.Locale;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
public class ScriptProcessListener extends GlintBaseListener implements GlintHandler {

    @Override
    public void handle() {
        GlintContext context = GlintExecutor.getContext();
        RunScriptRequest param = context.getParam();
        if (!param.getSkipPhysical()) {
            GlintExecutor.parse(param.getSql(), this);
        }
    }

    @Override
    public void exitSql(GlintParser.SqlContext ctx) {
        String explainType = ctx.getChild(0).getText().toLowerCase(Locale.ROOT);
        Explain explain = ExplainFactory.of(explainType);
        explain.explain(ctx);
    }
}
