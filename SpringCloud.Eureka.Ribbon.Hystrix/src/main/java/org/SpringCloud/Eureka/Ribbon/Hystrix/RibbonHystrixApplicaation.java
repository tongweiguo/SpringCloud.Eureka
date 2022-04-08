package org.SpringCloud.Eureka.Ribbon.Hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * <p>@SpringCloudApplication注解表示是一个正常的SpringCloud标准应用包,包含了下面三个注解
 * <br>@SpringBootApplication表示是一个SpringBoot应用包
 * <br>@EnableDiscoveryClient表示开启服务注册中心客户端
 * <br>@EnableCircuitBreaker表示开启断路器(熔断器)
 * @author weiguo
 *
 */
@SpringCloudApplication
public class RibbonHystrixApplicaation {

	
	@Bean
	@LoadBalanced//Ribbon调用注解
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RibbonHystrixApplicaation.class, args);
		System.out.println("Ribbon方式调用(开启断路器)启动完成");
	}

}
