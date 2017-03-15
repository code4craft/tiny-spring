package com.ysj.tinySpring.aop;

/**
 * 继承了AopProxy接口，有获取代理对象的能力
 * 同时继承此接口有AdvisedSupport的支持
 *
 */
public abstract class AbstractAopProxy implements AopProxy{

	protected AdvisedSupport advised;
	
	public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }
	
}
