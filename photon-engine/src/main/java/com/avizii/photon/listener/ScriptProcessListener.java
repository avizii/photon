package com.avizii.photon.listener;

import com.avizii.photon.core.Explain;
import com.avizii.photon.dto.RunScriptRequest;
import com.avizii.photon.execute.GlintContext;
import com.avizii.photon.execute.GlintExecutor;
import com.avizii.photon.explain.ExplainFactory;
import com.avizii.photon.parse.GlintBaseListener;
import com.avizii.photon.parse.GlintParser;

import java.util.Locale;

/**
 * @author : Avizii
 * @create : 2021.05.21
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
