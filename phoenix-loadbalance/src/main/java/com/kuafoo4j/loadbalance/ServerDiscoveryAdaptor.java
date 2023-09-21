package com.kuafoo4j.loadbalance;

import com.kuafoo4j.core.client.IServerDiscovery;
import com.kuafoo4j.core.pojo.Server;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配Phoenix注册中心
 */
public class ServerDiscoveryAdaptor implements IServerDiscovery {

    private IPhoenixServerDiscovery serverDiscovery;

    public ServerDiscoveryAdaptor(IPhoenixServerDiscovery serverDiscovery) {
        this.serverDiscovery = serverDiscovery;
    }

    /**
     * 获取服务的所有实例
     *
     * @param serviceName
     * @return
     */
    @Override
    public List<Server> getServerList(String serviceName) {
        List<Server> serverList = new ArrayList<>();
        // 从Phoenix中获取服务实例
        List<Instance> instanceList = serverDiscovery.getInstanceList(serviceName);
        if (instanceList == null) {
            return serverList;
        }
        // 将服务实例转换为负载均衡的机器
        for (Instance instance : instanceList) {
            Server server = new Server();
            BeanUtils.copyProperties(instance,server);
            serverList.add(server);
        }

        return serverList;
    }
}
