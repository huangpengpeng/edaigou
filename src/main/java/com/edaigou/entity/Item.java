package com.edaigou.entity;

import javax.persistence.Entity;

import com.edaigou.entity.base.BaseItem;

@Entity
public class Item extends BaseItem {

	public Item() {
	}

	public Item(String title, String imageByte, Long pNumIid, Long sNumIid,
			Double realSalesPrice, Double realSaleDiscount, Double realProfit,
			Double profitDifference, Long shopId,String pType) {
		super(title, imageByte, pNumIid, sNumIid, realSalesPrice,
				realSaleDiscount, realProfit, profitDifference, shopId,pType);
	}

	public void init() {
		if (getStatus() == null) {
			setStatus(ItemStatus.创建.toString());
		}
	}

	public enum ItemPType {
		普通, 标题不一致, 高级淘宝客
	}

	public enum ItemStatus {
		创建, 上架, 下架
	}

	private static final long serialVersionUID = -4610601954978960639L;
}
