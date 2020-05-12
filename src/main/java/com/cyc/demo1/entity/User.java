package com.cyc.demo1.entity;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
public class User {
    @NotNull
    private String id;

    @Length(min = 0, max = 3)
    private String userName;

    @Email(regexp = ".*@qq\\.com")
    private String email;

    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private Date date;

    @Past
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private Date pastDate;

    @Future
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private Date futureDate;
}
