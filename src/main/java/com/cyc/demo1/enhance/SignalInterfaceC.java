package com.cyc.demo1.enhance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
