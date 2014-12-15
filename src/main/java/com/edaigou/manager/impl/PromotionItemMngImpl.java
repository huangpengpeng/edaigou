package com.edaigou.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edaigou.dao.PromotionItemDao;
import com.edaigou.entity.PromotionItem;
import com.edaigou.manager.PromotionItemMng;

@Transactional
@Service
public class PromotionItemMngImpl implements PromotionItemMng{

	@Override
	public void add(Long id, String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,String url) {
		PromotionItem promotionItem = new PromotionItem(title, imageByte,
				imageUrl, numIid, originalPrice, salePrice, commissionMoney,
				commissionRate, subsidyRate, subsidy, sumCommissionRate,
				sumCOmmissionMoney, incomeTax, serviceFee,url);
		promotionItem.init();
		dao.save(promotionItem);
	}

	@Override
	public PromotionItem getByNumIid(Long numIid) {
		return dao.getByNumIid(numIid);
	}
	

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}
	

	@Override
	public PromotionItem get(Long id) {
		return dao.get(id);
	}
	
	@Override
	public void edit(Long id, String title, String imageByte, String imageUrl,
			Long numIid, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,
			String url) {
		dao.edit(id, title, imageByte, imageUrl, numIid, originalPrice, salePrice, commissionMoney, commissionRate, subsidyRate, subsidy, sumCommissionRate, sumCOmmissionMoney, incomeTax, serviceFee, url);
	}
	
	@Autowired
	private PromotionItemDao dao;

}
