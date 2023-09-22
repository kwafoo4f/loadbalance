package com.kuafoo4j.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-22 9:35
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String loadBalance(@RequestParam String serviceName) {
        String url = "http://%s/service/server-info";
        // 发起调用
        String result = restTemplate.getForObject(String.format(url, serviceName), String.class);
        return result;
    }

}
