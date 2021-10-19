package us.codecraft.tinyioc;

/**
 * @author yihua.huang@dianping.com
 */
public class OutputServiceImpl implements OutputService {
    private HelloWorldService helloWorldService;
    @Override
    public void output(String text){
        System.out.println(text);
    }
}
