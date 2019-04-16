package com.cyc.demo1.custom;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class CustomJob {

    protected void custom() {
        log.error("自定义方法");
    }
}
