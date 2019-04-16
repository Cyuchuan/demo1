package com.cyc.demo1.block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Component
@Slf4j
public class CustomBlock {

    private ReentrantLock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    private int capacity = 50;

    private String[] stringArray = new String[capacity];

    private int count = 0;

    private int totalProvideCount;

    private int totalConsumeCount;

    public void stringProvider(String srcString) {
        try {
            lock.lock();

            while ((count + 1) > capacity) {
                notFull.await();
            }

            stringArray[count] = srcString;
            count++;
            totalProvideCount++;
            notEmpty.signal();
            log.error("线程： {}   生产字符串：  {} 现在还剩 {} 个元素 共生产元素{}个", Thread.currentThread(), srcString, count,totalProvideCount);
        } catch (Exception e) {
            log.error("{}", e);

        } finally {
            lock.unlock();
        }

    }

    public void consumerString() {
        try {
            lock.lock();

            while (count == 0) {
                notEmpty.await();
            }

            String consumerString = stringArray[--count];
            stringArray[count] = null;
            totalConsumeCount++;
            notFull.signal();

            log.error("线程 ：{} 消费字符串：{} 还剩{}个 共消费元素{}个", Thread.currentThread(), consumerString,count,totalConsumeCount);
        } catch (Exception e) {
            log.error("{}", e);

        } finally {
            lock.unlock();

        }
    }
}
