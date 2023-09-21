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

    /**
     * 轮询算法选择一台机器
     * @param servers
     * @param serviceName
     * @return
     */
    @Override
    public Server choose(List<Server> servers, String serviceName) {
        AtomicInteger index = roundChooseMap.computeIfAbsent(serviceName, (key) -> new AtomicInteger(0));
        int choose = casGetChoose(index, servers.size());
        return servers.get(choose);
    }

    /**
     * cas
     * @param index
     * @param size
     * @return
     */
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
