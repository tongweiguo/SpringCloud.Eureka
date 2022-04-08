package org.SpringCloud.Eureka.Consumer.Ribbon.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	@Autowired
	RestTemplate template;
	
	/**
	 * 自身的服务实例
	 */
	@Autowired
	public DiscoveryClient discoveryClient;
	
	@RequestMapping(value = "/ribbon-hello/{name}", method = RequestMethod.GET )
	public String hello(@PathVariable String name) {
		logger.info("ribbon接口收到参数:" + name);
		List<ServiceInstance> serviceList = discoveryClient.getInstances("SpringCloud-Producer");
		for (ServiceInstance service : serviceList) {
			logger.info("SpringCloud-Producer的服务实例:" + service.getUri());
		}
		
		logger.info("ribbon方式调用SpringCloud-Producer");
		//调用SPRINGCLOUD-PRODUCER服务下hello接口,可以实现负载均衡
		//第三个参数会替换{1}里面的占位符,第四个替换{2}里面的.此次类推.SpringCloud-Producer
		return template.getForEntity("http://SpringCloud-Producer/hello?name={1}", String.class, name).getBody();
		//如果接口返回的是封装好的对象,可以使用下面方式   T是封装的对象
		//T = template.getForObject("http://SpringCloud-Producer/hello?name={1}", T.class, name);
	}
}
