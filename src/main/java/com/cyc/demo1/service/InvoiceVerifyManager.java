package com.cyc.demo1.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cyc.demo1.exception.RetryAnotherSourceException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Service
@Slf4j
public class InvoiceVerifyManager {

    private Map<String, InvoiceVerify> verifyMap;

    InvoiceVerifyManager(Map<String, InvoiceVerify> invoiceVerifyMap) {
        LinkedHashMap<String, InvoiceVerify> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        for (Map.Entry<String, InvoiceVerify> verifyEntry : invoiceVerifyMap.entrySet()) {
            linkedHashMap.put(verifyEntry.getKey(), verifyEntry.getValue());
        }

        verifyMap = linkedHashMap;
    }

    public InvoiceVerifyResponse invokeAllSource(InvoiceVerifyRequest invoiceVerifyRequest) {
        InvoiceVerifyResponse invoiceVerifyResponse = null;
        InvoiceVerify invoiceVerify = verifyMap.get(invoiceVerifyRequest.getDataSource());

        if (invoiceVerify != null) {
            invoiceVerifyResponse = invoiceVerify.process(invoiceVerifyRequest);

        } else {
            for (Map.Entry<String, InvoiceVerify> verifyEntry : verifyMap.entrySet()) {
                InvoiceVerify value = verifyEntry.getValue();
                try {
                    invoiceVerifyResponse = value.process(invoiceVerifyRequest);
                    break;
                } catch (RetryAnotherSourceException e) {
                    log.info("切换另一个数据源");
                }

            }
        }

        return invoiceVerifyResponse;
    }
}
