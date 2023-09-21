package com.kuafoo4j.configuration;

import com.kuafoo4j.core.client.*;
import com.kuafoo4j.loadbalance.PhoenixRoundLoadBalance;
import com.kuafoo4j.loadbalance.ServerDiscoveryAdaptor;
import com.kuafoo4j.phoenix.commom.core.api.IPhoenixServerDiscovery;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动装配
 *
 * @author kuafoo4j
 */
@Configuration
public class LoadBalanceAutoConfiguration {

    /**
     * 获取所有的RestTemplate
     */
    @Autowired
    private List<RestTemplate> restTemplateList = new ArrayList<>();

    /**
     * SmartInitializingSingleton: Springboot扩展点,当所有单例 bean 都初始化完成以后，
     * Spring的IOC容器会回调该接口的 afterSingletonsInstantiated()方法。
     * <p>
     * 使用给RestTemplate添加loadBalance拦截器
     *
     * @param loadBalanceInterceptor
     * @return
     */
    @Bean
    public SmartInitializingSingleton loadBalancedRestTemplateInitializer(LoadBalanceInterceptor loadBalanceInterceptor) {
        return () -> {
            for (RestTemplate restTemplate : this.restTemplateList) {
                List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
                // 添加loadBalance拦截器
                interceptors.add(loadBalanceInterceptor);
                restTemplate.setInterceptors(interceptors);
            }
        };
    }


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
     * 服务实例查询 Phoenix注册中心适配
     *
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
        return new LoadBalanceInterceptor(loadBalanceClient, httpClient);
    }

}
