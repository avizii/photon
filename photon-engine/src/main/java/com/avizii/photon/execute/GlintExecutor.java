package com.avizii.photon.execute;

import com.avizii.photon.listener.CaseChangingCharStream;
import com.avizii.photon.listener.GlintErrorStrategy;
import com.avizii.photon.listener.SyntaxErrorListener;
import com.avizii.photon.parse.GlintLexer;
import com.avizii.photon.parse.GlintListener;
import com.avizii.photon.parse.GlintParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class GlintExecutor {

  private static final ThreadLocal<GlintContext> glintContext = new ThreadLocal<>();

  public static void parse(String input, GlintListener listener) {
    CaseChangingCharStream charStream = new CaseChangingCharStream(input);
    GlintLexer lexer = new GlintLexer(charStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    GlintParser parser = new GlintParser(tokenStream);
    parser.setErrorHandler(new GlintErrorStrategy());
    parser.addErrorListener(new SyntaxErrorListener());

    ParseTreeWalker.DEFAULT.walk(listener, parser.statement());
  }

  public static GlintContext getContext() {
    return glintContext.get();
  }

  public static void setContext(GlintContext context) {
    glintContext.set(context);
  }

  public static void removeContext() {
    glintContext.remove();
  }
}
