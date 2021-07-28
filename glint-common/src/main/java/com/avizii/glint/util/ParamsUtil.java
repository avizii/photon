package com.avizii.glint.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class ParamsUtil {

  private static final char PARAM_START_FLAG = '-'; // 参数开始标记
  private static final char[] PARAM_SPLIT_FLAG = new char[] {'\t', ' '}; // 参数间隔标记

  public static Map<String, String> parse(String[] arr) {
    return parse(String.join(" ", arr));
  }

  public static Map<String, String> parse(String s) {
    Map<String, String> paramsMap = new HashMap<>();
    char[] paramsCharArray = s.toCharArray();
    int len = paramsCharArray.length - 1;
    int mark = 0;
    char c;
    do {
      c = paramsCharArray[mark];
      if (isParamStart(c)) {
        mark = paramPairParse(paramsCharArray, mark + 1, paramsMap);
      } else if (!isParamSplit(c)) {
        StringBuilder param = new StringBuilder();
        mark = paramOnlyParse(paramsCharArray, mark, param);
      }
      mark++;
    } while (mark <= len);
    return paramsMap;
  }

  private static int paramPairParse(
      char[] paramsCharArray, int start, Map<String, String> collector) {
    StringBuilder key = new StringBuilder();
    StringBuilder value = new StringBuilder();
    char last = '"';
    boolean keyFlag = true;
    int mark = start;
    char cur;
    boolean breakFlag = false;
    boolean quoteValue = false;
    boolean quoteEnd = true;
    int len = paramsCharArray.length - 1;
    while (true) {
      cur = paramsCharArray[mark];
      if (!quoteValue && isParamStart(cur) && isParamSplit(last)) {
        // 回退一个位置，并退出
        mark--;
        breakFlag = true;
      } else if (isParamSplit(cur)) {
        // 碰到了空格或\t,如果在""间，需要特殊处理
        if (quoteValue) {
          // 是在""中
          value.append(cur);
        } else {
          // 不是""中时，如果正在读取value部分，说明碰到了结尾
          if (keyFlag || value.length() == 0) {
            keyFlag = false;
          } else {
            breakFlag = true;
          }
        }
      } else {
        if (isQuote(cur)) {
          if (!keyFlag) {
            quoteValue = true;
            if (!quoteEnd) {
              quoteEnd = true;
              breakFlag = true;
            } else {
              quoteEnd = false;
            }
          }

        } else {
          if (keyFlag) {
            key.append(cur);
          } else {
            value.append(cur);
          }
        }
      }
      if (breakFlag || mark >= len) {
        if (key.length() > 0) {
          if (!quoteEnd) {
            System.out.println("警告:" + key + "\"配对不正确");
          }
          collector.put(key.toString().trim(), value.toString().trim());
        }
        return mark;
      } else {
        mark++;
        last = cur;
      }
    }
  }

  private static int paramOnlyParse(char[] paramsCharArray, int start, StringBuilder collector) {
    char last = '"';
    int mark = start;
    char cur;
    boolean breakFlag = false;
    boolean quoteValue = false;
    boolean quoteEnd = true;
    int len = paramsCharArray.length - 1;
    while (true) {
      cur = paramsCharArray[mark];
      if (!quoteValue && isParamStart(cur) && isParamSplit(last)) {
        mark--;
        breakFlag = true;
      } else if (isParamSplit(cur)) {
        // 碰到了空格或\t,如果在""间，需要特殊处理
        if (quoteValue) {
          // 是在""中
          collector.append(cur);
        } else {
          // 不是""中时，如果正在读取value部分，说明碰到了结尾
          if (collector.length() != 0) {
            breakFlag = true;
          }
        }
      } else {
        if (isQuote(cur)) {
          quoteValue = true;
          if (!quoteEnd) {
            quoteEnd = true;
            breakFlag = true;
          } else {
            quoteEnd = false;
          }
        } else {
          collector.append(cur);
        }
      }
      if (breakFlag || mark >= len) {
        return mark;
      } else {
        mark++;
        last = cur;
      }
    }
  }

  private static boolean isParamSplit(char c) {
    return Arrays.binarySearch(PARAM_SPLIT_FLAG, c) >= 0;
  }

  private static boolean isParamStart(char c) {
    return c == PARAM_START_FLAG;
  }

  private static boolean isQuote(char c) {
    return '"' == c;
  }
}
