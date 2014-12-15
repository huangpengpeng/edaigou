package com.edaigou.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.edaigou.dao.ApplianceDao;
import com.edaigou.entity.Appliance;

@Repository
public class ApplianceDaoImpl extends JdbcTemplateBaseDao implements ApplianceDao{

	@Override
	public List<Appliance> getByNick(String nick) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Appliance where 1=1");
		if(nick!=null){
			sqlBuilder.andEqualTo("nick", nick);
		}
		return query(sqlBuilder);
	}

	@Override
	protected Class<?> getEntityClass() {
		return Appliance.class;
	}

	@Override
	public List<Appliance> query() {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Appliance where 1=1");
		return query(sqlBuilder);
	}

	@Override
	public void update(Long id, String sessionKey) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update Appliance set gmtModify=current_timestamp()");
		
		if(sessionKey!=null){
			sqlBuilder.set("sessionKey", sessionKey);
		}
		
		super.update(id, sqlBuilder);
	}

}
