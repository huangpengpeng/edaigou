package com.edaigou.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.edaigou.dao.ItemErrorsDao;
import com.edaigou.entity.ItemErrors;

@Repository
public class ItemErrorsDaoImpl extends JdbcTemplateBaseDao implements
		ItemErrorsDao {

	@Override
	public void add(ItemErrors itemErrors) {
		super.add(itemErrors);
	}

	@Override
	public ItemErrors getByItemAndType(Long itemId, String errorType) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"select * from ItemErrors where 1=1");
		if (itemId != null) {
			sqlBuilder.andEqualTo("itemId", itemId);
		}
		if (errorType != null) {
			sqlBuilder.andEqualTo("errorType", errorType);
		}
		return queryForObject(sqlBuilder);
	}

	@Override
	protected Class<?> getEntityClass() {
		return ItemErrors.class;
	}

	@Override
	public List<ItemErrors> getByErrorType(String errorType) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from ItemErrors where 1=1");
		
		if(errorType!=null){
			sqlBuilder.andEqualTo("errorType", errorType);
		}
		return query(sqlBuilder);
	}

	@Override
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	public List<ItemErrors> getByItem(Long itemId) {
SqlBuilder sqlBuilder=new SqlBuilder("select * from ItemErrors where 1=1");
		
		if(itemId!=null){
			sqlBuilder.andEqualTo("itemId", itemId);
		}
		return query(sqlBuilder);
	}

}
