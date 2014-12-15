package com.edaigou.manager;

import com.edaigou.entity.PromotionItem;

public interface PromotionItemMng {

	void add(Long id,String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,String url);
	
	void edit(Long id,String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,
			String url);
	
	PromotionItem getByNumIid(Long numIid);
	
	PromotionItem get(Long id);
	
	void delete(Long id);
}
