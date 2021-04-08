package com.avizii.glint.advance;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class XarLoader extends ClassLoader {

    private final URL[] urls;

    private final Map<String, byte[]> xlassMap = new HashMap<>();

    public XarLoader(URL[] urls) {
        this.urls = urls;
        resolve();
    }

    private void resolve() {
        Arrays.stream(urls).forEach(url -> {
            File file = ZipUtil.unzip(url.getFile());
            Optional.ofNullable(file.listFiles())
                    .ifPresent(files ->
                            Arrays.stream(files)
                                    .forEach(clazzFile -> {
                                        FileReader reader = FileReader.create(clazzFile);
                                        byte[] bytes = reader.readBytes();
                                        if (bytes.length > 0)
                                            xlassMap.put(clazzFile.getName().replace(".xlass", ""), bytes);
                                    }));
        });
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = xlassMap.get(name);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/wudj/Workspace/avizii/glint/glint-jvm/doc/hello.xar");
        XarLoader loader = new XarLoader(new URL[]{file.toURL()});
        Class<?> clazz = loader.loadClass("Hello");
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(instance);
    }
}
