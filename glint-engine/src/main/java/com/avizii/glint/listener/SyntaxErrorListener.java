package com.avizii.glint.listener;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class SyntaxErrorListener extends BaseErrorListener {

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    throw new RuntimeException(
        String.format(
            "Glint Parser error in [row : %d, column : %d] with error message : [%s]",
            line, charPositionInLine, msg));
  }
}
