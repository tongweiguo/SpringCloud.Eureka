package org.SpringCloud.Eureka.Consumer.Ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker//表示开启断路器(熔断器)
@SpringCloudApplication
public class ConsumerApplication {

	@Bean
	@LoadBalanced//开启负载均衡,不开启无法实现调用
	public RestTemplate reTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConsumerApplication.class, args);
		System.out.println("Ribbon方式调用服务启动完成!");
	}

}
