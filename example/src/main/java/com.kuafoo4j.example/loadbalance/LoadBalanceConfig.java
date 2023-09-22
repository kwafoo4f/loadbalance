package com.kuafoo4j.example.loadbalance;

import com.kuafoo4j.core.client.ILoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 注入自定义随机负载均衡算法
 * @author: zk
 * @date: 2023-09-22 9:57
 */
@Slf4j
@Configuration
public class LoadBalanceConfig {

    @Bean
    public ILoadBalance randomLoadBalance() {
        log.info("负载均衡算法: randomLoadBalance");
        return new RandomLoadBalance();
    }

}
