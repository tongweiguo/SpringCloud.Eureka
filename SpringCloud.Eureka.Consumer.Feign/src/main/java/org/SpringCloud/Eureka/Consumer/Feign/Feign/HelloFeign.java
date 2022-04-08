package org.SpringCloud.Eureka.Consumer.Feign.Feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>远程调用接口
 * <p>@FeignClient远程调用注解
 * <p>name 必须对应服务提供者的服务名称;   
 * @author weiguo
 */
@FeignClient(name = "SpringCloud-Producer")
public interface HelloFeign {

	/**
	 * <p>远程接口名称
	 * value必须对应服务提供者的接口名称,参数也要对应
	 * 传参方式必须对应服务提供者的传参方式,包括参数名称
	 * @param name 参数名称
	 * @return
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(@RequestParam(value = "name") String name);
}
