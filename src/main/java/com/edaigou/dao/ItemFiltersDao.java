package com.edaigou.dao;

import java.util.List;

import com.edaigou.entity.ItemFilters;

public interface ItemFiltersDao {

	void add(ItemFilters itemFilters);
	
	ItemFilters getByItemAndNick(Long pNumIid,String nick);
	
	List<ItemFilters> query();
	
	List<ItemFilters> getByNumIid(Long pNumIid) ;
	
	void delete(Long id);
}
