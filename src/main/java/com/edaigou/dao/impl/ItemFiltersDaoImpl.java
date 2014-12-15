package com.edaigou.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.edaigou.dao.ItemFiltersDao;
import com.edaigou.entity.ItemFilters;

@Repository
public class ItemFiltersDaoImpl extends JdbcTemplateBaseDao implements ItemFiltersDao{

	@Override
	public void add(ItemFilters itemFilters) {
		super.add(itemFilters);
	}
	

	@Override
	protected Class<?> getEntityClass() {
		return ItemFilters.class;
	}


	@Override
	public ItemFilters getByItemAndNick(Long pNumIid, String nick) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from ItemFIlters where 1=1");
		if(pNumIid!=null){
			sqlBuilder.andEqualTo("pNumIid", pNumIid);
		}
		if(nick!=null){
			sqlBuilder.andEqualTo("nick", nick);
		}
		return queryForObject(sqlBuilder);
	}


	@Override
	public List<ItemFilters> query() {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from ItemFilters where 1=1");
		return query(sqlBuilder);
	}


	@Override
	public List<ItemFilters> getByNumIid(Long pNumIid) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"select * from ItemFilters where  pNumIid=" + pNumIid
						+ " or pNumIid is null");
		return query(sqlBuilder);
	}


	@Override
	public void delete(Long id) {
		super.delete(id);
	}
}
