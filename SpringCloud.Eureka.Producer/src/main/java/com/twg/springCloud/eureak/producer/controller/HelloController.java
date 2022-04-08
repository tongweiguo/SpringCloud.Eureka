package com.twg.springCloud.eureak.producer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.twg.springcloud.eureka.api.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
	
	Logger logger = LoggerFactory.getLogger("SpringCloud-Producer");

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(@RequestParam String name) throws InterruptedException
	{
		//获取自己服务实例
		ServiceInstance producer = discoveryClient.getLocalServiceInstance();
		//自己服务ID
		String locaServiceId = producer.getServiceId();
		//自己服务地址
		String localhost = producer.getHost() + ":" + producer.getPort();
		//获取服务中心列表
		logger.info("服务中心列表：" + discoveryClient.getServices());
		//随机获取0~3000
		int sleep = new Random().nextInt(4000);
		logger.info("SpringCloud-Producer/hello服务阻塞" + sleep);
		//模拟服务阻塞,断路器默认超时是1秒
		Thread.sleep(sleep);
	
		return "这是" + locaServiceId + "服务." + localhost + "/hello被调用!";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public User getUser(@RequestParam String id) throws InterruptedException
	{
		//获取自己服务实例
		ServiceInstance producer = discoveryClient.getLocalServiceInstance();
		//自己服务ID
		String locaServiceId = producer.getServiceId();
		//自己服务地址
		String localhost = producer.getHost() + ":" + producer.getPort();
		//获取服务中心列表
		logger.info("服务中心列表：" + discoveryClient.getServices());
		//随机获取0~3000
		//int sleep = new Random().nextInt(4000);
		logger.info("SpringCloud-Producer/getUser服务被调用");
		//模拟服务阻塞,断路器默认超时是1秒
		//Thread.sleep(sleep);
		User user = new User(id, localhost, locaServiceId);
		return user;
	}

	@RequestMapping(value = "/getUsers")
	public List<User> getUserList(@RequestParam String ids){
	    List<User> userList = new ArrayList<>();
        //获取自己服务实例
        ServiceInstance producer = discoveryClient.getLocalServiceInstance();
        //自己服务ID
        String locaServiceId = producer.getServiceId();
        //自己服务地址
        String localhost = producer.getHost() + ":" + producer.getPort();
        //获取服务中心列表
        logger.info("服务中心列表：" + discoveryClient.getServices());
        //随机获取0~3000
        //int sleep = new Random().nextInt(4000);
        logger.info("SpringCloud-Producer/getUserList服务被调用");
        //模拟服务阻塞,断路器默认超时是1秒
        //Thread.sleep(sleep);

        for (String id : ids.split(",")) {
            userList.add(new User(id, localhost, locaServiceId));
        }
        return userList;
    }
}
