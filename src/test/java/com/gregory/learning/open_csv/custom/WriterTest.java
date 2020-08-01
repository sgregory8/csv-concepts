package com.gregory.learning.open_csv.custom;

import java.util.Collections;
import org.junit.Test;

public class WriterTest {

  private Writer<Employee> writer = new Writer<>();

  @Test
  public void writeToCsv() {
    Employee employee = new Employee("Sam", 28);
    writer.writeToCsv(Collections.singletonList(employee), "employees");
  }
}