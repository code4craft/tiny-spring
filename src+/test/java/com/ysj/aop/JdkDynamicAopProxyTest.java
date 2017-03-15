package com.ysj.aop;

import org.junit.Test;

import com.ysj.HelloWorldService;
import com.ysj.HelloWorldServiceImpl;
import com.ysj.tinySpring.aop.AdvisedSupport;
import com.ysj.tinySpring.aop.JdkDynamicAopProxy;
import com.ysj.tinySpring.aop.TargetSource;
import com.ysj.tinySpring.context.ApplicationContext;
import com.ysj.tinySpring.context.ClassPathXmlApplicationContext;

/**
 * @author yihua.huang@dianping.com
 */
public class JdkDynamicAopProxyTest {

	@Test
	public void testInterceptor() throws Exception {
		// --------- helloWorldService without AOP
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();

		// --------- helloWorldService with AOP
		// 1. 设置被代理对象(Joinpoint)
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class,
				HelloWorldService.class);
		advisedSupport.setTargetSource(targetSource);

		// 2. 设置拦截器(Advice)
		TimerInterceptor timerInterceptor = new TimerInterceptor();
		advisedSupport.setMethodInterceptor(timerInterceptor);

		// 补：由于用户未设置MethodMatcher，所以通过代理还是调用的原方法(JdkDynamicAopProxy中的invoke方法最后
		// 返回method.invoke(...)而不是methodInterceptor.invoke(...) )
		// 3. 创建代理(Proxy)
		JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
		HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();

		// 4. 基于AOP的调用
		helloWorldServiceProxy.helloWorld();

	}
}
