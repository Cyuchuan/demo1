package com.cyc.demo1.autowirebyinterface;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class SignalInterfaceC implements SignalInterface {
    @Override
    public void doSomething() {
        log.error("C服務");
    }
}
