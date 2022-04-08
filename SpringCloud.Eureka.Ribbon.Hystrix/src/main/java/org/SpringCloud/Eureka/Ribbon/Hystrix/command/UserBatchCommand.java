package org.SpringCloud.Eureka.Ribbon.Hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.twg.springcloud.eureka.api.entity.User;
import org.SpringCloud.Eureka.Ribbon.Hystrix.service.UserService;


import java.util.ArrayList;
import java.util.List;

/**
 * 批量请求命令
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {

    UserService userService;

    List<String> ids;

    public UserBatchCommand(UserService userService,List<String> ids){
        super(Setter.withGroupKey(() -> "UserCommandGroup"));
        //super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommandGroup")));
        this.ids = ids;
        this.userService = userService;
    }

    /**
     * Implement this method with code to be executed when {@link #execute()} or {@link #queue()} are invoked.
     *
     * @return R response type
     * @throws Exception if command execution fails
     */
    @Override
    public List<User> run() throws Exception {
        return userService.getUsers(ids);
    }

    /**
     * run方法调用接口失败时断路器
     * @return
     */
    @Override
    protected List<User> getFallback() {
        List<User> users = new ArrayList<>();
        for (String id : ids) {
            users.add(new User(id, "服务不存在", "服务调用失败"));
        }
        return users;
    }
}
