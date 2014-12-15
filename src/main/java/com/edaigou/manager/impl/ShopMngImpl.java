package com.edaigou.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edaigou.dao.ShopDao;
import com.edaigou.entity.Shop;
import com.edaigou.manager.ShopMng;

@Transactional
@Service
public class ShopMngImpl implements ShopMng{

	@Override
	public List<Shop> query() {
		return dao.query();
	}
	
	@Override
	public Shop getByNick(String nick) {
		return dao.getByNick(nick);
	}
	
	@Autowired
	private ShopDao dao;

}
