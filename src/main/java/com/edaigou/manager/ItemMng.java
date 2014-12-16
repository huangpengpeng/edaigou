package com.edaigou.manager;

import java.util.List;

import com.common.jdbc.page.Pagination;
import com.edaigou.entity.Item;

public interface ItemMng {

	/**
	 * 增加商品
	 * 
	 * @param title
	 *            标题
	 * @param imageByte
	 *            二进制图片
	 * @param imageUrl
	 *            网络图片地址
	 * @param pNumIid
	 *            推广商品编号
	 * @param realProfit
	 *            实际利润
	 * @param realSalesPrice
	 *            实际销售价格
	 * @param realSaleDiscount
	 *            实际销售折扣
	 * @param originalPrice
	 *            原始销售价格
	 * @param salePrice
	 *            销售价格
	 * @param commissionMoney
	 *            推广提成金额
	 * @param commissionRate
	 *            推广提成比例
	 * @param subsidyRate
	 *            天猫补贴佣金比例
	 * @param subsidy
	 *            天猫补贴佣金金额
	 */
	void add(String title, String imageByte, String imageUrl, Long pNumIid,
			Double realSalesPrice, Double realSaleDiscount, 
			Double originalPrice, Double salePrice, Double commissionMoney,
			Double commissionRate, Double subsidyRate, Double subsidy,
			Double sumCommissionRate, Double sumCOmmissionMoney, Long shopId,
			String url,String pType);

	void edit(Long id, Long pNumIid, String title, String imageByte,
			String imageUrl, Double realSalesPrice, Double realSaleDiscount, Double originalPrice, Double salePrice,
			Double commissionMoney, Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Long shopId, String url, Long sNumIid,
			Double minPrice, Double shopSalePrice, String detail,String pType);
	
	void editDetail(Long id, String detail);

	Pagination getPageOfMap(Long[] ids, Long shopId, String title,
			String status, Integer pageNo, Integer pageSize,Boolean ifItemExists);
	
	List<Item> query(Long [] ids,String title,Long shopId,String status);

	Item getByPNumIid(Long pNumIid);
	
	List<Item> getBySNumIid(Long sNumIid);

	void edit(Long id, String status);

	void delete(Long id);

	List<Item> getByStatus(String status);
	
	Item get(Long id);
}
