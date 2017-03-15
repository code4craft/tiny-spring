package com.ysj.tinySpring.beans;

/**
 * Bean在 IoC 容器中的定义， IoC 容器可以根据这个定义来生成实例 的问题
 * 
 * 以 BeanDefinition 类为核心发散出的几个类，都是用于解决 Bean 的具体定义问题，包括 Bean 的名字是什么、
 * 它的类型是什么，它的属性赋予了哪些值或者引用
 *
 */
public class BeanDefinition {

	private Object bean;

	/**
	 * bean的类型
	 * 根据其 类型 可以生成一个类实例，然后可以把 属性 注入进去。
	 */
	private Class beanClass;
	
	/**
	 * bean的名字
	 */
	private String beanClassName;

	/**
	 * bean的属性集合
	 * 每个属性都是键值对 String - Object
	 */
	private PropertyValues propertyValues = new PropertyValues();
	
	public BeanDefinition() { }

	
	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
	
}
