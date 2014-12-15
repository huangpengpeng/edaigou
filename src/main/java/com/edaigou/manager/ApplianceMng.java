package com.edaigou.manager;

import java.util.List;

import com.edaigou.entity.Appliance;

public interface ApplianceMng {

	/**
	 * 根据循环数 随机去KEY
	 * @param nick
	 * @param count
	 * @return
	 */
	Appliance getByNickOfOne(String nick,Integer count);
	
	Appliance getByNickOfOne(String nick);
	
	List<Appliance> query();
	
	void  update(Long id,String sessionKey);
}
