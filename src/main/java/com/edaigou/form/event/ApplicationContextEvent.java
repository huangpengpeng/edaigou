package com.edaigou.form.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

	private ApplicationContext applicationContext;

	public ApplicationContextEvent(Object source) {
		super(source);
		this.applicationContext = (ApplicationContext) source;
	}

	public <T> T getBean(Class<?> requiredType) {
		return (T) applicationContext.getBean(requiredType);
	}

	private static final long serialVersionUID = 6972501901674186212L;
}
