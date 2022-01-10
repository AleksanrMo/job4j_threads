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
      String output = "";
      try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
          int data;
          while ((data = i.read()) > 0) {
              if (filter.test((char) data)) {
                  output += (char) data;
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return output;
    }

    public static void main(String[] args) {
      File file = new File("./tet.txt");
        ParseFile parseFile =  new ParseFile(file);
        System.out.println(parseFile.getContent(e -> true));

    }
}
