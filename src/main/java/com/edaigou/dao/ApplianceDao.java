package com.edaigou.dao;

import java.util.List;

import com.edaigou.entity.Appliance;

public interface ApplianceDao {

	List<Appliance> getByNick(String nick);
	
	void update(Long id, String sessionKey) ;
	
	List<Appliance> query();
}
