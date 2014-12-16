package com.edaigou.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.common.jdbc.page.Pagination;
import com.edaigou.dao.ItemDao;
import com.edaigou.entity.Item;

@Repository
public class ItemDaoImpl extends JdbcTemplateBaseDao implements ItemDao {

	@Override
	public long add(Item item) {
		return super.add(item);
	}

	@Override
	protected Class<?> getEntityClass() {
		return Item.class;
	}

	@Override
	public Pagination getPageOfMap(Long [] ids,Long shopId, String title,String status, Integer pageNo,Integer pageSize,Boolean ifItemExists) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"select i.*,i2.*,s.name as shopName,s.nick,s.url as shopUrl from Item as i left join PromotionItem as i2 on i.id=i2.id left join Shop as s on i.shopId=s.id where 1=1");
		if (shopId != null) {
			sqlBuilder.andEqualTo("s.id", shopId);
		}
		if (title != null && title.length() > 0) {
			sqlBuilder.andLike("i.title", "%" + title + "%");
		}
		if (status != null && status.length() > 0) {
			sqlBuilder.andEqualTo("i.status", status);
		}
		if(ifItemExists){
			sqlBuilder.andIsNull("i.sNumIid");
		}
		if(!ArrayUtils.isEmpty(ids)){
			sqlBuilder.andIn("i.id", ids);
		}
		sqlBuilder.append(" order by i.gmtCreated desc");
		return getPageMap(sqlBuilder, pageNo == null ? 1 : pageNo, pageSize);
	}
	
	@Override
	public void edit(Long id, String title, String imageByte, Long pNumIid,
			Long sNumIid,boolean ifsNumIidNull, Double realSalesPrice, Double realSaleDiscount,
			Double realProfit, Double profitDifference,Double minPrice, Long shopId,Double shopSalePrice,String pType) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update Item set gmtModify=current_timestamp()");
		if (imageByte != null && imageByte.length() > 0) {
			sqlBuilder.set("title", title);
		}
		if (imageByte != null && imageByte.length() > 0) {
			sqlBuilder.set("imageByte", imageByte);
		}
		if (pNumIid != null) {
			sqlBuilder.set("pNumIid", pNumIid);
		}
		if (sNumIid != null || ifsNumIidNull) {
			sqlBuilder.set("sNumIid", sNumIid);
		}

		if (realSalesPrice != null) {
			sqlBuilder.set("realSalesPrice", realSalesPrice);
		}
		if (realSaleDiscount != null) {
			sqlBuilder.set("realSaleDiscount", realSaleDiscount);
		}
		if (realProfit != null) {
			sqlBuilder.set("realProfit", realProfit);
		}

		if (profitDifference != null) {
			sqlBuilder.set("profitDifference", profitDifference);
		}
		
		if (minPrice != null) {
			sqlBuilder.set("minPrice", minPrice);
		}

		if (shopId != null) {
			sqlBuilder.set("shopId", shopId);
		}
		if (shopSalePrice != null) {
			sqlBuilder.set("shopSalePrice", shopSalePrice);
		}
		if (pType != null) {
			sqlBuilder.set("pType", pType);
		}
		update(id, sqlBuilder);
	}

	@Override
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	public List<Item> getByStatus(String status) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Item where 1=1");
		if(status!=null && status.length() >0){
			sqlBuilder.andEqualTo("status", status);
		}
		return query(sqlBuilder);
	}

	@Override
	public Item getByPNumIid(Long pNumIid) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Item where 1=1");
		if(pNumIid!=null){
			sqlBuilder.andEqualTo("pNumIid", pNumIid);
		}
		return queryForObject(sqlBuilder);
	}
	
	@Override
	public List<Item> getBySNumIid(Long sNumIid) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Item where 1=1");
		if(sNumIid!=null){
			sqlBuilder.andEqualTo("sNumIid", sNumIid);
		}
		return query(sqlBuilder);
	}

	@Override
	public void edit(Long id, String status) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update Item set gmtModify=current_timestamp()");
		if(status!=null){
			sqlBuilder.set("status", status);
		}
		super.update(id, sqlBuilder);
	}

	@Override
	public void editDetail(Long id, String detail) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update Item set gmtModify=current_timestamp()");
		if (detail != null ) {
			sqlBuilder.set("detail", detail);
		}
		super.update(id, sqlBuilder);
	}

	@Override
	public List<Item> query(Long[] ids, String title, Long  shopId,
			String status) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Item where 1=1");
		if(!ArrayUtils.isEmpty(ids)){
			sqlBuilder.andIn("id", ids);
		}
		if(title!=null && title.length() >0){
			sqlBuilder.andEqualTo("title", title);
		}
		if(shopId!=null){
			sqlBuilder.andEqualTo("shopId", shopId);
		}
		if(status!=null){
			sqlBuilder.andEqualTo("status", status);
		}
		return query(sqlBuilder);
	}

	@Override
	public Item get(Long id) {
		return super.queryForObject(id);
	}

}
