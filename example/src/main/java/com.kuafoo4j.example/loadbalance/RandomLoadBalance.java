package com.kuafoo4j.example.loadbalance;

import com.kuafoo4j.core.client.ILoadBalance;
import com.kuafoo4j.core.pojo.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: 用户自定义随机负载均衡算法
 * @author: zk
 * @date: 2023-09-22 9:55
 */
public class RandomLoadBalance implements ILoadBalance {
    @Override
    public Server choose(List<Server> servers, String serviceName) {
        int choose = ThreadLocalRandom.current().nextInt(0, servers.size());
        return servers.get(choose);
    }
}
