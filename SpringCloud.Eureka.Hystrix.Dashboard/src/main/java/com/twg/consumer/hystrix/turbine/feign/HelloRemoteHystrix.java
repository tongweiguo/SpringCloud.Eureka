package com.twg.consumer.hystrix.turbine.feign;

import org.springframework.stereotype.Component;

@Component
public class HelloRemoteHystrix implements HelloRemote {

	@Override
	public String hello() {
		
		return "hello ! 这是消费者调用Tong-client的熔断装置";
	}

}
