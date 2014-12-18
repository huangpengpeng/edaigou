package com.edaigou.entity;

import javax.persistence.Entity;

import com.edaigou.entity.base.BaseItemErrors;

@Entity
public class ItemErrors extends BaseItemErrors{

	public ItemErrors(){}
	
	public ItemErrors(Long itemId, String errorType) {
		super(itemId, errorType);
	}
	
	public void init(){}
	
	public enum ItemErrorsType{
		低价,利差,低格错误,实售价不符,详情错误,天猫下架,淘宝客变动,重复编号,SKU变动,标题错误
	}

	private static final long serialVersionUID = -6521164283095986651L;
}
