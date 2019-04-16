package com.cyc.demo1;

import com.cyc.demo1.config.MyConfig;
import com.cyc.demo1.enhance.SignalInterfaceA;
import com.cyc.demo1.eventservice.A;
import com.cyc.demo1.waittoadvice.AnnotationAdviceService;
import com.cyc.demo1.waittoadvice.Aservice;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@MapperScan("com.cyc.demo1.dao")
@Slf4j
public class Demo1Application implements CommandLineRunner {

    @Autowired
    A thisIsTest;

    @Autowired
    MyConfig myConfig;

    @Autowired
    SignalInterfaceA signalInterfaceA;

    @Autowired
    Aservice aservice;

    @Autowired
    AnnotationAdviceService annotationAdviceService;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Demo1Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        signalInterfaceA.doSomething();
        log.error("{}", myConfig);
        aservice.serviceA1();
        annotationAdviceService.annotationAdviceService();
        annotationAdviceService.exceptionService();

    }
}
