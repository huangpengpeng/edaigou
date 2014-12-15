package com.edaigou.dao;

import com.edaigou.entity.PromotionItem;

public interface PromotionItemDao {

	void save(PromotionItem promotionItem);
	
	void edit(Long id,String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,
			String url);
	
	PromotionItem getByNumIid(Long numIid);
	
	void delete(Long id);
	
	PromotionItem get(Long id);
}
