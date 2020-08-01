package com.gregory.learning.simple_csv;

import java.util.Arrays;
import java.util.List;

public class Book extends CSVWritable {

  private String author;
  private int pages;

  public Book(String author, int pages) {
    this.author = author;
    this.pages = pages;
  }

  @Override
  protected List<String> writeToCsvLine() {
    return Arrays.asList(author, String.valueOf(pages));
  }
}
