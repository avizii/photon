package com.avizii.glint.explain;

import com.avizii.glint.annotation.Syntax;
import com.avizii.glint.core.Explain;
import com.avizii.glint.parse.GlintParser;

/** @Author : Avizii @Create : 2021.05.25 */
@Syntax(name = "transform")
public class TransformExplain implements Explain {

  @Override
  public void explain(GlintParser.SqlContext ctx) {}
}
