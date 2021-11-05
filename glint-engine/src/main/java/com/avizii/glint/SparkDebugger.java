package com.avizii.glint;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** @author : Avizii */
public class SparkDebugger {

  public static void main(String[] args) {
    SparkSession spark =
        SparkSession.builder()
            .appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value")
            .master("local[*]")
            .getOrCreate();

    List<String> list =
        Arrays.asList(
            "Advanced Programming in the UNIX Environment",
            "Computer Architecture A Quantitative Approach",
            "Computer Organization and Design RISC-V edition",
            "Linux System Programming",
            "Modern Operating Systems",
            "SICP",
            "The Art Of Unix Programming",
            "csapp",
            "The Linux Programming Interface");

    String base = "/Users/wudj/Workspace/avizii/glint/glint-engine/src/main/resources/pdf";

    String pattern = "^[A-Za-z]+$";
    String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]";
    Pattern p = Pattern.compile(regEx);

    String[] paths = list.stream().map(name -> base + "/" + name + ".txt").toArray(String[]::new);

    // input
    Dataset<String> text = spark.read().textFile(paths).as(Encoders.STRING());

    // 分词
    Dataset<String> words =
        text.flatMap(
            new FlatMapFunction<String, String>() {
              @Override
              public Iterator<String> call(String s) throws Exception {
                Matcher m = p.matcher(s);
                s = m.replaceAll(" ").trim();
                List<String> collect =
                    Arrays.stream(s.split(" "))
                        .map(String::toLowerCase)
                        .filter(word -> Pattern.matches(pattern, word))
                        .filter(word -> word.length() > 1)
                        .collect(Collectors.toList());
                return collect.iterator();
              }
            },
            Encoders.STRING());

    // 转化为<word, count>的形式
    Dataset<Row> countRow = words.groupBy("value").count();
    Dataset<Row> word_count = countRow.sort(functions.desc("count")).coalesce(1);
    Dataset<Row> word_count_2 = countRow.sort(functions.asc("value")).coalesce(1);

    // output
    word_count
        .write()
        .mode("overwrite")
        .csv(
            "/Users/wudj/Workspace/avizii/glint/glint-engine/src/main/resources/pdf/wordcount-count.csv");
    word_count_2
        .write()
        .mode("overwrite")
        .csv(
            "/Users/wudj/Workspace/avizii/glint/glint-engine/src/main/resources/pdf/wordcount-value.csv");
  }
}
