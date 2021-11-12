package com.avizii.photon.annotation;

import java.lang.annotation.*;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {

  String name();

  String[] alias() default "";
}
