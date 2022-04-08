package org.SpringCloud.Eureka.Ribbon.Hystrix.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.twg.springcloud.eureka.api.entity.User;
import org.SpringCloud.Eureka.Ribbon.Hystrix.service.HelloService;
import org.SpringCloud.Eureka.Ribbon.Hystrix.command.UserCommand;
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
	public HelloService helloService;
	
	@Autowired
	public RestTemplate template;
	
	/**
	 * 自身的服务实例
	 */
	@Autowired
	public DiscoveryClient discoveryClient;
	
	/**
	 * <p>同步调用服务提供者
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/ribbon-hystrix/{name}", method = RequestMethod.GET)
	public String hello(@PathVariable String name) {
		logger.info("ribbon同步接口收到参数:" + name);
		List<ServiceInstance> serviceList = discoveryClient.getInstances("SpringCloud-Producer");
		for (ServiceInstance service : serviceList) {
			logger.info("SpringCloud-Producer的服务实例:" + service.getUri());
		}
		return helloService.hello(name);
	}
	
	/**
	 * <p>异步调用服务提供者
	 * @param name
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value = "/hystrix-sync/{name}", method = RequestMethod.GET)
	public String helloSync(@PathVariable String name) throws InterruptedException, ExecutionException {
		logger.info("ribbon异步接口收到参数:" + name);
		List<ServiceInstance> serviceList = discoveryClient.getInstances("SpringCloud-Producer");
		for (ServiceInstance service : serviceList) {
			logger.info("SpringCloud-Producer的服务实例:" + service.getUri());
		}
		//执行异步调用
		String result = helloService.helloSync(name);
		
		return result;
	}
	
	/**
	 * 同步调用服务SpringCloud-Producer的getUser接口
	 * <p>非注解方式
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getUser/{id}")
	public User getUser(@PathVariable String id) {
		logger.info("非注解方式同步接口收到参数:" + id);
		//获取开始调用时间
		long start = System.currentTimeMillis();
		//执行同步调用
		User user = new UserCommand( template, id).execute();
		//获取调用结束时间
		long end = System.currentTimeMillis();
		logger.info("同步调用服务花费时间:" + (end - start));
		return user;
	}
	
	/**
	 * 异步调用服务SpringCloud-Producer的getUser接口
	 * <p>非注解方式
	 * @param id
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value = "/getUser-sync/{id}")
	public User getUserSync(@PathVariable String id) throws InterruptedException, ExecutionException {
		logger.info("非注解方式异步接口收到参数:" + id);
		//获取开始调用时间
		long start = System.currentTimeMillis();
		//执行异步调用
		Future<User> future = new UserCommand(template, id).queue();
		//获取调用结束时间
		long end = System.currentTimeMillis();
		logger.info("异步调用服务花费时间:" + (end - start));
		
		return future.get();
	}
	
}
