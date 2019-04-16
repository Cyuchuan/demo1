package com.cyc.demo1.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyuchuan
 */
@Aspect
@Component
@Slf4j
public class AspectBefore {
    @Before("execution(void com.cyc.demo1.waittoadvice.*.*())")
    public void test() {
        log.error("服务前增强");
    }
}
