package com.cyc.demo1.pojo;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
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
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Set<Date> dateSet;

    private Set<DialTimeZone> dialTimeZoneSet;

    @Data
    public static class DialTimeZone {
        @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
        @DateTimeFormat(pattern = "HH:mm:ss")
        private Date startTime;
        @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
        @DateTimeFormat(pattern = "HH:mm:ss")
        private Date endTime;
    }
}
