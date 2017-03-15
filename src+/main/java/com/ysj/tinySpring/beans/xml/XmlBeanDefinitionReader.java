package com.ysj.tinySpring.beans.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ysj.tinySpring.BeanReference;
import com.ysj.tinySpring.beans.AbstractBeanDefinitionReader;
import com.ysj.tinySpring.beans.BeanDefinition;
import com.ysj.tinySpring.beans.PropertyValue;
import com.ysj.tinySpring.beans.io.ResourceLoader;

/**
 * 从 XML 文件中读取bean的定义。具体实现了 loadBeanDefinitions() 方法
 * 注意: 在loadBeanDefinitions中并没有实例化bean，而只是注册了bean的定义
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	/**
	 * 加载并通过bean定义注册bean
	 */
	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		// 获取资源输入流
		InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
		/**
		 * 从xml文件中读取所有bean的定义，并注册到registry中
		 * 注意: 此时bean定义里并没有实例化该bean
		 */
		doLoadBeanDefinitions(inputStream);
	}

	protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
		// 获取工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 获取生成器
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		// 解析为Document
		Document doc = docBuilder.parse(inputStream);
		// 解析并注册其中的bean
		registerBeanDefinitions(doc);
		inputStream.close();
	}
	
	public void registerBeanDefinitions(Document doc) {
		// 获取根标签<beans>
		Element root = doc.getDocumentElement();

		parseBeanDefinitions(root);
	}
	
	protected void parseBeanDefinitions(Element root) {
		// 获取所有<beans>下的<bean>
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			// 如果该子标签是Element
			if (node instanceof Element) {
				Element ele = (Element) node;
				// 解析bean标签
				processBeanDefinition(ele);
			}
		}
	}
	
	/**
	 * 处理(解析)bean标签
	 * @param ele
	 */
	protected void processBeanDefinition(Element ele) {
		// 获取bean标签的id属性作为bean的name
		String name = ele.getAttribute("id");
		// 获取bean标签的class属性作为bean的className
		String className = ele.getAttribute("class");
		
		BeanDefinition beanDefinition = new BeanDefinition();
		// 解析bean标签下的property子标签
		processProperty(ele, beanDefinition);
		// 设置className的同时，也在内部设置了Class
		beanDefinition.setBeanClassName(className);
		// 注册类定义
		getRegistry().put(name, beanDefinition);
	}
	
	/**
	 * 解析bean标签的property子标签
	 * @param ele
	 * @param beanDefinition
	 */
	private void processProperty(Element ele, BeanDefinition beanDefinition) {
		// 获取所有property标签
		NodeList propertyNodes = ele.getElementsByTagName("property");
		for (int i = 0; i < propertyNodes.getLength(); i++) {
			Node node = propertyNodes.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				// 如果是value型的属性值
				if (value != null && value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				} else {
					// 否则是ref型的属性值
					String ref = propertyEle.getAttribute("ref");
					if (ref == null || ref.length() == 0) {
						throw new IllegalArgumentException("Configuration problem: <property> element for property '"
								+ name + "' must specify a ref or value");
					}
					BeanReference beanReference = new BeanReference(ref);
					// 保存一个ref型属性值的属性
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
	
}
