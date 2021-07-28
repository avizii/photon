package com.avizii.glint.explain;

import com.avizii.glint.annotation.Syntax;
import com.avizii.glint.core.Explain;
import com.avizii.glint.parse.GlintParser;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Syntax(
    name = "train",
    alias = {"run", "predict"})
public class TrainExplain implements Explain {

  @Override
  public void explain(GlintParser.SqlContext ctx) {}
}
