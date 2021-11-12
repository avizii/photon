package com.avizii.photon.explain;

import com.avizii.photon.annotation.Syntax;
import com.avizii.photon.core.Explain;
import com.avizii.photon.parse.GlintParser;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Syntax(name = "create")
public class CreateExplain implements Explain {

  @Override
  public void explain(GlintParser.SqlContext ctx) {}
}
