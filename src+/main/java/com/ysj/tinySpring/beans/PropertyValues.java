package com.ysj.tinySpring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一个对象所有的PropertyValue。
 * 为什么封装而不是直接用List? 因为可以封装一些操作。
 *
 */
public class PropertyValues {

	private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();
	
	public PropertyValues() {}

	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

	public List<PropertyValue> getPropertyValuesList() {
		return this.propertyValueList;
	}
	
}
