package com.edaigou.entity;

import javax.persistence.Entity;

import com.edaigou.entity.base.BaseShop;

@Entity
public class Shop extends BaseShop{
	
	public Shop(){}

	public Shop(String name, String nick, String url) {
		super(name, nick, url);
	}

	private static final long serialVersionUID = -3568585538577154977L;
}
