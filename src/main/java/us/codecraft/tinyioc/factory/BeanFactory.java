package us.codecraft.tinyioc.factory;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * @author yihua.huang@dianping.com
 */
public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
