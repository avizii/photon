package com.avizii.photon.datasource;

import com.avizii.photon.core.DataSource;
import com.avizii.photon.core.Source;
import com.avizii.photon.exception.GlintException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class SourceRegister {

  private static final Map<String, Source> sourcePool = new ConcurrentHashMap<>();

  public static void register(String name, Source source) {
    sourcePool.put(name, source);
  }

  public static void unRegister(String name) {
    sourcePool.remove(name);
  }

  public static List<String> allSourceAliases() {
    return sourcePool.values().stream().map(DataSource::alias).collect(Collectors.toList());
  }

  public static Source fetch(String name) {
    if (sourcePool.containsKey(name)) {
      return sourcePool.get(name);
    }
    throw new GlintException(String.format("Can not find [%s] source plugin!", name));
  }
}
