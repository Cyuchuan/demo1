package com.cyc.demo1.config;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author atchen
 * @date 2020/11/2
 */
@Configuration
public class RestTemplateConfig {
    private int httpMaxTotal = 100;

    private int httpMaxPerRoute = 100;

    private int httpConnectTimeout = 6000;

    private int httpReadTimeout = 6000;

    /**
     * restTemplate 的builder
     */
    @Bean
    @SuppressWarnings("all")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

    /**
     * restTemplate 的连接管理器，用来控制并发
     */
    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(httpMaxTotal); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(httpMaxPerRoute); // 每个主机的并发
        return poolingConnectionManager;
    }

    /**
     * restTemplate client的Builder
     */
    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

    /**
     * clientFactory 设置超时时间
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
        clientHttpRequestFactory.setConnectTimeout(httpConnectTimeout); // 连接超时，毫秒
        clientHttpRequestFactory.setReadTimeout(httpReadTimeout); // 读写超时，毫秒
        return clientHttpRequestFactory;
    }
}
