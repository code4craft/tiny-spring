package com.ysj.tinySpring.beans.factory;

/**
 * 标识一个 IoC 容器。
 * 
 * 以 BeanFactory 接口为核心发散出的几个类，都是用于解决 IoC 容器在 已经获取 Bean 的 定义 的情况下，
 * 如何装配、获取 Bean 实例 的问题。
 *
 */
public interface BeanFactory {
	
	/**
	 * 通过 getBean(String) 方法来 获取bean实例
	 * @param name
	 * @return
	 * @throws Exception
	 */
	Object getBean(String name) throws Exception;
	
}
