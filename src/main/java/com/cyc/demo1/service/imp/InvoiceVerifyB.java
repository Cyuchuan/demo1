package com.cyc.demo1.service.imp;

import org.springframework.stereotype.Service;

import com.cyc.demo1.service.InvoiceVerify;
import com.cyc.demo1.service.InvoiceVerifyRequest;
import com.cyc.demo1.service.InvoiceVerifyResponse;

/**
 * @author chenyuchuan
 */
@Service("HH_YZ")
public class InvoiceVerifyB implements InvoiceVerify {
    @Override
    public InvoiceVerifyResponse process(InvoiceVerifyRequest invoiceVerifyRequest) {
        return InvoiceVerifyResponse.builder().valid("true").build();

    }
}
