package com.ysj.tinySpring.beans;

import java.util.HashMap;
import java.util.Map;

import com.ysj.tinySpring.beans.io.ResourceLoader;

/**
 * 实现 BeanDefinitionReader 接口的抽象类（未具体实现 loadBeanDefinitions，而是规范了
 *  BeanDefinitionReader 的基本结构）
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	/**
	 *  通过String - beanDefinition 键值对保存IoC容器里的类定义。
	 */
	private Map<String, BeanDefinition> registry;
	
	/**
	 * 保存了类加载器,使用时，只需要向其 loadBeanDefinitions() 传入一个资源地址，就可以自动调用其类加载器，
	 * 并把解析到的 BeanDefinition 保存到 registry 中去。
	 */
	private ResourceLoader resourceLoader;
	
	protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<String, BeanDefinition>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
