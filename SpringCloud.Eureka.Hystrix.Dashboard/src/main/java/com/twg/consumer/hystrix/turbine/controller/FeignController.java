package com.twg.consumer.hystrix.turbine.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twg.consumer.hystrix.turbine.feign.HelloRemote;

@RestController
public class FeignController {

	@Autowired
	private HelloRemote hello;
	
	@RequestMapping(value = "/feign/{name}")
	public String hello(@PathVariable String name) {
		System.out.println(name);
		return hello.hello();
	}
}
