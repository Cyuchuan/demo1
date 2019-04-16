package com.cyc.demo1.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author chenyuchuan
 */
@Getter
public class AfterADoneEvent extends ApplicationEvent {
    private String objectString;

    public AfterADoneEvent(Object source) {
        super(source);
    }

    public AfterADoneEvent(String source,String a) {
        super(source);
        objectString = source;
    }
}
