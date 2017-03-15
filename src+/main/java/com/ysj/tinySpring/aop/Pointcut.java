package com.ysj.tinySpring.aop;


/**
 * 切点, 确定是对什么类的什么方法进行AOP（就是确定在哪切）
 *
 */
public interface Pointcut {

	/**
	 * 获取 ClassFilter 对象
	 * 类名匹配（用于 筛选要代理的目标对象）
	 * @return
	 */
	ClassFilter getClassFilter();

	
	/**
	 * 获取一个 MethodMatcher 对象
	 *  方法名匹配
	 * @return
	 */
    MethodMatcher getMethodMatcher();
	
}
