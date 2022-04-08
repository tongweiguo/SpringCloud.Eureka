package org.SpringCloud.Eureka.Ribbon.Hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.twg.springcloud.eureka.api.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCollapser(//请求合并注解
            batchMethod = "getUsers",//请求合并批量处理方法
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,//所有请求接口进来都合并
            collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "1000")//将2000毫秒内请求合并
    })
    @Override
    public User getUser(String id) {
        //return restTemplate.getForEntity("http://SpringCloud-Producer/getUser?id={1}",User.class,id).getBody();
        return null;
    }

    @HystrixCommand(fallbackMethod = "getUsersFallback")
    @Override
    public List<User> getUsers(List<String> ids) {
        System.out.println("批量获取User:" + Thread.currentThread().getName() + ",ids:" + ids);
        return restTemplate.getForEntity("http://SpringCloud-Producer/getUsers?ids={1}",
                List.class,
                StringUtils.join(ids,",")).getBody();
    }

    public List<User> getUsersFallback(List<String> ids){
        List<User> users = new ArrayList<>();
        for (String id : ids) {
            users.add(new User(id, "服务不存在", "服务调用失败"));
        }
        return users;
    }

}
