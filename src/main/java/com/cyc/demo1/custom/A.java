package com.cyc.demo1.custom;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class A {
    public void test() {
        log.error("A的test方法");
    }
}
