package us.codecraft.tinyioc;

import org.junit.Test;
import us.codecraft.tinyioc.factory.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.factory.BeanFactory;

/**
 * @author yihua.huang@dianping.com
 */
public class BeanFactoryTest {

	@Test
	public void test() {
		// 1.初始化beanfactory
		BeanFactory beanFactory = new AutowireCapableBeanFactory();

		// 2.注入bean
		BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("us.codecraft.tinyioc.HelloWorldService");
		beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 3.获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }
}
