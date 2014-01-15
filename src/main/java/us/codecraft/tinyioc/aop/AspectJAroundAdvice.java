package us.codecraft.tinyioc.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import us.codecraft.tinyioc.beans.factory.BeanFactory;

import java.lang.reflect.Method;

/**
 * @author yihua.huang@dianping.com
 */
public class AspectJAroundAdvice implements Advice, MethodInterceptor {

	private BeanFactory beanFactory;

	private Method aspectJAdviceMethod;

	private String aspectInstanceName;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
        return aspectJAdviceMethod.invoke(beanFactory.getBean(aspectInstanceName), invocation);
	}

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Method getAspectJAdviceMethod() {
        return aspectJAdviceMethod;
    }

    public void setAspectJAdviceMethod(Method aspectJAdviceMethod) {
        this.aspectJAdviceMethod = aspectJAdviceMethod;
    }

    public String getAspectInstanceName() {
        return aspectInstanceName;
    }

    public void setAspectInstanceName(String aspectInstanceName) {
        this.aspectInstanceName = aspectInstanceName;
    }
}
