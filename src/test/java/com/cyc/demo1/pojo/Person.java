package com.cyc.demo1.pojo;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
public class Person {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "age")
    private int age;
}
