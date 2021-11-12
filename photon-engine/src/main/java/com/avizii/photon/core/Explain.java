package com.avizii.photon.core;

import com.avizii.photon.parse.GlintParser.SqlContext;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public interface Explain {

  void explain(SqlContext ctx);
}
