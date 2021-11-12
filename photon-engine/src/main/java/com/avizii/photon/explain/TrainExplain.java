package com.avizii.photon.explain;

import com.avizii.photon.annotation.Syntax;
import com.avizii.photon.core.Explain;
import com.avizii.photon.parse.GlintParser;

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
