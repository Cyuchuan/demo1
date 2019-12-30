package com.cyc.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import com.cyc.demo1.adviceservice.AnnotationAdviceService;
import com.cyc.demo1.adviceservice.Aservice;
import com.cyc.demo1.autoconfig.EnableContentService;
import com.cyc.demo1.autowirebyinterface.SignalInterfaceA;
import com.cyc.demo1.config.MyConfig;
import com.cyc.demo1.config.MyConfigInMyProperties;
import com.cyc.demo1.eventservice.A;
import com.cyc.demo1.service.ContentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@MapperScan("com.cyc.demo1.dao")
@EnableScheduling
@EnableContentService()
@Slf4j
public class Demo1Application {

    @Autowired
    A thisIsTest;

    @Autowired
    MyConfig myConfig;

    @Autowired
    MyConfigInMyProperties myConfigInMyProperties;

    @Autowired
    SignalInterfaceA signalInterfaceA;

    @Autowired
    Aservice aservice;

    @Autowired
    AnnotationAdviceService annotationAdviceService;

    public static void main(String[] args) {
        for (String arg : args) {
            log.error("{}", arg);

        }
        ConfigurableApplicationContext run = SpringApplication.run(Demo1Application.class, args);

        ContentService bean = run.getBean(ContentService.class);
        log.error("{}", bean.content());

    }
}
