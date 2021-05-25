package com.avizii.glint.explain;

import com.avizii.glint.annotation.Syntax;
import com.avizii.glint.common.AnnotationUtil;
import com.avizii.glint.core.Explain;
import com.avizii.glint.exception.GlintException;
import com.google.common.base.Preconditions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Avizii
 * @Create : 2021.05.25
 */
public class ExplainFactory {

    private static final String GLINT_EXPLAIN_PACKAGE = "com.avizii.glint.explain";

    private static final Map<String, Class<?>> explainMap = new ConcurrentHashMap<>();

    public static void init() {
        List<Class<?>> classes = AnnotationUtil.scan(GLINT_EXPLAIN_PACKAGE, Syntax.class);
        classes.forEach(clz -> {
            Syntax syntax = clz.getAnnotation(Syntax.class);
            explainMap.put(syntax.name(), clz);
            Arrays.stream(syntax.alias())
                    .filter(s -> !s.equals(""))
                    .forEach(s -> explainMap.put(s, clz));
        });
    }

    public static Explain of(String type) {
        Class<?> clz = Preconditions.checkNotNull(explainMap.get(type), String.format("未匹配到[%s]语法解释器!", type));
        try {
            Constructor<?> constructor = clz.getConstructor();
            return (Explain) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new GlintException();
        }
    }
}
