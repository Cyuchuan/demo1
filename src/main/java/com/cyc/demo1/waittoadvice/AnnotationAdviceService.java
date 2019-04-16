package com.cyc.demo1.waittoadvice;

import com.cyc.demo1.aspect.AnnotationToAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void exceptionService() {
        log.error("进行exceptionService服务");
        throw new RuntimeException("运行时异常");
    }
}
