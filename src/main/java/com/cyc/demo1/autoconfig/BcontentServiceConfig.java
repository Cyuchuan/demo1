package com.cyc.demo1.autoconfig;

import org.springframework.context.annotation.Bean;

import com.cyc.demo1.service.BcontentService;
import com.cyc.demo1.service.ContentService;

/**
 * @author chenyuchuan
 */
public class BcontentServiceConfig {

    @Bean
    public ContentService bContentService() {
        return new BcontentService();
    }
}
