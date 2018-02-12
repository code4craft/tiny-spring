package com.ysj.tinySpring.aop;

/**
 * 封装被代理的对象的类的相关信息
 *
 */
public class TargetSource {

	// 原对象
	private Object target;
	
	private Class<?> targetClass;

    private Class<?>[] interfaces;


    public TargetSource(Object target, Class<?> targetClass,Class<?>... interfaces) {
		this.target = target;
		this.targetClass = targetClass;
        this.interfaces = interfaces;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Object getTarget() {
		return target;
	}

    public Class<?>[] getInterfaces() {
        return interfaces;
    }
    
}
