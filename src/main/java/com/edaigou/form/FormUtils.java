package com.edaigou.form;

import java.lang.reflect.Field;

import org.eclipse.swt.widgets.Text;

public abstract class FormUtils {

	
	@SuppressWarnings("unchecked")
	public static <T> T get(String name, Object form) {
		try {
			Field field=	form.getClass().getDeclaredField(name);
			if(field!=null){
				field.setAccessible(true);
				return (T) field.get(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Text  getText(String name, Object form) {
		try {
			Field field=	form.getClass().getDeclaredField(name);
			if(field!=null){
				field.setAccessible(true);
				return  (Text) field.get(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
