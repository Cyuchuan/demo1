package com.cyc.demo1.service;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyc.demo1.dto.Pojo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Controller
@Slf4j
public class ValidationService {

    @RequestMapping("pojo")
    public void validatePojo(@Valid Pojo pojo) {
        log.error("{}", pojo);
    }
}
