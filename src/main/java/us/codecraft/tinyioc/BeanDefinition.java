package us.codecraft.tinyioc;

/**
 * @author yihua.huang@dianping.com
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
