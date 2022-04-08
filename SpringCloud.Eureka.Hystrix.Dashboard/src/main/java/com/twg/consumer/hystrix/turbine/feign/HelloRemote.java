package com.twg.consumer.hystrix.turbine.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>远程调用接口
 * <p>@FeignClient远程调用注解
 * <p>name 必须对应服务提供者的服务名称;   
 * <p>fallback 熔断装置.当服务提供者不可调用时调用该实现类
 * @author weiguo
 */
@FeignClient(name = "Tong-client" ,fallback = HelloRemoteHystrix.class)
public interface HelloRemote {
	
	/**
	 * <p>远程接口名称
	 * value必须对应服务提供者的接口名称,,参数也要对应
	 * @param name 参数名称
	 * @return
	 */
	@RequestMapping(value = "/doClient")
	public String hello();
}
