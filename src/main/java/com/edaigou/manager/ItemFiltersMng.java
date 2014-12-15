package com.edaigou.manager;

import java.util.List;

import com.edaigou.entity.ItemFilters;

public interface ItemFiltersMng {

	void add(Long pNumIid, String nick);

	ItemFilters getByItemAndNick(Long pNumIid, String nick);
	
	List<ItemFilters> query();
	
	List<ItemFilters> getByNumIid(Long pNumIid);
	
	void delete(Long id);
}
