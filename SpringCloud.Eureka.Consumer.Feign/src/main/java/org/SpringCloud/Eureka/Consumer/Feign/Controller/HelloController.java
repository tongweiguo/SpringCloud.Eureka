package org.SpringCloud.Eureka.Consumer.Feign.Controller;

import java.util.List;

import org.SpringCloud.Eureka.Consumer.Feign.Feign.HelloFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 服务消费方对外提供的接口
 * @author weiguo
 *
 */
@RestController
public class HelloController {
	
	Logger logger = LoggerFactory.getLogger(HelloController.class);

	/**
	 * 服务提供者的代理
	 */
	@Autowired
	public HelloFeign hello;
	
	/**
	 * 自身的服务实例
	 */
	@Autowired
	public DiscoveryClient discoveryClient;
	
	/**
	 * 对外提供调用SpringCloud-Producer服务的接口
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/feign-hello/{name}", method = RequestMethod.GET)
	public String hello(@PathVariable String name) {
		//获取SpringCloud-Producer服务的所有实例
		List<ServiceInstance> serviceList = discoveryClient.getInstances("SpringCloud-Producer");
		for (ServiceInstance service : serviceList) {
			logger.info("SpringCloud-Producer的服务实例:" + service.getUri());
		}
		logger.info("feign方式调用SpringCloud-Producer");
		//使用feign方式调用服务提供者的接口
		//实现了负载均衡.会轮询调用SpringCloud-Producer服务下所有实例的此接口
		return hello.hello(name);
	}
}
