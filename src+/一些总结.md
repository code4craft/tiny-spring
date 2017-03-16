## 一些总结：

- 一层一层的封装，合理运用组合、继承类或接口来赋予、增强类的相应功能

- **接口的运用**：
	- 通过暴露接口方法来进行非侵入式嵌入(例如：暴露BeanPostProcessor接口，实现该接口的类会优先于普通bean的实例化并可在bean实例化前对bean做一些初始化操作,例：aop织入)
	- BeanFactoryAware接口暴露了获取beanFactory的能力，继承该接口的类拥有操作beanFactory的能力，也就能具体的操作bean了。

- **模板方法模式以及hook方法的应用**：
　　例如: 在AbstractBeanFactory中规范了bean的加载，实例化，初始化，获取的过程。AutowireCapableBeanFactory里实现了hook方法(applyPropertyValues方法)，该方法在AbstractBeanFactory#initializeBean方法中调用，AbstractBeanFactory中有默认的hook方法空实现。

- **工厂方法模式的应用**：例如：BeanFactory#getBean，由子类决定怎样去获取bean并在获取时进行相关操作。工厂方法把实例化推迟到子类。

- **外观(门面)模式的运用**：ClassPathXmlApplicationContext对 Resouce 、 BeanFactory、BeanDefinition 
进行了功能的封装，解决 根据地址获取资源通过 IoC 容器注册bean定义并实例化，初始化bean的问题，并提供简单运用他们的方法。

- **代理模式的运用**：
	- 通过jdk的动态代理：jdk的动态代理是基于接口的，必须实现了某一个或多个任意接口才可以被代理，并且只有这些接口中的方法会被代理。
	- 通过cglib动态代理：cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中的方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。

- **单例模式的运用**：
	- tiny-spring默认是单例bean，在AbstractApplicationContext#refresh里注册bean定义，初始化后，默认用单例形式实例化bean：preInstantiateSingletons方法里获取beanDefinition的name后通过getBean(name)方法实例化bean，下次再getBean(name)时会先检查该name的beanDefinition里的bean是否已经实例化，如果已经实例化了，则返回那个bean的引用而不是再实例化一个新的bean返回
	- 标准单例模式中一般的实现方式是：第一次通过getInstance(双重检查)实例化该类的对象并保存，下次再getInstance时返回该对象。

- **策略模式**：
　　这里有个想法，看ClassPathXMLApplicationContext构造方法可以知道是默认用自动装配的策略，在这里可以另外自己写个类继承AbstractBeanFactory，重写applyPropertyValues方法实现装配策略，在初始化的时候就可以选择不同的装配策略了。