package com.cyc.demo1.listener;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Component
@Order(1)
@Slf4j
public class CustomListener2 implements Listener {
    @Override
    public void afterChunk() {
        log.error("进行自定义的监听器方法afterChunk2");
    }

    @Override
    public void beforeChunk() {
        log.error("进行自定义的监听器方法beforeChunk2");

    }
}
