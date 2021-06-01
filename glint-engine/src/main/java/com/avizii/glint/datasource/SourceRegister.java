package com.avizii.glint.datasource;

import com.avizii.glint.core.Source;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Avizii
 * @Create : 2021.05.31
 */
public class SourceRegister {

    private static final Map<String, Source> sourcePool = new ConcurrentHashMap<>();
}
