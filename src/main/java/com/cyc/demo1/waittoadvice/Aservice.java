package com.cyc.demo1.waittoadvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chenyuchuan
 */
@Component
@Slf4j
public class Aservice implements ApplicationContextAware {
    ApplicationContext applicationContext;

    public void seviceA() {
        log.error("进行服务seviceA");
    }

    public void serviceA1() {
        log.error("进行服务seviceA1");
        boolean aopProxy = AopUtils.isAopProxy(this);
        log.error("是aop对象{}", aopProxy);
        Aservice bean = applicationContext.getBean(Aservice.class);
        bean.seviceA();
        bean.privateMethod();
        bean.protectedMethod();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void privateMethod() {
        log.error("这是一个private方法");
    }

    protected void protectedMethod() {
        log.error("这是一个protectMethod方法");
    }
}
