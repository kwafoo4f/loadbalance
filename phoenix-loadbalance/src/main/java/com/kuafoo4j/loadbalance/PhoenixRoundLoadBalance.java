package com.kuafoo4j.loadbalance;

import com.kuafoo4j.core.client.ILoadBalance;
import com.kuafoo4j.core.pojo.Server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法
 */
public class PhoenixRoundLoadBalance implements ILoadBalance {
    private Map<String,AtomicInteger> roundChooseMap = new ConcurrentHashMap<>();

    @Override
    public Server choose(List<Server> servers, String serviceName) {
        AtomicInteger index = roundChooseMap.computeIfAbsent(serviceName, (key) -> new AtomicInteger(0));
        int choose = casGetChoose(index, servers.size());
        return servers.get(choose);
    }

    public int casGetChoose(AtomicInteger index,int size) {
        int current = index.incrementAndGet();
        for (;;) {
            int choose = current % size;
            boolean succ = index.compareAndSet(current, choose);
            if (succ) {
                return choose;
            }
        }
    }

}
