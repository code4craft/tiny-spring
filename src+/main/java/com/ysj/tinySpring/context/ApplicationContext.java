package com.ysj.tinySpring.context;

import com.ysj.tinySpring.beans.factory.BeanFactory;

/**
 * 以 ApplicationContext 接口为核心发散出的几个类，主要是对前面 Resouce 、 BeanFactory、BeanDefinition 
 * 进行了功能的封装，解决 根据地址获取资源通过 IoC 容器注册bean定义并实例化bean的问题。
 * 
 * 通常，要实现一个 IoC 容器时，需要先通过 ResourceLoader 获取一个 Resource，其中包括了容器的配置、Bean 
 * 的定义信息。接着，使用 BeanDefinitionReader接口暴露的方法读取并注册该 Resource 中的 BeanDefinition 信息。最后，
 * 把 BeanDefinition 保存在 BeanFactory 中，容器配置完毕可以使用。
 *
 */
public interface ApplicationContext extends BeanFactory{

}
