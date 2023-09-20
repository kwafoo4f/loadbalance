package com.kuafoo4j.loadbalance;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.kuafoo4j.core.client.IServerDiscovery;
import com.kuafoo4j.core.pojo.Server;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ServerDiscoveryAdaptor implements IServerDiscovery {

    private IPhoenixServerDiscovery serverDiscovery;

    @Override
    public List<Server> getServerList(String serviceName) {
        List<Server> serverList = new ArrayList<>();
        List<Instance> instanceList = serverDiscovery.getInstanceList(serviceName);
        if (instanceList == null) {
            return serverList;
        }

        for (Instance instance : instanceList) {
            Server server = new Server();
            BeanUtils.copyProperties(instance,server);
            serverList.add(server);
        }

        return serverList;
    }
}
