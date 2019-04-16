package com.cyc.demo1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenyuchuan
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class AdviceController {

    @ExceptionHandler({RuntimeException.class})
    public String runtimeExceptionHandler(RuntimeException e) {
        return e.getMessage();
    }
}
