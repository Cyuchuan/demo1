package com.cyc.demo1.controller;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ServiceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private URL base;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/service");
    }

    @Test
    public void test() {
        ResponseEntity<String> forEntity = template.getForEntity(base.toString() + "/test", String.class);
        log.error("请求的url:{}", base);
        log.error("相应的header:{} status:{} body:{}", forEntity.getHeaders(), forEntity.getStatusCode(),
            forEntity.getBody());
        Assert.assertEquals("test", forEntity.getBody());
    }

    @Test
    public void runtimeExceptionTest() {
        ResponseEntity<String> forEntity = template.getForEntity(base.toString() + "/runtime", String.class);
        log.error("请求的url:{}", base);
        log.error("相应的header:{} status:{} body:{}", forEntity.getHeaders(), forEntity.getStatusCode(),
            forEntity.getBody());
        Assert.assertEquals("出现了运行时异常", forEntity.getBody());
    }
}