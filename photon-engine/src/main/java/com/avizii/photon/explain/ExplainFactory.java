package com.avizii.photon.explain;

import cn.hutool.core.util.ReflectUtil;
import com.avizii.photon.annotation.Syntax;
import com.avizii.photon.core.Explain;
import com.avizii.photon.util.AnnotationUtil;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class ExplainFactory {

  private static final String GLINT_EXPLAIN_PACKAGE = "com.avizii.glint.explain";

  private static final Map<String, Class<?>> explainMap = new ConcurrentHashMap<>();

  public static void init() {
    List<Class<?>> classes = AnnotationUtil.scan(GLINT_EXPLAIN_PACKAGE, Syntax.class);
    classes.forEach(
        clz -> {
          Syntax syntax = clz.getAnnotation(Syntax.class);
          explainMap.put(syntax.name(), clz);
          Arrays.stream(syntax.alias())
              .filter(s -> !s.equals(""))
              .forEach(s -> explainMap.put(s, clz));
        });
  }

  public static Explain of(String type) {
    Class<?> clz =
        Preconditions.checkNotNull(explainMap.get(type), String.format("未匹配到[%s]语法解释器!", type));
    return (Explain) ReflectUtil.newInstance(clz);
  }
}
