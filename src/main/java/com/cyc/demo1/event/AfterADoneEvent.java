package com.cyc.demo1.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * @author chenyuchuan
 */
public class AfterADoneEvent extends ApplicationEvent {

    public AfterADoneEvent(Object source) {
        super(source);
    }
}
