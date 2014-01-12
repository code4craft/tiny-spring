package us.codecraft.tinyioc.factory;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * bean的容器
 * @author yihua.huang@dianping.com
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
