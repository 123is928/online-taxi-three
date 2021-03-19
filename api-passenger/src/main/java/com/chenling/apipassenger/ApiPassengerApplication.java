package com.chenling.apipassenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApiPassengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPassengerApplication.class, args);
    }

    @LoadBalanced //可以解析注册到eureka的服务名如SERVICE-PASSENGER-USER
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
