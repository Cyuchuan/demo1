package com.cyc.demo1.listener;

import org.springframework.core.annotation.Order;

public interface Listener {

    void afterChunk();

    void beforeChunk() ;

}

