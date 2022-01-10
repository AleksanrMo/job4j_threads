package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final  class ParseFile implements Content {
    private final File file;

  public ParseFile(File file) {
      this.file = file;
  }

  @Override
    public synchronized String getContent(Predicate<Character> filter) {
      StringBuilder output = new StringBuilder();
      try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
          int data;
          while ((data = i.read()) > 0) {
              if (filter.test((char) data)) {
                  output.append((char) data);
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return output.toString();
    }
}
