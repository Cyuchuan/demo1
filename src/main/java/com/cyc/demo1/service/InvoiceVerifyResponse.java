package com.cyc.demo1.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
@Builder
public class InvoiceVerifyResponse {

    private String valid;

    private String code;
}
