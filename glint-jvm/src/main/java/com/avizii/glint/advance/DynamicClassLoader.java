package com.avizii.glint.advance;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 自定义动态类加载器，支持如下特性：
 *     1.两种加载模式：
 *           a.双亲委派机制，先 parent classloader 加载，再 self classloader 加载(默认方式)
 *           b.破坏双亲委派机制，先 self classloader 加载，再 parent classloader 加载，用以处理jar包多版本冲突的问题
 *     2.支持类的卸载：
 *
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class DynamicClassLoader extends URLClassLoader {

    private final boolean loadParentFirst;

    public DynamicClassLoader(URL[] urls, ClassLoader parent, boolean loadParentFirst) {
        super(urls, parent);
        this.loadParentFirst = loadParentFirst;
    }

    public DynamicClassLoader(URL[] urls, boolean loadParentFirst) {
        super(urls);
        this.loadParentFirst = loadParentFirst;
    }

    public DynamicClassLoader(URL[] urls) {
        this(urls, true);
    }

    public DynamicClassLoader(URL[] urls, ClassLoader parent) {
        this(urls, parent, true);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return loadParentFirst ? loadClassParentFirst(name, resolve) : loadClassSelfFirst(name, resolve);
    }

    protected Class<?> loadClassParentFirst(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return loadClassFromParent(name, resolve);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            System.err.printf("class [%s] not found in parent classpath.%n", name);
        }
        return loadClassFromSelf(name, resolve);
    }

    protected Class<?> loadClassSelfFirst(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return loadClassFromSelf(name, resolve);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            System.err.printf("class [%s] not found in self classpath.%n", name);
        }
        return loadClassFromParent(name, resolve);
    }

    protected Class<?> loadClassFromParent(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clz = getParent().loadClass(name);
        if (clz != null) {
            System.out.printf("class [%s] is loading from parent classpath.%n", name);
            if (resolve) resolveClass(clz);
            return clz;
        }
        throw new ClassNotFoundException("can not found class : [" + name + "] in parent classpath.");
    }

    protected synchronized Class<?> loadClassFromSelf(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clz = findLoadedClass(name);
        if (clz != null) {
            System.out.printf("class [%s] was load from self classpath already.%n", name);
            if (resolve) resolveClass(clz);
            return clz;
        }
        clz = findClass(name);
        if (clz != null) {
            System.out.printf("class [%s] is loading from self classpath.%n", name);
            if (resolve) resolveClass(clz);
            return clz;
        }
        throw new ClassNotFoundException("can not found class : [" + name + "] in self classpath.");
    }


}
