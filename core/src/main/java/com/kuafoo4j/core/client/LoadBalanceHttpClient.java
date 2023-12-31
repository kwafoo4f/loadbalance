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
public class LoadBalanceHttpClient  {

    public ClientHttpResponse execute(HttpRequest request, byte[] bytes,
                                        ClientHttpRequestExecution execution,Server server) throws IOException {
        LoadBalanceHttpRequestWrapper requestWrapper = new LoadBalanceHttpRequestWrapper(request, server);
        return execution.execute(requestWrapper,bytes);
    }
}
