package com.cyc.demo1.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import com.cyc.demo1.A;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class MyPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof A) {
            ((A)bean).setBeanName(beanName);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof A) {
            log.error("{}", ((A) bean).getBeanName());

        }

        return bean;
    }
}
