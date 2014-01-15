package us.codecraft.tinyioc.aop;

import us.codecraft.tinyioc.beans.BeanPostProcessor;

/**
 * @author yihua.huang@dianping.com
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		return bean;
	}
}
