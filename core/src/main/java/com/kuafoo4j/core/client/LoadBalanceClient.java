package com.kuafoo4j.core.client;

import com.kuafoo4j.core.pojo.Server;

import java.util.List;

public class LoadBalanceClient {

    private IServerDiscovery serverDiscovery;

    private ILoadBalance loadBalance;

    public LoadBalanceClient(IServerDiscovery serverDiscovery, ILoadBalance loadBalance) {
        this.serverDiscovery = serverDiscovery;
        this.loadBalance = loadBalance;
    }

    /**
     * 从服务中选择一台机器
     *
     * @param serviceName
     * @return
     */
    public Server execute(String serviceName) {
        List<Server> serverList = serverDiscovery.getServerList(serviceName);
        if (serverList == null || serverList.size() < 1) {
            return null;
        }
        return loadBalance.choose(serverList,serviceName);
    }


}
