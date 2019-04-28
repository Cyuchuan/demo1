package com.cyc.demo1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Aspect
@Component
@Slf4j
public class AnotationAspect {
    @Before("@annotation(AnnotationToAdvice)&&args(s)")
    public void annotationBefore(Long s) {
        log.error("带特定注解的前置增强 {}", s);
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
