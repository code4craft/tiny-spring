package com.ysj.tinySpring.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *  cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但
 *  因为采用的是继承， 所以不能对final修饰的类进行代理。 
 *
 */
public class Cglib2AopProxy extends AbstractAopProxy {

	public Cglib2AopProxy(AdvisedSupport advised) {
		super(advised);
	}

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advised.getTargetSource().getTargetClass());
		enhancer.setInterfaces(advised.getTargetSource().getInterfaces());
		/** Enhancer.java中setCallback方法源码
		 *	public void setCallback(final Callback callback) {
		 *       setCallbacks(new Callback[]{ callback });
		 *  }
	    */
	    // 设置回调方法
		enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
		Object enhanced = enhancer.create();
		return enhanced;
	}

	/** 注意该类实现的是net.sf.cglib.proxy.MethodInterceptor，不是aopalliance的MethodInterceptor.
	 *	net.sf.cglib.proxy.MethodInterceptor.java:

 	 *	public interface MethodInterceptor extends Callback{
			
			// All generated proxied methods call this method instead of the original method.
     		// The original method may either be invoked by normal reflection using the Method object,
     		// or by using the MethodProxy (faster).
 			public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                           MethodProxy proxy) throws Throwable;

 	 *	}
	 *	
	 */
	private static class DynamicAdvisedInterceptor implements MethodInterceptor {

		private AdvisedSupport advised;

		// 用户写的的方法拦截器
		private org.aopalliance.intercept.MethodInterceptor delegateMethodInterceptor;

		private DynamicAdvisedInterceptor(AdvisedSupport advised) {
			this.advised = advised;
			this.delegateMethodInterceptor = advised.getMethodInterceptor();
		}

		// 拦截代理对象的所有方法
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			// 如果advised.getMethodMatcher()为空(编程式的使用aop，例如：Cglib2AopProxyTest.java)，拦截该类的所有方法
			// 如果有方法匹配器(声明式的在xml文件里配置了AOP)并且匹配方法成功就拦截指定的方法
			if (advised.getMethodMatcher() == null
					|| advised.getMethodMatcher().matches(method, advised.getTargetSource().getTargetClass())) {
				// delegateMethodInterceptor通过advised.getMethodInterceptor()得到用户写的方法拦截器
				// 返回去调用用户写的拦截器的invoke方法(用户根据需要在调用proceed方法前后添加相应行为,例如：TimerInterceptor.java)
				return delegateMethodInterceptor.invoke(new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, args, proxy));
			}
			// 有AspectJ表达式，但没有匹配该方法，返回通过methodProxy调用原始对象的该方法
			return new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, args, proxy).proceed();
		}
	}

	private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
		// 方法代理
		private final MethodProxy methodProxy;

		public CglibMethodInvocation(Object target, Method method, Object[] args, MethodProxy methodProxy) {
			super(target, method, args);
			this.methodProxy = methodProxy;
		}

		// 通过methodProxy调用原始对象的方法
		@Override
		public Object proceed() throws Throwable {
			return this.methodProxy.invoke(this.target, this.arguments);
		}
	}
	
}
