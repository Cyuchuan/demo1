package com.cyc.demo1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.EnableAsync;

import com.cyc.demo1.adviceservice.AnnotationAdviceService;
import com.cyc.demo1.adviceservice.Aservice;
import com.cyc.demo1.autowirebyinterface.SignalInterfaceA;
import com.cyc.demo1.config.MyConfig;
import com.cyc.demo1.config.MyConfigInMyProperties;
import com.cyc.demo1.dto.Result;
import com.cyc.demo1.eventservice.A;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@MapperScan("com.cyc.demo1.dao")
@Slf4j
public class Demo1Application {

    @Autowired
    A thisIsTest;

    @Autowired
    MyConfig myConfig;

    @Autowired
    MyConfigInMyProperties myConfigInMyProperties;

    @Autowired
    SignalInterfaceA signalInterfaceA;

    @Autowired
    Aservice aservice;

    @Autowired
    AnnotationAdviceService annotationAdviceService;

    @Autowired
    private ExpirableLockRegistry lockRegistry;

    public static void main(String[] args) {
        for (String arg : args) {
            log.error("{}", arg);

        }
        ConfigurableApplicationContext run = SpringApplication.run(Demo1Application.class, args);

        Demo1Application bean = run.getBean(Demo1Application.class);
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map map = new HashMap();
        map.put(Result.class, ResultEditor.class);
        customEditorConfigurer.setCustomEditors(map);
        return customEditorConfigurer;
    }

    @Bean
    public DefaultLockRepository lockRepository(DataSource dataSource) throws UnknownHostException {
        return new DefaultLockRepository(dataSource, InetAddress.getLocalHost().getHostAddress());
    }

    @Bean
    public JdbcLockRegistry lockRegistry(LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry lockRegistry) {
        return new LockRegistryLeaderInitiator(lockRegistry);
    }

    @Bean
    public MessageSource<?> integerMessageSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(new AtomicInteger());
        source.setMethodName("getAndIncrement");
        return source;
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow myFlow() {
        return IntegrationFlows.from(integerMessageSource(), c -> c.poller(Pollers.fixedRate(100)))
            .channel(this.inputChannel()).filter((Integer p) -> p > 0).transform(Object::toString)
            .channel(MessageChannels.queue()).get();
    }
}
