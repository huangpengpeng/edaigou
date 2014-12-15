package com.edaigou.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.edaigou.dao.ShopDao;
import com.edaigou.entity.Shop;

@Repository
public class ShopDaoImpl extends JdbcTemplateBaseDao implements ShopDao {

	@Override
	public List<Shop> query() {
		SqlBuilder sqlBuilder = new SqlBuilder("select * from Shop where 1=1");
		return query(sqlBuilder);
	}

	@Override
	protected Class<?> getEntityClass() {
		return Shop.class;
	}

	@Override
	public Shop getByNick(String nick) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Shop where 1=1");
		if(nick!=null){
			sqlBuilder.andEqualTo("nick", nick);
		}
		return queryForObject(sqlBuilder);
	}
}
