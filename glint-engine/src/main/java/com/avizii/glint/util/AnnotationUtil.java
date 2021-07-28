package com.avizii.glint.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** @Author : Avizii @Create : 2021.05.25 */
public class AnnotationUtil {

  public static List<Class<?>> scan(String basePackage, Class annotation) {
    List<Class<?>> classes;
    try {
      classes = findClass(basePackage, annotation);
    } catch (IOException | ClassNotFoundException e) {
      classes = Collections.emptyList();
    }
    return classes;
  }

  private static List<Class<?>> findClass(String basePackage, Class<Annotation> annotation)
      throws IOException, ClassNotFoundException {
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
        new PathMatchingResourcePatternResolver(AnnotationUtil.class.getClassLoader());
    CachingMetadataReaderFactory cachingMetadataReaderFactory =
        new CachingMetadataReaderFactory((ResourceLoader) pathMatchingResourcePatternResolver);
    List<Class<?>> candidates = new ArrayList<>();
    String packageSearchPath = "classpath*:" + resolveBasePackage(basePackage) + "/**/*.class";
    Resource[] resources = pathMatchingResourcePatternResolver.getResources(packageSearchPath);
    if (resources != null)
      for (Resource resource : resources) {
        if (resource.isReadable()) {
          MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
          Class<?> aClass = Class.forName(metadataReader.getClassMetadata().getClassName());
          if (annotation == null || aClass.getAnnotation(annotation) != null)
            candidates.add(aClass);
        }
      }
    return candidates;
  }

  private static String resolveBasePackage(String basePackage) {
    return ClassUtils.convertClassNameToResourcePath(
        SystemPropertyUtils.resolvePlaceholders(basePackage));
  }
}
