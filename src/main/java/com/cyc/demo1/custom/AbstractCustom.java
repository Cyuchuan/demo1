package com.cyc.demo1.custom;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenyuchuan
 */
public abstract class AbstractCustom {
    @Autowired
    public A a;

    protected abstract void custom();
}
