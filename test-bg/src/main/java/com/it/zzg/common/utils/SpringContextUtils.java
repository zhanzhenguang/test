package com.it.zzg.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 * 
 * @author admin
 * @email admin
 * @date 2016年11月29日 下午11:45:51
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	public static ApplicationContext applicationContext; 

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}
	/*private ApplicationContext applicationContext; 

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.setApplicationContext(applicationContext);
	}

	public  Object getBean(String name) {
		return this.getApplicationContext().getBean(name);
	}

	public  <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public  boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public  boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public  Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}*/

}