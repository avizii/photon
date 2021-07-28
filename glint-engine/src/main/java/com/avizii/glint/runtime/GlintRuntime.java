package com.avizii.glint.runtime;

import java.util.Map;

/** @Author : Avizii @Create : 2021.05.14 */
public abstract class GlintRuntime<SESSION> {

  protected Map<String, String> params;

  public GlintRuntime(Map<String, String> params) {
    this.params = params;
  }

  public abstract SESSION run();
}
