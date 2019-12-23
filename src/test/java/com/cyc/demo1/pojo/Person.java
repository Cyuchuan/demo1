package com.cyc.demo1.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "hh:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private Date dateTime;
}
