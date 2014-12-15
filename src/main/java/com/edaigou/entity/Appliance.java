package com.edaigou.entity;

import javax.persistence.Entity;

import com.edaigou.entity.base.BaseAppliance;

@Entity
public class Appliance extends BaseAppliance{

	public Appliance(){}
	
	public Appliance(String appKey, String appSecret, String sessionKey,String nick) {
		super(appKey, appSecret, sessionKey,nick);
	}

	private static final long serialVersionUID = -3120764667106577705L;
}
