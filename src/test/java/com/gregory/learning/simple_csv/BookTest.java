package com.gregory.learning.simple_csv;

import java.util.Arrays;
import org.junit.Test;

public class BookTest {

  @Test
  public void writeToCsvLine() {
    Book book = new Book("Sam", 44);
    Book anotherBook = new Book("Amber", 55);
    System.out.println(book.writeToCsv(Arrays.asList(book, anotherBook)));
  }
}