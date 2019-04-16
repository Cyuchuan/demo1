package com.cyc.demo1.entity;

import com.cyc.demo1.validation.CustomValidation;

import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
public class User3 {
    @CustomValidation
    String name;
}
