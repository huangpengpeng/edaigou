package com.edaigou.entity.base;

import javax.persistence.MappedSuperclass;

import com.common.jdbc.BaseEntity;

@MappedSuperclass
public class BaseAppliance extends BaseEntity{

	private static final long serialVersionUID = 1828286880365941293L;
	
	public BaseAppliance(){}
	
	public BaseAppliance(String appKey,String appSecret,String sessionKey,String nick){
		this.setAppKey(appKey);
		this.setAppSecret(appSecret);
		this.setSessionKey(sessionKey);
		this.setNick(nick);
	}
	
	private String nick;

	private String appKey;
	
	private String appSecret;
	
	private String sessionKey;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
