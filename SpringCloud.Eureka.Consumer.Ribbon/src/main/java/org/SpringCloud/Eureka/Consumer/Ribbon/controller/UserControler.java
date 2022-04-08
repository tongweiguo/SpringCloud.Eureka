package org.SpringCloud.Eureka.Consumer.Ribbon.controller;

import com.twg.springcloud.eureka.api.entity.User;
import org.SpringCloud.Eureka.Consumer.Ribbon.command.UserBatchCommand;
import org.SpringCloud.Eureka.Consumer.Ribbon.command.UserCollapseCommand;
import org.SpringCloud.Eureka.Consumer.Ribbon.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserControler {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/ribbon/getUser")
    public User getUser(@RequestParam String id) throws ExecutionException, InterruptedException {
        UserCollapseCommand userCollapseCommand = new UserCollapseCommand(userService,id);
        return userCollapseCommand.queue().get();
        //return userService.getUser(id);
    }

    @RequestMapping(value = "/ribbon/getUsers")
    public List<User> getUsers(@RequestParam String ids){
        UserBatchCommand userBatchCommand = new UserBatchCommand(userService,Arrays.asList(ids.split(",")));
        return userBatchCommand.execute();
    }

}
