package com.avizii.glint;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.github.houbb.word.checker.util.EnWordCheckers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** @author : Avizii */
public class WordFilter {

  public static void main(String[] args) {
    //
    String path =
        "/Users/wudj/Workspace/avizii/glint/glint-engine/src/main/resources/pdf/wordcount-value.csv/part-00000-279867a1-dde3-4eee-9f88-2fd2103831f2-c000.csv";

    String finalPath = "/Users/wudj/Workspace/avizii/glint/wordcount_2.csv";

    List<String> newLines = new ArrayList<>(20000);

    for (String line : FileReader.create(new File(path)).readLines()) {
      String word = line.split(",")[0];
      boolean correct = EnWordCheckers.isCorrect(word);
      if (correct) {
        newLines.add(line);
      }
    }

    FileWriter writer = FileWriter.create(new File(finalPath));
    writer.appendLines(newLines);
  }
}
