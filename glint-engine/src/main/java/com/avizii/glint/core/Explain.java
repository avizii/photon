package com.avizii.glint.core;

import com.avizii.glint.parse.GlintParser.SqlContext;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public interface Explain {

  void explain(SqlContext ctx);
}
