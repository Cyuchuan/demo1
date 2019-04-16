package com.cyc.demo1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cyc.demo1.enhance.SignalInterfaceA;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@MapperScan("com.cyc.demo1.dao")
@Slf4j
public class Demo1Application implements CommandLineRunner {

    @Autowired
    A thisIsTest;

    @Autowired
    SignalInterfaceA signalInterfaceA;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Demo1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        signalInterfaceA.doSomething();
    }
}
