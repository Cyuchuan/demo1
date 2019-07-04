package com.cyc.demo1.niodemo;

/**
 * @author chenyuchuan
 */
public interface IMessageProcessor {

    public void process(Message message, WriteProxy writeProxy);

}
