package com.cyc.demo1.service;

/**
 * 多数据源接口
 * 
 * @author chenyuchuan
 */
public interface InvoiceVerify {

    InvoiceVerifyResponse process(InvoiceVerifyRequest invoiceVerifyRequest);
}
