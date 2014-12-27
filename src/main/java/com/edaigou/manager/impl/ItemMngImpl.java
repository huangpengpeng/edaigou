package com.edaigou.manager.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.jdbc.page.Pagination;
import com.edaigou.dao.ItemDao;
import com.edaigou.entity.Item;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.entity.ItemFilters;
import com.edaigou.entity.PromotionItem;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemFiltersMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;

@Transactional
@Service
public class ItemMngImpl implements ItemMng {

	@Override
	public void add(String title, String imageByte, String imageUrl,
			Long pNumIid, Double realSalesPrice, Double realSaleDiscount, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Long shopId, String url,String pType) {
		Item item = new Item(title, imageByte, pNumIid, null, realSalesPrice,
				realSaleDiscount, getRealProfit(realSalesPrice, sumCOmmissionMoney, salePrice,subsidy), getProfitDifference(salePrice,
						realSalesPrice), shopId,pType);
		item.init();
		long id = dao.add(item);
		promotionItemMng.add(id, title, imageByte, imageUrl, pNumIid,
				originalPrice, salePrice, commissionMoney, commissionRate,
				subsidyRate, subsidy, sumCommissionRate, sumCOmmissionMoney,
				getIncomeTax(subsidy), getServiceFee(sumCOmmissionMoney), url);
	}

	@Override
	public void edit(Long id, Long pNumIid, String title, String imageByte,
			String imageUrl, Double realSalesPrice, Double realSaleDiscount,
			Double originalPrice, Double salePrice, Double commissionMoney,
			Double commissionRate, Double subsidyRate, Double subsidy,
			Double sumCommissionRate, Double sumCOmmissionMoney, Long shopId,
			String url, Long sNumIid, Double minPrice, Double shopSalePrice,
			String detail, String pType) {
		dao.edit(
				id,
				title,
				imageByte,
				null,
				sNumIid,
				true,
				realSalesPrice,
				realSaleDiscount,
				getRealProfit(realSalesPrice, sumCOmmissionMoney, salePrice,
						subsidy),
				getProfitDifference(salePrice, realSalesPrice), minPrice,
				shopId, shopSalePrice, pType);

		promotionItemMng.edit(id, title, imageByte, imageUrl, null,
				originalPrice, salePrice, commissionMoney, commissionRate,
				subsidyRate, subsidy, sumCommissionRate, sumCOmmissionMoney,
				getIncomeTax(subsidy), getServiceFee(sumCOmmissionMoney), url);

		ItemErrors itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.低价.toString());

		if (minPrice != null
				&& minPrice.intValue() <= realSalesPrice.intValue()) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.低价.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}

		itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.利差.toString());
		if (getProfitDifference(salePrice, realSalesPrice) > getRealProfit(
				realSalesPrice, sumCOmmissionMoney, salePrice, subsidy)) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.利差.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}

		itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.低格错误.toString());
		if (minPrice != null && Double.MAX_VALUE == minPrice) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.低格错误.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}

		itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.实售价不符.toString());
		if (shopSalePrice != null
				&& shopSalePrice.intValue() != realSalesPrice.intValue()) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.实售价不符.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}

		itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.详情错误.toString());
		if (StringUtils.isBlank(detail)) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.详情错误.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}

		itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.重复编号.toString());
		if (sNumIid != null && dao.getBySNumIid(sNumIid).size() > 1) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.重复编号.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}
	}

	@Override
	public void editDetail(Long id, String detail) {
		ItemErrors itemErrors = itemErrorsMng.getByItemAndType(id,
				ItemErrors.ItemErrorsType.详情错误.toString());
		if (StringUtils.isBlank(detail)) {
			if (itemErrors == null) {
				itemErrorsMng.add(id, ItemErrorsType.详情错误.toString());
			}
		} else if (itemErrors != null) {
			itemErrorsMng.delete(itemErrors.getId());
		}
		dao.editDetail(id, detail);
	}

	/**
	 * 个人所得税= 天猫商品补贴金额 * 0.2
	 * 
	 * @param salePrice
	 * @param subsidyRate
	 * @return
	 */
	protected Double getIncomeTax(Double subsidy) {
		return subsidy * 0.2;
	}

	/**
	 * 获取实际利润
	 * 
	 * @param realSalesPrice
	 * @param sumCOmmissionMoney
	 * @return
	 */
	public Double getRealProfit(Double realSalesPrice,
			Double sumCOmmissionMoney, Double salePrice,Double subsidy) {
		return realSalesPrice + sumCOmmissionMoney - salePrice-getServiceFee(sumCOmmissionMoney)-getIncomeTax(subsidy);
	}

	/**
	 * 技术服务费= 商品总佣金金额 * 0.1
	 * 
	 * @param sumCOmmissionMoney
	 * @return
	 */
	protected Double getServiceFee(Double sumCOmmissionMoney) {

		return sumCOmmissionMoney * 0.1;
	}

	/**
	 * 
	 * 实际差值=推广视频销售价格 - 实际销售价格
	 * 
	 * @return
	 */
	protected Double getProfitDifference(Double salePrice, Double realSalesPrice) {
		return salePrice - realSalesPrice;
	}

	@Override
	public Pagination getPageOfMap(Long[] ids, Long shopId, String title,
			String status, Integer pageNo, Integer pageSize,Boolean ifItemExists) {
		return dao.getPageOfMap(ids, shopId, title, status, pageNo, pageSize,ifItemExists);
	}

	@Override
	public void delete(Long id) {
		PromotionItem promotionItem = promotionItemMng.get(id);
		dao.delete(id);
		promotionItemMng.delete(id);
		List<ItemFilters> filters = itemFiltersMng.getByNumIid(promotionItem
				.getNumIid());
		for (ItemFilters itemFilter : filters) {
			if (itemFilter.getpNumIid() != null)
				itemFiltersMng.delete(itemFilter.getId());
		}
	}

	@Override
	public List<Item> getByStatus(String status) {
		return dao.getByStatus(status);
	}

	@Override
	public Item getByPNumIid(Long pNumIid) {
		return dao.getByPNumIid(pNumIid);
	}

	@Override
	public void edit(Long id, String status) {
		dao.edit(id, status);
		if (StringUtils.equals(status, ItemStatus.下架.toString())) {
			List<ItemErrors> itemErrors = itemErrorsMng.getByItem(id);
			for (ItemErrors ie : itemErrors) {
				itemErrorsMng.delete(ie.getId());
			}
		}
	}

	@Override
	public List<Item> query(Long[] ids, String title, Long shopId, String status) {
		return dao.query(ids, title, shopId, status);
	}

	@Override
	public Item get(Long id) {
		return dao.get(id);
	}
	
	@Override
	public List<Item> getBySNumIid(Long sNumIid) {
		return dao.getBySNumIid(sNumIid);
	}
	
	@Override
	public void editPropertyAlias(Long id, String propertyAlias) {
		dao.editPropertyAlias(id, propertyAlias);
	}

	@Autowired
	private ItemFiltersMng itemFiltersMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemDao dao;
	@Autowired
	private PromotionItemMng promotionItemMng;
}
