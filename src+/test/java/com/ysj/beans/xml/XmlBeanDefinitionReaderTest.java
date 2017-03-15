package com.ysj.beans.xml;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ysj.tinySpring.beans.BeanDefinition;
import com.ysj.tinySpring.beans.io.ResourceLoader;
import com.ysj.tinySpring.beans.xml.XmlBeanDefinitionReader;

/**
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
		Assert.assertTrue(registry.size() > 0);
	}
}
