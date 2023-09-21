package com.kuafoo4j.configuration;

import com.kuafoo4j.core.client.*;
import com.kuafoo4j.loadbalance.IPhoenixServerDiscovery;
import com.kuafoo4j.loadbalance.PhoenixRoundLoadBalance;
import com.kuafoo4j.loadbalance.ServerDiscoveryAdaptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 * @author kuafoo4j
 */
@Configuration
public class LoadBalanceAutoConfiguration {

    /**
     * 轮询算法-轮询算法
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ILoadBalance.class)
    public ILoadBalance loadBalance() {
        return new PhoenixRoundLoadBalance();
    }

    /**
     * 服务实例查询 TODO Phoenix适配
     * @param serverDiscovery
     * @return
     */
    @Bean
    public IServerDiscovery serverDiscovery(IPhoenixServerDiscovery serverDiscovery) {
        return new ServerDiscoveryAdaptor(serverDiscovery);
    }

    /**
     * 负载均衡客户端
     *
     * @param serverDiscovery
     * @param loadBalance
     * @return
     */
    @Bean
    public LoadBalanceClient loadBalanceClient(IServerDiscovery serverDiscovery,
                                               ILoadBalance loadBalance) {
        return new LoadBalanceClient(serverDiscovery, loadBalance);
    }

    /**
     * 负载均衡http处理
     *
     * @return
     */
    @Bean
    public LoadBalanceHttpClient loadBalanceHttpClient() {
        return new LoadBalanceHttpClient();
    }

    /**
     * 负载均衡http拦截器
     *
     * @param loadBalanceClient
     * @param httpClient
     * @return
     */
    @Bean
    public LoadBalanceInterceptor loadBalanceInterceptor(LoadBalanceClient loadBalanceClient,
                                                         LoadBalanceHttpClient httpClient) {
        return new LoadBalanceInterceptor(loadBalanceClient,httpClient);
    }

}
