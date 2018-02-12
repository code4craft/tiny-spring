package com.ysj.tinySpring.beans;

/**
 * 用于在bean定义初始化时嵌入相关操作
 * 	  例如：在 postProcessorAfterInitialization 方法中，使用动态代理的方式，返回一个对象的代理对象。解决了
 *        在 IoC 容器的何处植入 AOP 的问题。
 *
 */
public interface BeanPostProcessor {

	Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

	Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
	
}
