package com.edaigou.manager;

import java.util.List;

import com.edaigou.entity.Shop;

public interface ShopMng {

	List<Shop> query();
	
	Shop getByNick(String nick);
}
