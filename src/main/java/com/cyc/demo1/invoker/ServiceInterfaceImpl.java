package com.cyc.demo1.invoker;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author atchen
 */
@Service
@Slf4j
public class ServiceInterfaceImpl implements ServiceInterface {

    @Override
    public void print() {
        log.info("打印个日志");
    }
}
