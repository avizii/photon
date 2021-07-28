package com.avizii.glint.util;

import com.avizii.glint.parse.GlintParser;

/** @Author : Avizii @Create : 2021.05.25 */
public class ScriptUtils {

  public static String cleanStr(String str) {
    boolean isClean =
        str.startsWith("`")
            || str.startsWith("\"")
            || (str.startsWith("'") && !str.startsWith("'''"));
    return isClean ? str.substring(1, str.length() - 1) : str;
  }

  public static String cleanBlockStr(String str) {
    boolean isClean = str.startsWith("'''") && str.endsWith("'''");
    return isClean ? str.substring(3, str.length() - 3) : str;
  }

  public static String getStrOrBlockStr(GlintParser.ExpressionContext ec) {
    boolean isBlock = ec.STRING() == null || ec.STRING().getText().isEmpty();
    return isBlock ? cleanBlockStr(ec.BLOCK_STRING().getText()) : cleanStr(ec.STRING().getText());
  }
}
