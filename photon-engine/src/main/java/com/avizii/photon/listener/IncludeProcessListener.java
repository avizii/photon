package com.avizii.photon.listener;

import com.avizii.photon.dto.RunScriptRequest;
import com.avizii.photon.execute.GlintContext;
import com.avizii.photon.execute.GlintExecutor;
import com.avizii.photon.parse.GlintBaseListener;

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
