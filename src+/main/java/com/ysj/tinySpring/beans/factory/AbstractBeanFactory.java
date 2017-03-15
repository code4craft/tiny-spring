package com.ysj.tinySpring.beans.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ysj.tinySpring.beans.BeanDefinition;
import com.ysj.tinySpring.beans.BeanPostProcessor;

/**
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构。
 * 
 * IoC 容器的结构：AbstractBeanFactory 维护一个 beanDefinitionMap 哈希表用于保存类的定义信息（BeanDefinition）。
 *
 */
public class AbstractBeanFactory implements BeanFactory{

	/**
	 * bean定义的信息和bean的name保存在线程安全的HashMap中
	 */
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	/**
	 * 保存完成注册的bean的name
	 */
	private final List<String> beanDefinitionNames = new ArrayList<String>();
	
	/**
	 * 增加bean处理程序：
	 * 	例如通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()实现AOP的织入
	 */
	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
	
	/**
	 * 根据名字获取bean实例(实例化并初始化bean)
	 */
	@Override
	public Object getBean(String name) throws Exception {
		// 获取该bean的定义
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		// 如果没有这个bean的定义就抛异常
		if (beanDefinition == null) {
			throw new IllegalArgumentException("No bean named " + name + " is defined");
		}
		Object bean = beanDefinition.getBean();
		// 如果还没有装配bean
		if (bean == null) {
			// 装配bean（实例化并注入属性）
			bean = doCreateBean(beanDefinition);
			// 初始化bean
			// 例如：生成相关代理类,用于实现AOP织入
            bean = initializeBean(bean, name);
            beanDefinition.setBean(bean);
		}
		return bean;
	}

	// 装载bean
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		// 实例化bean
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		// 注入属性的hook方法(参考模板方法设计模式中的hook方法)交给子类去实现
		// 例如：AutowireCapableBeanFactory.java实现了自动装配
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}
	
	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}
	
	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

	}
	
	/** 
	 * 初始化bean
	 * 可在此进行AOP的相关操作：例如生成相关代理类，并返回
	 * @param bean
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected Object initializeBean(Object bean, String name) throws Exception {
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
		}

		// 可以看看AutowireCapableBeanFactory的postProcessAfterInitialization()方法实现
		// 返回的可能是代理对象
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
		}
        return bean;
	}
	
	/**
	 * 预处理bean的定义，将bean的名字提前存好,实现Ioc容器中存储单例bean
	 * @throws Exception
	 */
	public void preInstantiateSingletons() throws Exception {
		Iterator it = this.beanDefinitionNames.iterator();
		while (it.hasNext()) {
			String beanName = (String) it.next();
			getBean(beanName);
		}
	}
	
	/**
	 * 根据类型获取所有bean实例
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBeansForType(Class type) throws Exception {
		List beans = new ArrayList<Object>();
		for (String beanDefinitionName : beanDefinitionNames) {
			/**
			 * boolean isAssignableFrom(Class<?> cls)  
			 * 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
			 */
			if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}
	
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}
	
	// 增加bean处理程序，例如AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
		this.beanPostProcessors.add(beanPostProcessor);
	}
	
	
}
