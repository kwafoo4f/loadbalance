package com.kuafoo4j.core.pojo;

import lombok.Data;

/**
 * 服务实例
 */
@Data
public class Server {
    /**
     * 服务名称
     */
    private String name;
    /**
     * 实例ip
     */
    private String ip;
    /**
     * 实例端口
     */
    private int port;
    /**
     * 实例健康状态
     */
    private boolean healthy = true;
}
