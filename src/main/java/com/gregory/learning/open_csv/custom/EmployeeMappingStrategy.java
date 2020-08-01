package com.gregory.learning.open_csv.custom;

import com.opencsv.CSVReader;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeMappingStrategy implements MappingStrategy<Employee> {

  // these will be the .csv headers
  private final String[] headersToMap;

  public EmployeeMappingStrategy(String[] headersToMap) {
    this.headersToMap = headersToMap;
  }

  @Override
  public void captureHeader(CSVReader csvReader)
      throws IOException, CsvRequiredFieldEmptyException {

  }

  // this method is used for writing the header line in the .csv file
  @Override
  public String[] generateHeader(Employee employee) throws CsvRequiredFieldEmptyException {
    return headersToMap;
  }

  @Override
  public Employee populateNewBean(String[] strings)
      throws CsvBeanIntrospectionException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, CsvConstraintViolationException, CsvValidationException {
    return null;
  }

  @Override
  public void setType(Class<? extends Employee> aClass) throws CsvBadConverterException {

  }

  // this method is used to collate each line in the .csv output
  // you are provided with the instance of your POJO to write!
  @Override
  public String[] transmuteBean(Employee employee)
      throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
    // create an array of stings (one for each field of the pojo)
    // this should be exactly the same size as the header array
    String[] csvStrings = new String[headersToMap.length];

    // create a map between header name and method to invoke
    // i've assumed the header name will have an associated getter
    Map<String, Method> headerMethodMap = new HashMap<>();
    List<Method> getterMethods = Arrays.stream(employee.getClass().getMethods())
        .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
        .collect(
            Collectors.toList());
    Arrays.stream(headersToMap).forEach(header -> {
      boolean matchFound = false;
      for (Method method : getterMethods) {
        if (method.getName().contains(header)) {
          headerMethodMap.put(header, method);
          matchFound = true;
        }
      }
      if (!matchFound) throw new RuntimeException("No match found :( for header: " + header);
    });

    // invoke each method on the instance of our POJO and add to our string array
    int count = 0;
    for (String header : headersToMap) {
      try {
        csvStrings[count] = headerMethodMap.get(header).invoke(employee).toString();
        count ++;
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException("Could be bad :(", e);
      }
    }
    return csvStrings;
  }
}
