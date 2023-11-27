package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.BeanReference;
import us.codecraft.tinyioc.aop.BeanFactoryAware;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可自动装配内容的BeanFactory
 * 
 * @author yihua.huang@dianping.com
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}

			try {
				Method declaredMethod = bean.getClass().getDeclaredMethod(
						"set" + propertyValue.getName().substring(0, 1).toUpperCase()
								+ propertyValue.getName().substring(1), value.getClass());
				declaredMethod.setAccessible(true);

				declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
				// Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				// declaredField.setAccessible(true);
				// declaredField.set(bean, value);
				Field declaredField = null;
				try {
					declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
					declaredField.setAccessible(true);
					declaredField.set(bean, value);
				} catch (NoSuchFieldException fieldException) {
					// Handle the case where the field doesn't exist
					// You can log an error or take appropriate action
					//logger.severe("An error occurred: " + e.getMessage());
					System.err.println(fieldException);
				}
			}
		}
	}
}
