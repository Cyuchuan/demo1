package com.cyc.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyc.demo1.service.CacheDaoService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceStart {
    @Autowired
    CacheDaoService cacheDaoService;

    @Test
    public void tes123() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        String a = cacheDaoService.getCache("a");
        log.error("耗时:{}  a={}", System.currentTimeMillis() - startTime, a);

        startTime = System.currentTimeMillis();
        a = cacheDaoService.getCache("a");
        log.error("耗时:{}  a={}", System.currentTimeMillis() - startTime, a);

        Thread.sleep(5000L);
        startTime = System.currentTimeMillis();
        a = cacheDaoService.getCache("a");
        log.error("耗时:{}  a={}", System.currentTimeMillis() - startTime, a);
    }
}
