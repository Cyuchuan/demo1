package com.cyc.demo1;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.cyc.demo1.dto.Pojo;
import com.cyc.demo1.eventservice.A;
import com.cyc.demo1.eventservice.B;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import com.cyc.demo1.entity.User3;
import com.cyc.demo1.exception.NoBatchProcessException;
import com.cyc.demo1.listener.CustomListener1;
import com.cyc.demo1.listener.Service;
import com.cyc.demo1.node.Node;
import com.cyc.demo1.node.Packet;
import com.cyc.demo1.random.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class NormalTest {

    @Test
    public void test() {

        boolean annotationDeclaredLocally =
            AnnotationUtils.isAnnotationDeclaredLocally(Order.class, CustomListener1.class);
        log.error("{}", annotationDeclaredLocally);
    }

    @Test
    public void threadPoolExecutorTest() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int i = threadGroup.activeCount();
        Thread[] threads = new Thread[i];
        threadGroup.enumerate(threads);

        // ThreadPoolExecutor threadPoolExecutor =
        // new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        //
        // threadPoolExecutor.execute(new Runnable() {
        // @Override
        // public void run() {
        // log.error("debug 线程池 ThreadPoolExecutor");
        // }
        // });

    }

    @Test
    public void threadLocalTest() {
        InheritableThreadLocal<String> objectInheritableThreadLocal = new InheritableThreadLocal<>();
        objectInheritableThreadLocal.set("父线程的值");

        new Thread(new Runnable() {
            @Override
            public void run() {
                objectInheritableThreadLocal.set("A");
                String s = objectInheritableThreadLocal.get();
                log.error("{}", s);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                objectInheritableThreadLocal.set("B");

                String s = objectInheritableThreadLocal.get();
                log.error("{}", s);
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.error("{}", objectInheritableThreadLocal.get());
    }

    @Test
    public void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(30, 30, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    log.error("线程:" + Thread.currentThread() + " 进入等待");
                    cyclicBarrier.await();
                    log.error("线程:" + Thread.currentThread() + " 结束等待");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };
        while (true) {
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1() {

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());

        Calendar instance1 = Calendar.getInstance();
        instance.setTime(new Date());

        boolean b = instance.get(0) == instance1.get(0) && instance.get(1) == instance1.get(1)
            && instance.get(6) == instance1.get(6);

    }

    @Test
    public void testTimeout() {
        DateTime dateTime = new DateTime(System.currentTimeMillis());
        boolean beforeNow = dateTime.isBeforeNow();
        log.error("{}", beforeNow);
    }

    @Test
    public void methodHandleTest() throws Throwable {
        Pojo pojo = new Pojo();
        pojo.setNotValidation("这是一次验证");
        MethodHandles.Lookup lookup = Pojo.getLookup();
        MethodType.methodType(String.class);
        MethodHandle notValidation = lookup.findGetter(Pojo.class, "notValidation", String.class);
        Object invoke = notValidation.invoke(pojo);
        log.error("{}", invoke);
    }

    @Test
    public void methodServiceTest() throws Exception {
        A a = new A();
        B b = new B();
        Method service = Service.class.getMethod("service");
        service.invoke(a);
        service.invoke(b);

    }

    @Test
    public void methodHandleServiceTest() throws Throwable {
        A a = new A();
        B b = new B();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(void.class);

        MethodHandle aservice1 = lookup.findVirtual(A.class, "service", methodType);
        MethodHandle bservice1 = lookup.findVirtual(B.class, "service", methodType);
        Object invoke = aservice1.invoke(a);
        bservice1.invoke(b);
    }

    @Test
    public void exceptionTest() {
        new NoBatchProcessException();
    }

    @Test
    public void stringTokenTest() {
        StringTokenizer stringTokenizer = new StringTokenizer("dasda,dsadasd;fdsiufhsi,;dasdsada", ",;");
        while (stringTokenizer.hasMoreTokens()) {
            log.error("{}", stringTokenizer.nextToken());
        }

        String[] string = new String("asdfgh").split(".*+");
        log.error("\n");
        for (String s : string) {
            log.error("{}", s);
        }
    }

    @Test
    public void test3() {

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLongBetween(-100, 10);
            log.error("nextLongBetween{}", l);

        }

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLong(14);
            log.error("nextLong(bound){}", l);

        }

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLong();
            log.error("nextLong{}", l);

        }

    }

    @Test
    public void validationTest() {
        log.error("开始");
        User3 user = new User3();
        user.setName("123");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<User3>> validate = validator.validate(user);

        for (ConstraintViolation<User3> user3ConstraintViolation : validate) {
            log.error("{}", user3ConstraintViolation.getMessage());
        }
    }

    @Test
    public void methodHandlesTest() {

    }

    @Test
    public void nodeTest() throws InterruptedException {
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");
        Node node7 = new Node("7");
        Node node8 = new Node("8");
        Node node9 = new Node("9");
        Node node10 = new Node("10");

        node1.link(node2, node3, node6);
        node2.link(node3, node10);
        node4.link(node6);
        node5.link(node6);
        node6.link(node8);
        node7.link(node8);
        node8.link(node9, node10);

        // 设置节点7为终止结点
        Packet packet = new Packet(3, 0, node7, node1, new ArrayList<>());
        // 从节点1出发，尝试跳跃次数为3
        node1.process(packet);

        Packet packet1 = new Packet(3, 0, node7, node1, new ArrayList<>());
        node1.process(packet1);

        Thread.sleep(5000);

        log.error("\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n{}:{}\n", node1.getName(),
            node1.getMaxBlockSize(), node2.getName(), node2.getMaxBlockSize(), node3.getName(), node3.getMaxBlockSize(),
            node4.getName(), node4.getMaxBlockSize(), node5.getName(), node5.getMaxBlockSize(), node6.getName(),
            node6.getMaxBlockSize(), node7.getName(), node7.getMaxBlockSize(), node8.getName(), node8.getMaxBlockSize(),
            node9.getName(), node9.getMaxBlockSize(), node10.getName(), node10.getMaxBlockSize());
    }
}
