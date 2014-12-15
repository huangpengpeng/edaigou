package com.edaigou.entity;

import javax.persistence.Entity;

import com.edaigou.entity.base.BasePromotionItem;

@Entity
public class PromotionItem extends BasePromotionItem {

	public PromotionItem() {
	}

	public PromotionItem(String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,
			String url) {
		super(title, imageByte, imageUrl, numIid, originalPrice, salePrice,
				commissionMoney, commissionRate, subsidyRate, subsidy,
				sumCommissionRate, sumCOmmissionMoney, incomeTax, serviceFee,
				url);
	}

	public void init() {
	}

	private static final long serialVersionUID = -8634689390878854571L;
}
