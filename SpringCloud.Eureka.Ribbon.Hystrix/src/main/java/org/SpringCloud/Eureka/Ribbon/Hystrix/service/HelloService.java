package org.SpringCloud.Eureka.Ribbon.Hystrix.service;

import com.twg.springcloud.eureka.api.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import rx.Observable;
import rx.Subscriber;
import rx.Observable.OnSubscribe;

import java.util.concurrent.ExecutionException;

/**
 * 服务层
 * @author weiguo
 *
 */
@Service
public class HelloService {
	
	Logger logger = LoggerFactory.getLogger(HelloService.class);

	@Autowired
	public RestTemplate template;
	
	
	/**
	 * <p>同步执行调用服务提供者的接口
	 * <p>@HystrixCommand容错注解
	 * <br>指定调用服务失败时回调方法
	 * @param name
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "helloFallback")
	public String hello(String name) {
		logger.info("ribbon方式同步执行调用SpringCloud-Producer");
		//获取开始调用时间
		long start = System.currentTimeMillis();
		
		//调用SPRINGCLOUD-PRODUCER服务下hello接口,可以实现负载均衡
		//第三个参数会替换{1}里面的占位符,第四个替换{2}里面的.此次类推.SpringCloud-Producer
		String result = template.getForEntity("http://SpringCloud-Producer/hello?name={1}", String.class, name).getBody();
		//如果接口返回的是封装好的对象,可以使用下面方式   T是封装的对象
		//T = template.getForObject("http://SpringCloud-Producer/hello?name={1}", T.class, name);
		
		//获取调用结束时间
		long end = System.currentTimeMillis();
		logger.info("同步调用服务花费时间:" + (end - start));
		return result;
	}
	
	/**
	 * <p>异步执行调用服务提供者的接口
	 * @param name
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "helloFallback")
	public String helloSync(String name){
		logger.info("ribbon方式异步执行调用SpringCloud-Producer");
		//获取开始调用时间
		long start = System.currentTimeMillis();
		
		AsyncResult<String> result = new AsyncResult<String>() {
			@Override
			public String invoke() {
				// TODO 异步调用
				return template.getForEntity("http://SpringCloud-Producer/hello?name={1}", String.class, name).getBody();
			}
			
		};
		//获取调用结束时间
		long end = System.currentTimeMillis();
		logger.info("异步调用服务花费时间:" + (end - start));
		return result.get();
		
	}
	
	/**
	 * 使用观察者模式调用
	 * <p>observableExecutionMode = ObservableExecutionMode.EAGER表示observer()执行方式
	 * <p>observableExecutionMode = ObservableExecutionMode.LAZY表示toObservable()执行方式
	 * <p>fallbackMethod = "getUserFallback"指定断路时调用的方法
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getUserFallback", observableExecutionMode = ObservableExecutionMode.LAZY)
	public User getUser(String id) throws ExecutionException, InterruptedException {
		Observable<User> observable = Observable.create(new OnSubscribe<User>()
		{
			@Override
			public void call(Subscriber<? super User> observer) 
			{
				try 
				{
					if(!observer.isUnsubscribed()) 
					{
						User user = template.getForObject("http://SpringCloud-Producer/getUser?id={1}", User.class, id);
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
		
		return observable.toBlocking().toFuture().get();
	}
	
	public String helloFallback(String name) {
		logger.info("ribbon方式调用SpringCloud-Producer服务失败");
		return name + "hello! ribbon方式调用SpringCloud-Producer服务失败";
	}
	
	public User getUserFallback(String id)
	{
		return new User(id, "服务不存在", "服务调用失败");
	}
	
}
