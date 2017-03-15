package com.ysj.tinySpring.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 封装被代理对象的方法
 *
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

	// 原对象
	protected Object target;

    protected Method method;

    protected Object[] arguments;
	
    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}
    
	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	@Override
	public Object getThis() {
		return target;
	}

	/**
	 * 调用被拦截对象的方法
	 */
	@Override
	public Object proceed() throws Throwable {
		// tiny-spring这里是调用原始对象的方法
		// 不支持拦截器链
		/*
			为了支持拦截器链，可以做出以下修改：
				将 proceed() 方法修改为调用代理对象的方法 method.invoke(proxy,args)。
				在代理对象的 InvocationHandler 的 invoke 函数中，查看拦截器列表，如果有拦截器，则调用第一个拦截器并
				  返回，否则调用原始对象的方法。
		*/
		return method.invoke(target, arguments);
	}

	@Override
	public Method getMethod() {
		return method;
	}

}
