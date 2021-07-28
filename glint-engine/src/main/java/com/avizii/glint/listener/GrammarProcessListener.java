package com.avizii.glint.listener;

import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.execute.GlintContext;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.parse.GlintBaseListener;

/** @Author : Avizii @Create : 2021.05.17 */
public class GrammarProcessListener extends GlintBaseListener implements GlintHandler {

  @Override
  public void handle() {
    GlintContext context = GlintExecutor.getContext();
    RunScriptRequest param = context.getParam();
    if (!param.getSkipGrammarValidate()) {
      // do something
    }
  }
}
