package com.avizii.glint.template;

import cn.hutool.core.date.DateUtil;
import com.avizii.glint.common.CommandSugarEnum;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Avizii
 * @Create : 2021.06.25
 */
public class SyntaxTemplateEngine {

    private static final String DATA_FORMAT = "yyyy-MM-dd";

    public static String merge(String value) {
        return merge(value, CommandSugarEnum.toMap());
    }

    public static String merge(String value, Map<String, String> elements) {
        Map<String, Object> mergeMap = new HashMap<>(elements);
        Date date = new Date();
        mergeMap.put("date", date);
        mergeMap.put("yesterday", DateUtil.yesterday().toString(DATA_FORMAT));
        mergeMap.put("today", DateUtil.today());
        mergeMap.put("tomorrow", DateUtil.tomorrow().toString(DATA_FORMAT));
        mergeMap.put("theDayBeforeYesterday", DateUtil.offsetDay(date, -2).toString(DATA_FORMAT));
        return namedEvaluate(value, mergeMap);
    }

    private static String namedEvaluate(String template, Map<String, Object> elements) {
        VelocityContext context = new VelocityContext(elements);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "", new StringReader(template));
        return writer.toString();
    }
}
