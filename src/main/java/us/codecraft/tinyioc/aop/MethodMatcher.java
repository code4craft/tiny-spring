package us.codecraft.tinyioc.aop;

import java.lang.reflect.Method;

/**
 * @author yihua.huang@dianping.com
 */
public interface MethodMatcher {

    boolean matches(Method method, Class targetClass);
}
