package com.cyc.demo1.controller;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ServiceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate template;

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

    @Test
    public void nlu() {
        ResponseEntity<NluTextRecognizeResponse> forEntity =
            template.getForEntity(base.toString() + "/nlu", NluTextRecognizeResponse.class);

        if (HttpStatus.OK.is2xxSuccessful()) {
            NluTextRecognizeResponse body = forEntity.getBody();

            log.error("{}", body);
        }

    }

    @Test
    public void nlu1() {
        NluTextRecognizeRequest nluTextRecognizeRequest = new NluTextRecognizeRequest();
        nluTextRecognizeRequest.setStrategyType("312");
        nluTextRecognizeRequest.setOperateType("312");
        nluTextRecognizeRequest.setNluQuestion("312");
        nluTextRecognizeRequest.setAsrText("312");
        nluTextRecognizeRequest.setRuleIdList(Lists.newArrayList("123"));
        nluTextRecognizeRequest.setCurrentNode("3123");

        HttpEntity<NluTextRecognizeRequest> objectHttpEntity = new HttpEntity<>(null);

        ResponseEntity<NluTextRecognizeResponse> exchange = template.exchange(base.toString() + "/nlu", HttpMethod.POST,
            objectHttpEntity, NluTextRecognizeResponse.class);

        if (exchange.getStatusCode().is2xxSuccessful()) {
            NluTextRecognizeResponse body = exchange.getBody();

            log.error("{}", body);
        }

    }
}