package com.edaigou.dao;

import java.util.List;

import com.edaigou.entity.Shop;

public interface ShopDao {

	List<Shop> query();
	
	Shop getByNick(String nick);
}
