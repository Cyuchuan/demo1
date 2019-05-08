package com.cyc.demo1.adviceservice;

import org.springframework.stereotype.Service;

import com.cyc.demo1.aspect.annotation.AnnotationToAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Service
@Slf4j
public class AnnotationAdviceService {

    @AnnotationToAdvice
    public void annotationAdviceService() {
        log.error("进行AnnotationAdviceService服务");
    }

    @AnnotationToAdvice
    public void exceptionService(String s) {
        log.error("进行exceptionService服务,{}", s);
        throw new RuntimeException("运行时异常");
    }
}
