package com.cyc.demo1.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chenyuchuan
 */
@ConditionalOnProperty(prefix = "my", value = "enable", matchIfMissing = true)
@ConfigurationProperties(prefix = "my")
@Configuration
@Getter
@Setter
@ToString
public class MyConfig {
    private String name;

    private Integer age;

    private String address;

    private String hobbies;
}
