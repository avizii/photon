package com.avizii.glint.explain;

import com.avizii.glint.annotation.Syntax;
import com.avizii.glint.common.DataSourceConstant;
import com.avizii.glint.core.Explain;
import com.avizii.glint.datasource.SourceRegister;
import com.avizii.glint.execute.GlintContext;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.parse.GlintParser.*;
import com.avizii.glint.template.SyntaxTemplateEngine;
import com.avizii.glint.util.ScriptUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;

/** @Author : Avizii @Create : 2021.05.25 */
@Syntax(name = "load")
public class LoadExplain implements Explain {

  @Override
  public void explain(SqlContext ctx) {
    GlintContext context = GlintExecutor.getContext();

    // extract param
    LoadParam param = extractParamFromSqlContext(ctx);

    // handle streaming input
    if (context.env().containsKey(DataSourceConstant.GLINT_STREAM_NAME)) {
      context.addEnv(DataSourceConstant.GLINT_STREAM, Boolean.TRUE.toString());
    }

    // build spark sql DataReader
    process(context, param);

    // cache latest-created spark-sql view name
    context.setLastSelectTable(param.getTableName());
  }

  private LoadParam extractParamFromSqlContext(SqlContext ctx) {
    String path = "";
    String format = "";
    String tableName = "";
    Map<String, String> options = new HashMap<>(5);
    for (int i = 0; i < ctx.getChildCount(); i++) {
      ParseTree tree = ctx.getChild(i);
      if (tree instanceof PathContext) {
        path = tree.getText();
      } else if (tree instanceof FormatContext) {
        format = tree.getText();
      } else if (tree instanceof TableNameContext) {
        tableName = SyntaxTemplateEngine.merge(tree.getText());
      } else if (tree instanceof ExpressionContext) {
        ExpressionContext ec = (ExpressionContext) tree;
        String key = ScriptUtils.cleanStr(ec.qualifiedName().getText());
        String value = SyntaxTemplateEngine.merge(ScriptUtils.getStrOrBlockStr(ec));
        options.put(key, value);
      } else if (tree instanceof BooleanExpressionContext) {
        BooleanExpressionContext bec = (BooleanExpressionContext) tree;
        String key = ScriptUtils.cleanStr(bec.expression().qualifiedName().getText());
        String value = SyntaxTemplateEngine.merge(ScriptUtils.getStrOrBlockStr(bec.expression()));
        options.put(key, value);
      }
    }
    return new LoadParam(path, format, tableName, options);
  }

  private void process(GlintContext context, LoadParam param) {
    SparkSession session = context.getSession();
    DataFrameReader reader = session.read().options(param.getOptions());
    param.path = SyntaxTemplateEngine.merge(param.path);
    Dataset<Row> dataFrame =
        SourceRegister.fetch(param.format).load(reader, null, param.path, param.options);
    dataFrame.createOrReplaceTempView(param.tableName);
  }

  @Data
  @AllArgsConstructor
  private static class LoadParam {
    private String path;
    private String format;
    private String tableName;
    private Map<String, String> options;
  }
}
