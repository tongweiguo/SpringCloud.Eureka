package org.SpringCloud.Eureka.Ribbon.Hystrix.command;

import com.twg.springcloud.eureka.api.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;


/**
 * 使用观察者模式调用
 * @author weiguo
 *
 */
public class UserObservableCommand extends HystrixObservableCommand<User>{

	Logger logger = LoggerFactory.getLogger(UserObservableCommand.class);
	
	private RestTemplate restTemplate;

	private String id;
	
	public UserObservableCommand(RestTemplate restTemplate,String id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommandGroup")));
		this.restTemplate = restTemplate;
		this.id = id;
	}

	/**
	 * 观察者模式调用
	 */
	@Override
	protected Observable<User> construct() {
		logger.info("非注解观察者方式调用SpringCloud-Producer开始");

		Observable<User> user = Observable.create(new OnSubscribe<User>() 
		{
			@Override
			public void call(Subscriber<? super User> observer) 
			{
				try 
				{
					if(!observer.isUnsubscribed()) 
					{
						User user = restTemplate.getForObject("http://SpringCloud-Producer/getUser?id={1}", User.class, id);
						observer.onNext(user);
						observer.onCompleted();
					}
				}
				catch(Exception e) 
				{
					observer.onError(e);
				}
			}

		});
		return user;
	}

	
}
