package com.avizii.glint.listener;

import com.avizii.glint.dto.RunScriptRequest;
import com.avizii.glint.execute.GlintContext;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.parse.GlintBaseListener;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class IncludeProcessListener extends GlintBaseListener implements GlintHandler {

  @Override
  public void handle() {
    GlintContext context = GlintExecutor.getContext();
    RunScriptRequest param = context.getParam();
    if (!param.getSkipInclude()) {
      // do something ……
    }
  }
}
