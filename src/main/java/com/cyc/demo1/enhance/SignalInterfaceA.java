package com.cyc.demo1.enhance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
