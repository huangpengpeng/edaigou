package com.edaigou.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.NumberUtils;
import com.common.util.WebUtils;
import com.edaigou.service.TaobaoItemSvc;
import com.taobao.api.domain.AitaobaoItem;
import com.taobao.api.domain.AitaobaoItemDetail;
import com.taobao.api.domain.AitaobaoShop;
import com.taobao.biz.manager.impl.ShopMngImpl;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

@Transactional
@Service
public class TaobaoItemSvcImpl implements TaobaoItemSvc {

	@Override
	public Map<String, Object> getAitaobaoItem(String url) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(url)) {
			throw new IllegalStateException("url is　not null");
		}
		Long numIid = null;
		try {
			numIid = NumberUtils.toLong(WebUtils.getParameters(url, "id"));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}

		AitaobaoItemDetail detail = new TaobaoItemMngImpl()
				.getAitaobaoOfItem(numIid);
		if (detail == null || detail.getItem() == null) {
			throw new IllegalStateException("此商品没有淘客");
		}

		com.taobao.api.domain.Item item = detail.getItem();

		/***************** 商品基础数据 ************************/
		modelMap.put("title", item.getTitle());
		modelMap.put("image", item.getPicUrl());
		modelMap.put("numIid", item.getNumIid());
		modelMap.put("url", url);
		modelMap.put("originalPrice", item.getPrice());
		modelMap.put("salePrice", Double.valueOf(item.getPrice()));
		modelMap.put("image", item.getPicUrl());
		/********************* 商品优惠寄淘宝客数据 ******************************/

		AitaobaoItem couponItem = new TaobaoItemMngImpl().getCouponItem(
				item.getTitle(), item.getCid(), "B", null, null, numIid,
				"commissionRate_desc");

		if (couponItem == null) {
			couponItem = new TaobaoItemMngImpl().getAitaobaoItem(
					item.getTitle(), item.getCid(), numIid,
					"commissionRate_desc");
		}

		if (couponItem != null) {
			Double salePrice = 0D;

			if (couponItem.getCouponPrice() != null) {
				salePrice = Double.valueOf(couponItem.getCouponPrice());
			} else if (couponItem.getPromotionPrice() != null) {
				salePrice = Double.valueOf(couponItem.getPromotionPrice());
			} else {
				salePrice = Double.valueOf(couponItem.getPrice());
			}

			Double commissionRate = Double.valueOf(couponItem
					.getCommissionRate()) / 100;
			Double commission = Double.valueOf(salePrice)
					* (commissionRate / 100);
			modelMap.put("commission", commission);
			modelMap.put("commissionRate", commissionRate);
			modelMap.put("salePrice", salePrice);
		}

		/****************************** 计算数据 *********************************/
		// 计算实际小时价格
		Double salePrice = Double.valueOf(modelMap.get("salePrice").toString());
		Double realSalePrice = NumberUtils.toDouble(new DecimalFormat("#.00")
				.format(salePrice - salePrice * 0.12));

		// 计算实际小时折扣
		Double realSaleDiscount = NumberUtils
				.toDouble(new DecimalFormat("#.00").format(realSalePrice
						/ NumberUtils.toDouble(item.getPrice())));

		modelMap.put("realSalePrice", realSalePrice);
		modelMap.put("realSaleDiscount", realSaleDiscount);
		return modelMap;
	}

}
