package com.kuafoo4j.configuration;

import com.kuafoo4j.core.client.ILoadBalance;
import com.kuafoo4j.loadbalance.PhoenixRoundLoadBalance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 */
@Configuration
public class LoadBalanceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ILoadBalance.class)
    public ILoadBalance loadBalance() {
        return new PhoenixRoundLoadBalance();
    }



}
