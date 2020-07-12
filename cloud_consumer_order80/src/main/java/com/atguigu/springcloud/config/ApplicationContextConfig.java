package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced  //赋予服务器负载均衡的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
