package com.ysj.tinySpring.aop;

import org.aopalliance.aop.Advice;

/**
 * 通知器
 * 用于实现 具体的方法拦截，需要使用者编写，也就对应了 Spring 中的前置通知、后置通知、环切通知等。
 *
 */
public interface Advisor {

	/**
	 * 获取通知器(方法拦截器)
	 * @return
	 */
	Advice getAdvice();
	
}
