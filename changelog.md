tiny-spring
=====

# 第一部分：IoC容器

## 1.step1-最基本的容器
	
	git checkout step-1-container-register-and-get

IoC最基本的角色有两个：容器(`BeanFactory`)和Bean本身。这里使用`BeanDefinition`来封装了bean对象，这样可以保存一些额外的元信息。测试代码：

```java
// 1.初始化beanfactory
BeanFactory beanFactory = new BeanFactory();

// 2.注入bean
BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

// 3.获取bean
HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
helloWorldService.helloWorld();
```

## 2.step2-将bean创建放入工厂

	git checkout step-2-abstract-beanfactory-and-do-bean-initilizing-in-it

step1中的bean是初始化好之后再set进去的，实际使用中，我们希望容器来管理bean的创建。于是我们将bean的初始化放入BeanFactory中。为了保证扩展性，我们使用Extract Interface的方法，将`BeanFactory`替换成接口，而使用`AbstractBeanFactory`和`AutowireCapableBeanFactory`作为其实现。"AutowireCapable"的意思是“可自动装配的”，为我们后面注入属性做准备。

```java
 // 1.初始化beanfactory
BeanFactory beanFactory = new AutowireCapableBeanFactory();

// 2.注入bean
BeanDefinition beanDefinition = new BeanDefinition();
beanDefinition.setBeanClassName("us.codecraft.tinyioc.HelloWorldService");
beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

// 3.获取bean
HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
helloWorldService.helloWorld();
```
	
## 3.step3-为bean注入属性

	git checkout step-3-inject-bean-with-property

这一步，我们想要为bean注入属性。我们选择将属性注入信息保存成`PropertyValue`对象，并且保存到`BeanDefinition`中。这样在初始化bean的时候，我们就可以根据PropertyValue来进行bean属性的注入。Spring本身使用了setter来进行注入，这里为了代码简洁，我们使用Field的形式来注入。
	
```java
// 1.初始化beanfactory
BeanFactory beanFactory = new AutowireCapableBeanFactory();

// 2.bean定义
BeanDefinition beanDefinition = new BeanDefinition();
beanDefinition.setBeanClassName("us.codecraft.tinyioc.HelloWorldService");

// 3.设置属性
PropertyValues propertyValues = new PropertyValues();
propertyValues.addPropertyValue(new PropertyValue("text", "Hello World!"));
beanDefinition.setPropertyValues(propertyValues);

// 4.生成bean
beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

// 5.获取bean
HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
helloWorldService.helloWorld();

```

## 4.step4-读取xml配置来初始化bean

	git checkout step-4-config-beanfactory-with-xml
	
这么大一坨初始化代码让人心烦。这里的`BeanDefinition`只是一些配置，我们还是用xml来初始化吧。我们定义了`BeanDefinitionReader`初始化bean，它有一个实现是`XmlBeanDefinitionReader`。

```java
// 1.读取配置
XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

// 2.初始化BeanFactory并注册bean
BeanFactory beanFactory = new AutowireCapableBeanFactory();
for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
        beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
}

// 3.获取bean
HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
helloWorldService.helloWorld();
```

## 5.step5-为bean注入bean

	git checkout step-5-inject-bean-to-bean
	
使用xml配置之后，似乎里我们熟知的Spring更近了一步！但是现在有一个大问题没有解决：我们无法处理bean之间的依赖，无法将bean注入到bean中，所以它无法称之为完整的IoC容器！如何实现呢？我们定义一个`BeanReference`，来表示这个属性是对另一个bean的引用。这个在读取xml的时候初始化，并在初始化bean的时候，进行解析和真实bean的注入。

```java
for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
    Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
    declaredField.setAccessible(true);
    Object value = propertyValue.getValue();
    if (value instanceof BeanReference) {
        BeanReference beanReference = (BeanReference) value;
        value = getBean(beanReference.getName());
    }
    declaredField.set(bean, value);
}
```

同时为了解决循环依赖的问题，我们使用lazy-init的方式，将createBean的事情放到`getBean`的时候才执行，是不是一下子方便很多？这样在注入bean的时候，如果该属性对应的bean找不到，那么就先创建！因为总是先创建后注入，所以不会存在两个循环依赖的bean创建死锁的问题。

```java
// 1.读取配置
XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

// 2.初始化BeanFactory并注册bean
AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
    beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
}

// 3.初始化bean
beanFactory.preInstantiateSingletons();

// 4.获取bean
HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
helloWorldService.helloWorld();
```

## 6.step6-ApplicationContext登场

	git checkout step-6-invite-application-context
	
现在BeanFactory的功能齐全了，但是使用起来有点麻烦。于是我们引入熟悉的`ApplicationContext`接口，并在`AbstractApplicationContext`的`refresh()`方法中进行bean的初始化工作。

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
helloWorldService.helloWorld();
```

是不是非常熟悉？至此为止，我们的tiny-spring的IoC部分可说完工了。这部分的类、方法命名和作用，都是对应Spring中相应的组件。虽然代码量只有400多行，但是已经有了基本的IoC功能！

# AOP
