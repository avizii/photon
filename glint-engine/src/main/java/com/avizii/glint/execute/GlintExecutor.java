package com.avizii.glint.execute;

import com.avizii.glint.parse.GlintLexer;
import com.avizii.glint.parse.GlintListener;
import com.avizii.glint.parse.GlintParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
public class GlintExecutor {

    public static void execute(String input, GlintListener listener) {
        CaseChangingCharStream charStream = new CaseChangingCharStream(input);
        GlintLexer lexer = new GlintLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        GlintParser parser = new GlintParser(tokenStream);
        parser.setErrorHandler(new GlintErrorStrategy());
        parser.addErrorListener(new SyntaxErrorListener());

        ParseTreeWalker.DEFAULT.walk(listener, parser.statement());
    }
}
