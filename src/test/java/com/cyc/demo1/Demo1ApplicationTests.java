package com.cyc.demo1;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyc.demo1.dao.TestFieldMapper;
import com.cyc.demo1.dto.Pojo;
import com.cyc.demo1.entity.User3;
import com.cyc.demo1.invoker.ServiceInterface;
import com.cyc.demo1.service.ValidationService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class Demo1ApplicationTests {
    @Autowired
    private TestFieldMapper testFieldMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Validator validator;

    @Autowired
    private ValidationService validationService;

    @Autowired
    LockRegistry lockRegistry;

    @Autowired
    @Qualifier("httpInvokerProxyFactoryBean")
    ServiceInterface httpInvokerProxyFactoryBean;

    @Test
    public void contextLoads() {
        Lock key1 = lockRegistry.obtain("key1");

        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        Runnable runnable = () -> {
            try {
                key1.lock();
                log.info("{}上key1锁", Thread.currentThread());

            } catch (Exception e) {
                log.error("", e);
            } finally {
                key1.unlock();
            }
        };

        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);

        try {
            key1.lock();
            log.info("上key1锁");

        } catch (Exception e) {
            log.error("", e);
        } finally {
            key1.unlock();
        }

    }

    @Test
    public void tes123() {
        ServiceInterface serviceInterface = this.httpInvokerProxyFactoryBean;
        serviceInterface.print();
    }

    @Test
    public void contextLoads1() {
        Lock key1 = lockRegistry.obtain("key1");

        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        Runnable runnable = () -> {
            try {
                key1.lock();
                log.info("{}上key1锁", Thread.currentThread());

            } catch (Exception e) {
                log.error("", e);
            } finally {
                key1.unlock();
            }
        };

        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);

        try {
            key1.lock();
            log.info("上key1锁");

        } catch (Exception e) {
            log.error("", e);
        } finally {
            key1.unlock();
        }

    }

    @Test
    public void validationTest() {
        log.error("开始");
        User3 user = new User3();
        user.setName("123");
        Set<ConstraintViolation<User3>> asd = validator.validate(user);

        for (ConstraintViolation<User3> stringConstraintViolation : asd) {
            log.error("{}", stringConstraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation() {
        Pojo pojo = new Pojo();
        pojo.setNotValidation("");
        pojo.setId("");
        pojo.setFileName("1.txt");
        pojo.setFileType("g1");
        pojo.setDate("20180808");

        validationService.validatePojo(pojo);
        ResponseEntity<String> forEntity =
            restTemplate.getForEntity("http://localhost:" + port + "/pojo", String.class, pojo);
        log.error("{}", forEntity);

    }

}
