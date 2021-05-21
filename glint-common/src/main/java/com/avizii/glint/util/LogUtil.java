package com.avizii.glint.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author : Avizii
 * @Create : 2021.05.21
 */
public class LogUtil {

    public static String formatExceptionMessage(Throwable e) {
        return e + "\n" +
                Arrays.stream(e.getStackTrace())
                        .map(t -> "\t" + t.toString())
                        .collect(Collectors.joining("\n"));
    }

}
