package us.codecraft.tinyioc;

/**
 * @author yihua.huang@dianping.com
 */
public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
