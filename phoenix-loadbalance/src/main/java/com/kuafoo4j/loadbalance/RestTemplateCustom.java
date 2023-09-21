package com.kuafoo4j.loadbalance;

import com.kuafoo4j.core.client.LoadBalanceInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-21 17:44
 */
public class RestTemplateCustom {

    private List<RestTemplate> restTemplateList = new ArrayList<>();

    private LoadBalanceInterceptor loadBalanceInterceptor;

    public void custom() {

    }

}
