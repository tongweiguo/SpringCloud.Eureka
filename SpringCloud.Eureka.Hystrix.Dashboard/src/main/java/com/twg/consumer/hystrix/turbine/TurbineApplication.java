package com.twg.consumer.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient//启动注册中心
@EnableFeignClients//启用Feign远程调用
@EnableHystrixDashboard//熔断监控
@EnableCircuitBreaker//开启断路器(熔断器)
public class TurbineApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(TurbineApplication.class, args);
    }
}
