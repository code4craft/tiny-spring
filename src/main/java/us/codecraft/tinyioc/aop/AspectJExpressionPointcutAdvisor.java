package us.codecraft.tinyioc.aop;

import org.aopalliance.aop.Advice;

/**
 * @author yihua.huang@dianping.com
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor{

    @Override
    public Advice getAdvice() {
        return null;
    }

    @Override
    public Pointcut getPointcut() {
        return null;
    }
}
