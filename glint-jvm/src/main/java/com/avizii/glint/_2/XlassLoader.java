package com.avizii.glint._2;

import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class XlassLoader extends ClassLoader {

    private static final String PATH = "/Users/wudj/Workspace/avizii/glint/glint-jvm/doc/";

    /**
     * TODO : 要重写 findClass 方法，而不是 loadClass 方法(会破坏双亲委托机制，导致报错：Prohibited package name: java.lang)
     */
    @Override
    protected Class<?> findClass(String name) {
        FileReader reader = new FileReader(PATH + File.separator + name + ".xlass");
        byte[] bytes = reader.readBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        XlassLoader xlassLoader = new XlassLoader();
        Class<?> clazz = xlassLoader.loadClass("Hello");
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(instance);
    }


}
