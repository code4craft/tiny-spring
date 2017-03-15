package com.ysj.tinySpring.beans.io;

import java.net.URL;

/**
 * 资源加载类
 *
 */
public class ResourceLoader {
	
	/**
	 * 获取一个 Resouce 对象
	 * @param location
	 * @return
	 */
	public Resource getResource(String location){
		// 通过给定名称查找资源
		URL resource = this.getClass().getClassLoader().getResource(location);
		/*
		 * 注： 这里在设计上有一定的问题，ResourceLoader 直接返回了一个 UrlResource，更好的方法是声明
		 * 一个 ResourceLoader 接口，再实现一个 UrlResourceLoader 类用于加载 UrlResource。
		 */
        return new UrlResource(resource);
	}
	
}
