package com.edaigou.form;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ApplicationContextUtils{
	
	private static	ApplicationContext applicationContext;
	
	public static void newInstance(){
		applicationContext= new FileSystemXmlApplicationContext("classpath:/com/edaigou/config/application-context.xml"); 
	}	
	
	public static void publishEvent(){
		applicationContext.publishEvent(new ApplicationContextEvent(applicationContext));
	}
	
	public static <T> T getBean(Class<?> requiredType) {
		return (T) applicationContext.getBean(requiredType);
	}
	
	public static class ApplicationContextEvent extends ApplicationEvent {
		
	    private	ApplicationContext applicationContext;

		public ApplicationContextEvent(Object source) {
			super(source);
			this.applicationContext=(ApplicationContext) source;
		}

		public <T> T getBean(Class<?> requiredType) {
			return (T) applicationContext.getBean(requiredType);
		}
		
		private static final long serialVersionUID = 2270780677321421792L;
	}
}
