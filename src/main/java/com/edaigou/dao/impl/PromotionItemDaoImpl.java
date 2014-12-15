package com.edaigou.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.edaigou.dao.PromotionItemDao;
import com.edaigou.entity.PromotionItem;

@Repository
public class PromotionItemDaoImpl extends JdbcTemplateBaseDao implements PromotionItemDao{

	@Override
	public void save(PromotionItem promotionItem) {
		super.save(promotionItem);
	}

	@Override
	protected Class<?> getEntityClass() {
		return PromotionItem.class;
	}

	@Override
	public PromotionItem getByNumIid(Long numIid) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from PromotionItem  where 1=1");
		if(numIid!=null){
			sqlBuilder.andEqualTo("numIid", numIid);
		}
		return queryForObject(sqlBuilder);
	}

	@Override
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	public PromotionItem get(Long id) {
		return super.queryForObject(id);
	}

	@Override
	public void edit(Long id, String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,
			String url) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update PromotionItem set gmtModify=current_timestamp()");
		if (title != null && title.length() > 0) {
			sqlBuilder.set("title", title);
		}
		if (imageByte != null && imageByte.length() > 0) {
			sqlBuilder.set("imageByte", imageByte);
		}
		if (imageUrl != null && imageUrl.length() > 0) {
			sqlBuilder.set("imageUrl", imageUrl);
		}
		if (numIid != null) {
			sqlBuilder.set("numIid", numIid);
		}
		if (originalPrice != null) {
			sqlBuilder.set("originalPrice", originalPrice);
		}
		if (salePrice != null) {
			sqlBuilder.set("salePrice", salePrice);
		}

		if (commissionMoney != null) {
			sqlBuilder.set("commissionMoney", commissionMoney);
		}
		if (commissionRate != null) {
			sqlBuilder.set("commissionRate", commissionRate);
		}
		if (subsidyRate != null) {
			sqlBuilder.set("subsidyRate", subsidyRate);
		}
		if (subsidy != null) {
			sqlBuilder.set("subsidy", subsidy);
		}
		if (sumCommissionRate != null) {
			sqlBuilder.set("sumCommissionRate", sumCommissionRate);
		}
		if (sumCOmmissionMoney != null) {
			sqlBuilder.set("sumCOmmissionMoney", sumCOmmissionMoney);
		}
		if (incomeTax != null) {
			sqlBuilder.set("incomeTax", incomeTax);
		}
		if (serviceFee != null) {
			sqlBuilder.set("serviceFee", serviceFee);
		}
		if (url != null) {
			sqlBuilder.set("url", url);
		}
		update(id, sqlBuilder);
	}
}
