package com.cyc.demo1.autoconfig;

import org.springframework.context.annotation.Bean;

import com.cyc.demo1.service.AcontentService;
import com.cyc.demo1.service.ContentService;

/**
 * @author chenyuchuan
 */
public class AcontentServiceConfig {

    @Bean
    public ContentService aContentService() {
        return new AcontentService();
    }
}
