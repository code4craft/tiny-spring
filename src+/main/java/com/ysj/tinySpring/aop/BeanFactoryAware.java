package com.ysj.tinySpring.aop;

import com.ysj.tinySpring.beans.factory.BeanFactory;

/**
 * 实现该接口则有操作beanFactory的能力
 * 
 * 容器的引用传入到 Bean 中去，这样，Bean 将获取容器的引用，获取对容器操作的权限，也就允许了 编写
 * 扩展 IoC 容器的功能的 Bean。
 * 	  例如：获取容器中所有的 切点对象，决定对哪些对象的哪些方法进行代理。解决了 为哪些对象提供 AOP 的植入 的问题。
 *
 */
public interface BeanFactoryAware {

	void setBeanFactory(BeanFactory beanFactory) throws Exception;
	
}
