package com.gregory.learning.open_csv.custom;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class Writer<T> {

  private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
  private static final String CSV_SUFFIX = ".csv";

  public Path writeToCsv(List<T> objects, String fileName) {

    String filePath =
        TEMP_DIR + File.separator + fileName + CSV_SUFFIX;

    try {
      FileWriter fileWriter = new FileWriter(filePath);
      StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(fileWriter).withMappingStrategy(new EmployeeMappingStrategy(new String[]{"Age", "Name"}))
          .build();
      beanToCsv.write(objects);
      fileWriter.close();
    } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
      throw new RuntimeException(e.getCause());
    }
    return Path.of(filePath);
  }
}
