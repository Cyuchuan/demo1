package com.cyc.demo1.config;

import com.cyc.demo1.dto.Pojo;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyuchuan
 */
public class SelfAutoConfig {

    @Bean
    public Pojo pojo() {
        return Pojo.builder().date("20190427").id("1").fileName("test.txt").build();
    }
}