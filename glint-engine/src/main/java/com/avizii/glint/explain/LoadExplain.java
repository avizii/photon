package com.avizii.glint.explain;

import com.avizii.glint.annotation.Syntax;
import com.avizii.glint.common.GlintConstant;
import com.avizii.glint.common.ScriptUtils;
import com.avizii.glint.core.Explain;
import com.avizii.glint.execute.GlintContext;
import com.avizii.glint.execute.GlintExecutor;
import com.avizii.glint.parse.GlintParser.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.05.25
 */
@Syntax(name = "load")
public class LoadExplain implements Explain {

    @Override
    public void explain(SqlContext ctx) {
        GlintContext context = GlintExecutor.getContext();

        // extract param
        LoadParam param = extractParamFromSqlContext(ctx);

        // handle streaming input
        if (context.env().containsKey(GlintConstant.GLINT_STREAM_NAME)) {
            context.addEnv(GlintConstant.GLINT_STREAM, Boolean.TRUE.toString());
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
                tableName = tree.getText(); // TODO: 2021/5/31 此处MLSQL有做TemplateMerge处理，还不清楚干嘛用
            } else if (tree instanceof ExpressionContext) {
                ExpressionContext ec = (ExpressionContext) tree;
                String key = ScriptUtils.cleanStr(ec.qualifiedName().getText());
                String value = ScriptUtils.getStrOrBlockStr(ec); // TODO: 2021/5/31 此处MLSQL有做TemplateMerge处理，还不清楚干嘛用
                options.put(key, value);
            } else if (tree instanceof BooleanExpressionContext) {
                BooleanExpressionContext bec = (BooleanExpressionContext) tree;
                String key = ScriptUtils.cleanStr(bec.expression().qualifiedName().getText());
                String value = ScriptUtils.getStrOrBlockStr(bec.expression());  // TODO: 2021/5/31 此处MLSQL有做TemplateMerge处理，还不清楚干嘛用
                options.put(key, value);
            }
        }
        return new LoadParam(path, format, tableName, options);
    }

    private void process(GlintContext context, LoadParam param) {
        SparkSession session = context.getSession();
        DataFrameReader reader = session.read().options(param.getOptions());

        // TODO: 2021/5/31 此处MLSQL对path有做TemplateMerge处理，还不清楚干嘛用
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
