package com.ysj;

import com.ysj.tinySpring.beans.BeanPostProcessor;

/**
 * 实例化bean后，初始化时会调用该方法
 * @author yihua.huang@dianping.com
 */
public class BeanInitializeLogger implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		System.out.println("Initialize bean " + beanName + " start!");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		System.out.println("Initialize bean " + beanName + " end!");
		return bean;
	}
}
