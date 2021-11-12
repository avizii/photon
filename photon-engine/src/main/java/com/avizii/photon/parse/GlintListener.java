package com.avizii.photon.parse;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/** This interface defines a complete listener for a parse tree produced by {@link GlintParser}. */
public interface GlintListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link GlintParser#statement}.
   *
   * @param ctx the parse tree
   */
  void enterStatement(GlintParser.StatementContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#statement}.
   *
   * @param ctx the parse tree
   */
  void exitStatement(GlintParser.StatementContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#sql}.
   *
   * @param ctx the parse tree
   */
  void enterSql(GlintParser.SqlContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#sql}.
   *
   * @param ctx the parse tree
   */
  void exitSql(GlintParser.SqlContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#as}.
   *
   * @param ctx the parse tree
   */
  void enterAs(GlintParser.AsContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#as}.
   *
   * @param ctx the parse tree
   */
  void exitAs(GlintParser.AsContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#into}.
   *
   * @param ctx the parse tree
   */
  void enterInto(GlintParser.IntoContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#into}.
   *
   * @param ctx the parse tree
   */
  void exitInto(GlintParser.IntoContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#saveMode}.
   *
   * @param ctx the parse tree
   */
  void enterSaveMode(GlintParser.SaveModeContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#saveMode}.
   *
   * @param ctx the parse tree
   */
  void exitSaveMode(GlintParser.SaveModeContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#where}.
   *
   * @param ctx the parse tree
   */
  void enterWhere(GlintParser.WhereContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#where}.
   *
   * @param ctx the parse tree
   */
  void exitWhere(GlintParser.WhereContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#whereExpressions}.
   *
   * @param ctx the parse tree
   */
  void enterWhereExpressions(GlintParser.WhereExpressionsContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#whereExpressions}.
   *
   * @param ctx the parse tree
   */
  void exitWhereExpressions(GlintParser.WhereExpressionsContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#overwrite}.
   *
   * @param ctx the parse tree
   */
  void enterOverwrite(GlintParser.OverwriteContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#overwrite}.
   *
   * @param ctx the parse tree
   */
  void exitOverwrite(GlintParser.OverwriteContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#append}.
   *
   * @param ctx the parse tree
   */
  void enterAppend(GlintParser.AppendContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#append}.
   *
   * @param ctx the parse tree
   */
  void exitAppend(GlintParser.AppendContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#errorIfExists}.
   *
   * @param ctx the parse tree
   */
  void enterErrorIfExists(GlintParser.ErrorIfExistsContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#errorIfExists}.
   *
   * @param ctx the parse tree
   */
  void exitErrorIfExists(GlintParser.ErrorIfExistsContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#ignore}.
   *
   * @param ctx the parse tree
   */
  void enterIgnore(GlintParser.IgnoreContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#ignore}.
   *
   * @param ctx the parse tree
   */
  void exitIgnore(GlintParser.IgnoreContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#booleanExpression}.
   *
   * @param ctx the parse tree
   */
  void enterBooleanExpression(GlintParser.BooleanExpressionContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#booleanExpression}.
   *
   * @param ctx the parse tree
   */
  void exitBooleanExpression(GlintParser.BooleanExpressionContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#expression}.
   *
   * @param ctx the parse tree
   */
  void enterExpression(GlintParser.ExpressionContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#expression}.
   *
   * @param ctx the parse tree
   */
  void exitExpression(GlintParser.ExpressionContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#ender}.
   *
   * @param ctx the parse tree
   */
  void enterEnder(GlintParser.EnderContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#ender}.
   *
   * @param ctx the parse tree
   */
  void exitEnder(GlintParser.EnderContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#format}.
   *
   * @param ctx the parse tree
   */
  void enterFormat(GlintParser.FormatContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#format}.
   *
   * @param ctx the parse tree
   */
  void exitFormat(GlintParser.FormatContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#path}.
   *
   * @param ctx the parse tree
   */
  void enterPath(GlintParser.PathContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#path}.
   *
   * @param ctx the parse tree
   */
  void exitPath(GlintParser.PathContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#commandValue}.
   *
   * @param ctx the parse tree
   */
  void enterCommandValue(GlintParser.CommandValueContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#commandValue}.
   *
   * @param ctx the parse tree
   */
  void exitCommandValue(GlintParser.CommandValueContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#rawCommandValue}.
   *
   * @param ctx the parse tree
   */
  void enterRawCommandValue(GlintParser.RawCommandValueContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#rawCommandValue}.
   *
   * @param ctx the parse tree
   */
  void exitRawCommandValue(GlintParser.RawCommandValueContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#setValue}.
   *
   * @param ctx the parse tree
   */
  void enterSetValue(GlintParser.SetValueContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#setValue}.
   *
   * @param ctx the parse tree
   */
  void exitSetValue(GlintParser.SetValueContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#setKey}.
   *
   * @param ctx the parse tree
   */
  void enterSetKey(GlintParser.SetKeyContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#setKey}.
   *
   * @param ctx the parse tree
   */
  void exitSetKey(GlintParser.SetKeyContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#db}.
   *
   * @param ctx the parse tree
   */
  void enterDb(GlintParser.DbContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#db}.
   *
   * @param ctx the parse tree
   */
  void exitDb(GlintParser.DbContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#asTableName}.
   *
   * @param ctx the parse tree
   */
  void enterAsTableName(GlintParser.AsTableNameContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#asTableName}.
   *
   * @param ctx the parse tree
   */
  void exitAsTableName(GlintParser.AsTableNameContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#tableName}.
   *
   * @param ctx the parse tree
   */
  void enterTableName(GlintParser.TableNameContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#tableName}.
   *
   * @param ctx the parse tree
   */
  void exitTableName(GlintParser.TableNameContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#functionName}.
   *
   * @param ctx the parse tree
   */
  void enterFunctionName(GlintParser.FunctionNameContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#functionName}.
   *
   * @param ctx the parse tree
   */
  void exitFunctionName(GlintParser.FunctionNameContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#colGroup}.
   *
   * @param ctx the parse tree
   */
  void enterColGroup(GlintParser.ColGroupContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#colGroup}.
   *
   * @param ctx the parse tree
   */
  void exitColGroup(GlintParser.ColGroupContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#col}.
   *
   * @param ctx the parse tree
   */
  void enterCol(GlintParser.ColContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#col}.
   *
   * @param ctx the parse tree
   */
  void exitCol(GlintParser.ColContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#qualifiedName}.
   *
   * @param ctx the parse tree
   */
  void enterQualifiedName(GlintParser.QualifiedNameContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#qualifiedName}.
   *
   * @param ctx the parse tree
   */
  void exitQualifiedName(GlintParser.QualifiedNameContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#identifier}.
   *
   * @param ctx the parse tree
   */
  void enterIdentifier(GlintParser.IdentifierContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#identifier}.
   *
   * @param ctx the parse tree
   */
  void exitIdentifier(GlintParser.IdentifierContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#strictIdentifier}.
   *
   * @param ctx the parse tree
   */
  void enterStrictIdentifier(GlintParser.StrictIdentifierContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#strictIdentifier}.
   *
   * @param ctx the parse tree
   */
  void exitStrictIdentifier(GlintParser.StrictIdentifierContext ctx);
  /**
   * Enter a parse tree produced by {@link GlintParser#quotedIdentifier}.
   *
   * @param ctx the parse tree
   */
  void enterQuotedIdentifier(GlintParser.QuotedIdentifierContext ctx);
  /**
   * Exit a parse tree produced by {@link GlintParser#quotedIdentifier}.
   *
   * @param ctx the parse tree
   */
  void exitQuotedIdentifier(GlintParser.QuotedIdentifierContext ctx);
}
