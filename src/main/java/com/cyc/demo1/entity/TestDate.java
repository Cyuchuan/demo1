package com.cyc.demo1.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TestDate {
    private Long id;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private Date time;

    private Date date;

    private Date datetime;
}