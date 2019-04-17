package com.cyc.demo1.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler({RuntimeException.class})
    public String runtimeExceptionHandler(RuntimeException e) {
        return e.getMessage();
    }
}
