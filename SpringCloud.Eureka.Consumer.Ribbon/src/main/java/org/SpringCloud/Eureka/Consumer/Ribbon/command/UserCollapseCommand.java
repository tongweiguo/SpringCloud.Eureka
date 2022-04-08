package org.SpringCloud.Eureka.Consumer.Ribbon.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCommand;
import com.twg.springcloud.eureka.api.entity.User;
import org.SpringCloud.Eureka.Consumer.Ribbon.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求合并器
 * @author weiguo
 *
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, String> {

    private UserService userService;

    private String id;

    public UserCollapseCommand(UserService userService, String id) {
        //为请求合并设置时间延迟,合并器会在该时间延迟内将收到的单个User的请求合并成一个批量请求
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand")));
               // .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(2000))
              //  .andScope(Scope.GLOBAL));
        this.userService = userService;
        this.id = id;
    }

    /**
     * 返回单个请求的请求参数
     * @return
     */
    @Override
    public String getRequestArgument() {
        return this.id;
    }

    /**
     * 该方法的collapsedRequests参数中保存了延迟时间窗中收集到的所有获取单个user的请求.
     * 通过获取这些请求的参数来组织上面准备的批量请求UserBatchCommand实例
     * @param collapsedRequests
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collapsedRequests) {
        List<String> idList = new ArrayList<>(collapsedRequests.size());
        idList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService,idList);
    }

    /**
     * 在批量请求命令UserBatchCommand实例被触发执行完成之后,该方法开始执行,其中batchResponse参数保存了批量请求的返回结果.
     * 而collapsedRequests参数则代表了每个被合并的请求.
     * @param batchResponse
     * @param collapsedRequests
     */
    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, String>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User,String> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            //给每个请求设置返回结果
            collapsedRequest.setResponse(user);
        }
    }
}
