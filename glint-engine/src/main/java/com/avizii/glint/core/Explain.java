package com.avizii.glint.core;

import com.avizii.glint.parse.GlintParser.SqlContext;

/** @Author : Avizii @Create : 2021.05.25 */
public interface Explain {

  void explain(SqlContext ctx);
}
