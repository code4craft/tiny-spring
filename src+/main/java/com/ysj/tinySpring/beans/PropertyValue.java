package com.ysj.tinySpring.beans;

/**
 * 封装bean的属性
 *
 */
public class PropertyValue {

	private final String name;

	/**
	 * 在 Spring 的 XML 中的 property 中，键是 key ，值是 value 或者 ref。对于 value 只要直接注入属性就行
	 * 了，但是 ref 要先进行解析,转化为对应的实际 Object。
	 */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
	
}
