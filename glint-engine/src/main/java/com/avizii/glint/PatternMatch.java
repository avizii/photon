package com.avizii.glint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/** @author : Avizii */
public class PatternMatch {

  public static void main(String[] args) {
    String test = ",asdv2213.123vgg!";
    System.out.println(filterString(test));
    //
  }

  public static boolean checkSpecialChar(String str) throws PatternSyntaxException {
    // 清除掉所有特殊字符
    String regEx = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.matches();
  }

  public static String filterString(String str) throws PatternSyntaxException {
    String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.replaceAll("").trim();
  }
}
