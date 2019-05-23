package com.cyc.demo1.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class MathHandle implements InvocationHandler {
    private Object target;

    public MathHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.error("方法：{} 参数：{}", method.getName(), args);
        Object invoke = method.invoke(target, args);
        log.error("返回：{}", invoke);

        return invoke;
    }
}
