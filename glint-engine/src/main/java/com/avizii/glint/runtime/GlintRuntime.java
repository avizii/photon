package com.avizii.glint.runtime;

import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public abstract class GlintRuntime<SESSION> {

  protected Map<String, String> params;

  public GlintRuntime(Map<String, String> params) {
    this.params = params;
  }

  public abstract SESSION run();
}
