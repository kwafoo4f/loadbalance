package com.kuafoo4j.loadbalance;


import java.util.List;

/**
 * 服务查询 TODO 使用phenix-common
 */
public interface IPhoenixServerDiscovery {
    /**
     * 获取服务的所有实例
     * @param serviceName
     * @return
     */
    List<Instance> getInstanceList(String serviceName);
}
