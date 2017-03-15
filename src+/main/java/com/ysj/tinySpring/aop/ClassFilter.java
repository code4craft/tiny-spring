package com.ysj.tinySpring.aop;

/**
 * 类匹配器
 *
 */
public interface ClassFilter {

	/**
	 * 用于匹配targetClass是否是要拦截的类
	 * @param targetClass
	 * @return
	 */
	boolean matches(Class targetClass);
	
}
