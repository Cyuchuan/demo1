package com.cyc.demo1.eventservice;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cyc.demo1.listener.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class B implements Service {

    @Override
    @Async
    public void service() {
        log.error("进行B服务");
    }
}
