package com.kuafoo4j.core.client;

import com.kuafoo4j.core.pojo.Server;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 负载均衡http请求装饰器
 */
public class LoadBalanceHttpRequestWrapper extends HttpRequestWrapper {
    private Server server;

    public LoadBalanceHttpRequestWrapper(HttpRequest request,Server server) {
        super(request);
        this.server = server;
    }


    @Override
    public URI getURI() {
        return replaceHost();
    }

    /**
     * 替换服务名称为具体的ip
     * @return
     */
    private URI replaceHost() {
        HttpRequest request = getRequest();
        URI original = request.getURI();
        String scheme = original.getScheme();
        String host = server.getIp()+":"+server.getPort();
        StringBuilder sb = new StringBuilder();
        // http://ip:port?sdad
        sb.append(scheme).append("://").append(host).append(original.getRawPath());
        URI uri = null;
        try {
            uri = new URI(sb.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri;
    }

}
