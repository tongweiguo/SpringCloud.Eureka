package com.twg.SpringCloud.Eureak.Consumer.Hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients//启用feign进行远程调用
public class ConsumerApplication {

	public static void main(String[] args){
		SpringApplication.run(ConsumerApplication.class, args);
		System.out.println("feign方式调用(包括熔断器)启动完成");
	}

}
