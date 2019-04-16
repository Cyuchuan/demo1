package com.cyc.demo1.waittoadvice;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

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

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
