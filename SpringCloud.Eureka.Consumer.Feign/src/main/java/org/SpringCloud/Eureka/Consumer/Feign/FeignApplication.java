package org.SpringCloud.Eureka.Consumer.Feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//启动服务注册中心客户端
@EnableFeignClients//启动feign远程调用
public class FeignApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FeignApplication.class, args);
		System.out.println("feign方式调用启动完成");
	}

}
