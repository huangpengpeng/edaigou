package com.edaigou.manager.impl;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edaigou.dao.ApplianceDao;
import com.edaigou.entity.Appliance;
import com.edaigou.manager.ApplianceMng;

@Transactional
@Service
public class ApplianceMngImpl implements ApplianceMng {

	@Override
	public Appliance getByNickOfOne(String nick, Integer count) {
		List<Appliance> appliances = dao.getByNick(nick);
		return appliances.get(count % appliances.size());
	}

	@Override
	public Appliance getByNickOfOne(String nick) {
		List<Appliance> appliances = dao.getByNick(nick);
		return appliances.get(RandomUtils.nextInt(appliances.size())
				% appliances.size());
	}

	@Override
	public List<Appliance> query() {
		return dao.query();
	}

	@Override
	public void update(Long id, String sessionKey) {
		dao.update(id, sessionKey);
	}
	
	
	@Autowired
	private ApplianceDao dao;
}
