package org.SpringCloud.Eureka.Ribbon.Hystrix.command;

import com.twg.springcloud.eureka.api.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;


/**
 * 单个请求命令
 * 非注解调用层
 * @author weiguo
 *
 */
public class UserCommand extends HystrixCommand<User>{
	
	Logger logger = LoggerFactory.getLogger(UserCommand.class);
	
	private RestTemplate restTemplate;

	private String id;

	public UserCommand(RestTemplate restTemplate,String id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommandGroup")));
		this.restTemplate = restTemplate;
		this.id = id;
	}

	/**
	 * 调用execute或者queue就会执行run
	 */
	@Override
	protected User run() throws Exception {
		
		logger.info("非注解方式调用SpringCloud-Producer开始");
		
		User user = restTemplate.getForEntity("http://SpringCloud-Producer/getUser?id={1}", User.class, id).getBody();
		
		return user;
	}
	
	/**
	 * run方法调用接口失败时断路器
	 */
	@Override
	protected User getFallback() {
		
		return new User(id, "服务不存在", "服务调用失败");
	}
	

}
