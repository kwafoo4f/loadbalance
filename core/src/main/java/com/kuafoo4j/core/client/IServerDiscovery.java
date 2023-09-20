package com.kuafoo4j.core.client;

import com.kuafoo4j.core.pojo.Server;

import java.util.List;

/**
 * 获取服务的实例
 */
public interface IServerDiscovery {
    /**
     * 获取服务的所有实例
     *
     * @param serviceName
     * @return
     */
    List<Server> getServerList(String serviceName);
}
