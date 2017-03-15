package com.ysj.tinySpring.context;

import java.util.List;

import com.ysj.tinySpring.beans.BeanPostProcessor;
import com.ysj.tinySpring.beans.factory.AbstractBeanFactory;

/**
 * ApplicationContext 的抽象实现
 *
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	protected AbstractBeanFactory beanFactory;
	
	public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	/**
	 * 用于实现 BeanFactory 的刷新，也就是告诉 BeanFactory 该使用哪个资源（Resource）加载bean的定义
	 * （BeanDefinition）,并实例化，初始化bean
	 * @throws Exception
	 */
	public void refresh() throws Exception {
		// 加载出bean的定义并保存到beanFactory中
		loadBeanDefinitions(beanFactory);
		// 从 BeanFactory 中的bean的定义找实现 BeanPostProcessor接口的类(例如：AspectJAwareAdvisorAutoProxyCreator.java)
		// 注册到 AbstractBeanFactory 维护的 BeanPostProcessor 列表中去(用于在bean实例化后对其进行一些其他处理，
		// 可以看看getBean方法()中的initializeBean()的调用)。 
		// 后面调用getBean方法通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()方法中调用了
		// getBeansForType方法保证了 PointcutAdvisor 的实例化顺序优于普通 Bean。
		registerBeanPostProcessors(beanFactory);
		// 默认以单例的方式实例化所有 Bean
		onRefresh();
	}
	
	/**
	 * 由子类决定从哪种形式的Resource中加载出bean的定义，并保存到beanFactory中
	 * @param beanFactory
	 * @throws Exception
	 */
	protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;
	
	/**
	 * 可用于实例化AspectJAwareAdvisorAutoProxyCreator
	 * @param beanFactory
	 * @throws Exception
	 */
	protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
		List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
		for (Object beanPostProcessor : beanPostProcessors) {
			beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
		}
	}
	
	// 实现支持singleton(单例)类型的bean
	protected void onRefresh() throws Exception{
        beanFactory.preInstantiateSingletons();
    }
	
	// 从beanFactory中获取bean
	@Override
	public Object getBean(String name) throws Exception {
		return beanFactory.getBean(name);
	}

}
