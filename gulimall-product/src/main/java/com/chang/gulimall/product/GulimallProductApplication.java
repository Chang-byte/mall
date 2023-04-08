package com.chang.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @title: GulimallProductionApplication
 * @Author Chang
 * @Date: 2023/3/5 22:26
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.chang.gulimall.product.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.chang.gulimall.product.feign")
public class GulimallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }
}
