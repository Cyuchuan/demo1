package com.cyc.demo1.custom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Component
@Slf4j
public class MyJob extends CustomJob {

    int a;
    int b;
    private ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition1 = reentrantLock.newCondition();
    Condition condition2 = reentrantLock.newCondition();

    @Override
    public void custom() {
        
        log.error("我的方法");
    }
}
