package com.cyc.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyc.demo1.service.InvoiceVerifyManager;
import com.cyc.demo1.service.InvoiceVerifyRequest;
import com.cyc.demo1.service.InvoiceVerifyResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InvoiceTest {
    @Autowired
    InvoiceVerifyManager invoiceVerifyManager;

    @Test
    public void test() {
        InvoiceVerifyRequest build = InvoiceVerifyRequest.builder().invoiceCode("1").build();
        for (int i = 0; i < 30; i++) {
            InvoiceVerifyResponse invoiceVerifyResponse = invoiceVerifyManager.invokeAllSource(build);

            log.error("{}", invoiceVerifyResponse);
        }

    }
}
