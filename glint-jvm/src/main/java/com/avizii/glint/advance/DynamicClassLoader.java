package com.avizii.glint.advance;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class DynamicClassLoader extends URLClassLoader {



    public DynamicClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }



}
