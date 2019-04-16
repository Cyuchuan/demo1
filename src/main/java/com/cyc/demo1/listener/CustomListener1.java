package com.cyc.demo1.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author chenyuchuan
 */
@Component
@Order(0)
@Slf4j
public class CustomListener1 implements Listener {
    @Override
    public void afterChunk() {
        log.error("进行自定义的监听器方法afterChunk1");
    }

    @Override
    public void beforeChunk() {
        log.error("进行自定义的监听器方法beforeChunk1");

    }
}
