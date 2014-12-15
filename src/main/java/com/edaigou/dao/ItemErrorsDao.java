package com.edaigou.dao;

import java.util.List;

import com.edaigou.entity.ItemErrors;

public interface ItemErrorsDao {
	
	void add(ItemErrors itemErrors);
	
	ItemErrors getByItemAndType(Long itemId,String errorType);
	
	 List<ItemErrors> getByErrorType(String errorType);
	 
	 List<ItemErrors> getByItem(Long itemId);
	 
	 void delete(Long id);
}
