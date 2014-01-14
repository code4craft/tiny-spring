package us.codecraft.tinyioc.aop;

/**
 * @author yihua.huang@dianping.com
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
