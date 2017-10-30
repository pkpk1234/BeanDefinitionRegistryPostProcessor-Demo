# 动态注册bean,，Spring官方套路：使用BeanDefinitionRegistryPostProcessor

标签（空格分隔）： Spring

---
之前写过关于Spring总动态注册Bean的两篇文章
[Spring动态注册bean][1]
[动态注册bean，Spring官方套路：使用ImportBeanDefinitionRegistrar][2]

这里再介绍一个Spring官方大量使用的接口BeanDefinitionRegistryPostProcessor，这个接口扩展自BeanFactoryPostProcessor，专门用于动态注册Bean。
此外，Spring5中的BeanDefinitionBuilder还增加了一个新的genericBeanDefinition方法，签名如下：
```java

public static <T> BeanDefinitionBuilder genericBeanDefinition(
			@Nullable Class<T> beanClass, Supplier<T> instanceSupplier)
```
可以看到，此处在会用Supplier接口的返回值作为bean的实例。
这样就提供了替代传统的static和instance factory-mehtod的功能，前面两者分别需要调用BeanDefinitionBuilder#setFactoryMethod和BeanDefinitionBuilder#setFactoryMethodOnBean专门进行配置。

因为这里要使用Spring5，所以Spring Boot要使用2.x版本，编写该文章时，最新版本为2.0.0.M5，该依赖并没有发布到maven中央仓库中，需要手工添加Spring的仓库，如下：
```xml
...省略
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.M5</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<repositories>
    <repository>
        <id>spring-snapshots</id>
        <name>Spring Snapshots</name>
        <url>https://repo.spring.io/snapshot</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>

<pluginRepositories>
    <pluginRepository>
        <id>spring-snapshots</id>
        <name>Spring Snapshots</name>
        <url>https://repo.spring.io/snapshot</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </pluginRepository>
    <pluginRepository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
</pluginRepositories>    

...省略
```


核心代码如下：
```java
@Component
@Slf4j
public class PersonBeanDefinitionRegistryPostProcessor
		implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
			throws BeansException {
		// 注册Bean定义，容器根据定义返回bean
		log.info("register personManager1>>>>>>>>>>>>>>>>");
		//构造bean定义
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(PersonManager.class);
		//设置依赖
		beanDefinitionBuilder.addPropertyReference("personDao", "personDao");
		BeanDefinition personManagerBeanDefinition = beanDefinitionBuilder
				.getRawBeanDefinition();
		//注册bean定义
		registry.registerBeanDefinition("personManager1", personManagerBeanDefinition);

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		// 注册Bean实例，使用supply接口
		log.info("register personManager2>>>>>>>>>>>>>>>>");
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(PersonManager.class, () -> {
					PersonDao personDao = beanFactory.getBean(PersonDao.class);
					PersonManager personManager = new PersonManager();
					personManager.setPersonDao(personDao);
					return personManager;
				});
		BeanDefinition personManagerBeanDefinition = beanDefinitionBuilder
				.getRawBeanDefinition();
		((DefaultListableBeanFactory) beanFactory)
				.registerBeanDefinition("personManager2", personManagerBeanDefinition);
	}
}
```
完整代码：https://github.com/pkpk1234/BeanDefinitionRegistryPostProcessor-Demo

官方例子：
[ConfigurationClassPostProcessor][3]


  [1]: https://zhuanlan.zhihu.com/p/30070328
  [2]: https://zhuanlan.zhihu.com/p/30123517
  [3]: https://github.com/spring-projects/spring-framework/blob/5f4d1a4628513ab34098fa3f92ba03aa20fc4204/spring-context/src/main/java/org/springframework/context/annotation/ConfigurationClassPostProcessor.java
