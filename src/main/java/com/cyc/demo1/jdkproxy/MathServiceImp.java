package com.cyc.demo1.jdkproxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class MathServiceImp implements MathService {
    @Override
    public long add(long a, long b) {
        return a + b;
    }

    @Override
    public long subtraction(long a, long b) {
        return a - b;
    }

    @Override
    public long multiplication(long a, long b) {
        return a * b;
    }

    @Override
    public long division(long a, long b) {
        return a / b;
    }
}
