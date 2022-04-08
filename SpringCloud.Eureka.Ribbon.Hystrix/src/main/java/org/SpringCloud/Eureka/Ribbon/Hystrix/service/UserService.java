package org.SpringCloud.Eureka.Ribbon.Hystrix.service;



import com.twg.springcloud.eureka.api.entity.User;

import java.util.List;

/**
 * 接口調用服務層
 * @author weiguo
 */
public interface UserService {

    User getUser(String id);

    List<User> getUsers(List<String> ids);
}
