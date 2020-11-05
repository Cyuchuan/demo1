package com.cyc.demo1.service;

import java.util.UUID;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author atchen
 */
@Service
@CacheConfig(cacheManager = "guavaCacheManager", cacheNames = "testCache1")
public class CacheDaoService {
    @Cacheable
    public String getCache(String a) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {

        }

        return UUID.randomUUID().toString();
    }

}
