package com.edaigou.dao;

import java.util.List;

import com.common.jdbc.page.Pagination;
import com.edaigou.entity.Item;

public interface ItemDao {

	public long add(Item item);
	
	void edit(Long id,String title, String imageByte, Long pNumIid, Long sNumIid,boolean ifsNumIidNull,
			Double realSalesPrice, Double realSaleDiscount, Double realProfit,
			Double profitDifference,Double minPrice, Long shopId,Double shopSalePrice);
	
	void editDetail(Long id,String detail);

	Pagination getPageOfMap(Long [] ids,Long shopId, String title,String status, Integer pageNo,Integer pageSize,Boolean ifItemExists);

	void delete(Long id);

	List<Item> getByStatus(String status);
	
	Item getByPNumIid(Long pNumIid);
	
	 List<Item> getBySNumIid(Long sNumIid) ;
	
	void edit(Long id, String status) ;
	
	List<Item> query(Long[] ids, String title, Long shopId,
			String status) ;
	
	Item get(Long id);
}
