package com.gregory.learning.simple_csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CSVWritable {

  private List<String> fields;
  private String delimeter = ",";
  private String qualifier = "\"";
  private String lineBreak = "\n";

  public CSVWritable() {
    initFields();
  }

  protected void initFields() {
    if (fields == null) {
      fields = new ArrayList<>();
    }
    Arrays.stream(this.getClass().getDeclaredFields()).forEach(field ->
        fields.add(field.getName().toUpperCase()));
  }

  abstract protected List<String> writeToCsvLine();

  protected String writeToCsv(List<CSVWritable> csvWritables) {
    StringBuilder sb = new StringBuilder();

    fields.forEach(field -> {
      sb.append(qualifier);
      sb.append(field);
      sb.append(qualifier);
      sb.append(delimeter);
    });

    sb.setLength(sb.length() - 1);
    sb.append(lineBreak);

    csvWritables.stream().forEach(writable -> {
      List<String> lines = writable.writeToCsvLine();
      if (fields.size() != lines.size()) {
        throw new RuntimeException("Mismatch in fields provided and csv line elements");
      }
      lines.stream().forEach(line -> {
        sb.append(qualifier);
        sb.append(line);
        sb.append(qualifier);
        sb.append(delimeter);
      });
      sb.setLength(sb.length() - 1);
      sb.append(lineBreak);
    });
    return sb.toString();
  }
}
