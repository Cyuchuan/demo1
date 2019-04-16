package com.cyc.demo1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author chenyuchuan
 */
@Aspect
@Component
@Slf4j
public class AspectBefore {
    @Before("execution(* com.cyc.demo1.waittoadvice.Aservice.*())")
    public void test() {
        log.error("服务前增强");
    }

    @After("execution(* com.cyc.demo1.waittoadvice.Aservice.*())")
    public void afterTest() {
        log.error("服务后增强");
    }
}
