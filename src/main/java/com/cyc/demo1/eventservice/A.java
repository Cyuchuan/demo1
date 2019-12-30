package com.cyc.demo1.eventservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cyc.demo1.event.AfterADoneEvent;
import com.cyc.demo1.listener.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Component
@Slf4j
@Setter
@Getter
public class A implements Service {
    @Autowired
    ApplicationContext applicationContext;

    private String beanName;

    @Override
    public void service() {
        log.error("进行A服务");
        log.error("A服务完成");

//        applicationContext.publishEvent(new AfterADoneEvent("A服务已完成后，给其他服务的对象"));

    }

}
