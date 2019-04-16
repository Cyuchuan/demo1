package com.cyc.demo1.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author chenyuchuan
 */
@Component
@Slf4j
public class DefaultListener implements Listener {
    private OrderComponent<Listener> listeners = new OrderComponent();

    private DefaultListener(List<? extends Listener> listeners) {
        this.listeners.setComponents(listeners);

    }

    @Override
    public void afterChunk() {
        Iterator iterator = this.listeners.reverse();

        while (iterator.hasNext()) {
            Listener listener = (Listener)iterator.next();
            listener.afterChunk();
        }

    }

    @Override
    public void beforeChunk() {
        Iterator iterator = this.listeners.iterator();

        while (iterator.hasNext()) {
            Listener listener = (Listener)iterator.next();
            listener.beforeChunk();
        }

    }

}
