package com.twg.SpringCloud.Eureak.Consumer.Hystrix.Feign;

import org.springframework.stereotype.Component;

/**
 * 调用SpringCloud-Producer服务的熔断装置
 * @author weiguo
 * 
 */
@Component
public class FeignHelloHystrix implements FeignHello {

	@Override
	public String hello(String name) {
		
		return name + " hello! 这是调用SpringCloud-Producer的熔断器";
	}

}
