package com.cyc.demo1.invoker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author atchen
 */
@Configuration
@Slf4j
public class InvokerConfig {

    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean() {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceUrl("http://127.0.0.1:8080/invoker");
        httpInvokerProxyFactoryBean.setServiceInterface(ServiceInterface.class);
        return httpInvokerProxyFactoryBean;
    }

    @Bean("/invoker")
    public HttpInvokerServiceExporter httpInvokerServiceExporter(ServiceInterface serviceInterfaceImpl) {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(serviceInterfaceImpl);
        httpInvokerServiceExporter.setServiceInterface(ServiceInterface.class);

        return httpInvokerServiceExporter;
    }
}
