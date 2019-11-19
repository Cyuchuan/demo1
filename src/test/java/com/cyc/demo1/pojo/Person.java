package com.cyc.demo1.pojo;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyuchuan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @CsvDate("yyyyMMdd")
    @CsvBindByName(column = "brithday")
    private Date brithday;
    @CsvBindByName(column = "age")
    private int age;
    @CsvBindByName(column = "name")
    private String name;
}
