package com.cyc.demo1.autowirebyinterface;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class SignalInterfaceA implements SignalInterface {
    private List<SignalInterface> otherSignalInterface;

    SignalInterfaceA(List<SignalInterface> signalInterfaces) {
        otherSignalInterface = signalInterfaces;
    }

    @Override
    public void doSomething() {
        for (SignalInterface signalInterface : otherSignalInterface) {
            signalInterface.doSomething();
        }
    }
}
