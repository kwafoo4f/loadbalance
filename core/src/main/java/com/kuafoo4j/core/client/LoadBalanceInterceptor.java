package com.kuafoo4j.core.client;

import com.kuafoo4j.core.pojo.Server;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;

/**
 * 负载均衡拦截器
 */
public class LoadBalanceInterceptor implements ClientHttpRequestInterceptor {

    private LoadBalanceClient loadBalanceClient;

    private LoadBalanceHttpClient httpClient;

    public LoadBalanceInterceptor(LoadBalanceClient loadBalanceClient, LoadBalanceHttpClient httpClient) {
        this.loadBalanceClient = loadBalanceClient;
        this.httpClient = httpClient;
    }

    /**
     * 拦截http请求,负载均衡器选取一个主机，将服务名替换为具体主机并发起调用.
     *
     * @param request
     * @param bytes
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes,
                                        ClientHttpRequestExecution execution) throws IOException {
        URI uri = request.getURI();
        String host = uri.getHost();
        Assert.isTrue(host != null,"没有服务名称:"+uri);
        // 负载均衡器选取一个主机
        Server server = loadBalanceClient.execute(host);
        Assert.isTrue(server != null,host+",没有有效实例");

        // 将服务名替换为具体主机并发起调用
        return httpClient.execute(request,bytes,execution,server);
    }
}
