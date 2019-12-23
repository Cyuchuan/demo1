package com.cyc.demo1.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
@Builder
public class InvoiceVerifyRequest {

    private String invoiceCode;

    private String dataSource;
}
