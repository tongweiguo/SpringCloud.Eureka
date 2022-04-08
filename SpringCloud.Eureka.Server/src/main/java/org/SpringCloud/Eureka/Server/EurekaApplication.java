package org.SpringCloud.Eureka.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//服务注册中心注解
public class EurekaApplication {

	public static String port;
	
	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(EurekaApplication.class, args);
		System.out.println("服务注册中心启动完成!" + port);
	}
	
}
