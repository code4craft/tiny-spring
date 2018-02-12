package com.ysj.beans.io;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.ysj.tinySpring.beans.io.Resource;
import com.ysj.tinySpring.beans.io.ResourceLoader;

/**
 * @author yihua.huang@dianping.com
 */
public class ResourceLoaderTest {

	@Test
	public void test() throws IOException {
		ResourceLoader resourceLoader = new ResourceLoader();
		Resource resource = resourceLoader.getResource("tinyioc.xml");
		InputStream inputStream = resource.getInputStream();
		Assert.assertNotNull(inputStream);
	}
}
