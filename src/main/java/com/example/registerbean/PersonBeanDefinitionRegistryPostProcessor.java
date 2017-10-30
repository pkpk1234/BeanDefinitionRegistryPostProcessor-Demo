package com.example.registerbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author 李佳明 https://github.com/pkpk1234/
 * @date 2017-10-30
 */
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
