package com.cyc.demo1.service.imp;

import com.cyc.demo1.exception.RetryAnotherSourceException;
import org.springframework.stereotype.Service;

import com.cyc.demo1.service.InvoiceVerify;
import com.cyc.demo1.service.InvoiceVerifyRequest;
import com.cyc.demo1.service.InvoiceVerifyResponse;

/**
 * @author chenyuchuan
 */
@Service("YB_YZ")
public class InvoiceVerifyA implements InvoiceVerify {
    @Override
    public InvoiceVerifyResponse process(InvoiceVerifyRequest invoiceVerifyRequest) {
        String invoiceCode = invoiceVerifyRequest.getInvoiceCode();
        if ("1".equals(invoiceCode)) {
            throw new RetryAnotherSourceException();
        }

        return InvoiceVerifyResponse.builder().valid("false").build();
    }
}
