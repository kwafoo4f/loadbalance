package com.kuafoo4j.core.client;

import com.kuafoo4j.core.pojo.Server;

import java.util.List;

/**
 * 负载均衡器
 */
public interface ILoadBalance {
    /**
     * 选多个机器中选择一台机器
     *
     * @param servers
     * @param serviceName
     * @return
     */
    Server choose(List<Server> servers,String serviceName);

}
