package com.avizii.glint.annotation;

import java.lang.annotation.*;

/** @Author : Avizii @Create : 2021.05.25 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {

  String name();

  String[] alias() default "";
}
