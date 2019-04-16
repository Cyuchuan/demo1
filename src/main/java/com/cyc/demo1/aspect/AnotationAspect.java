package com.cyc.demo1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author chenyuchuan
 */
@Aspect
@Component
@Slf4j
public class AnotationAspect {
    @Before("@annotation(AnnotationToAdvice)")
    public void annotationBefore() {
        log.error("带特定注解的前置增强");
    }

    @Around(value = "@annotation(AnnotationToAdvice)&& args(s,..)")
    public void annotationAfterError(ProceedingJoinPoint proceedingJoinPoint, String s) {
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable e1) {
            log.error("{}", e1.getMessage());

        }
    }
}
