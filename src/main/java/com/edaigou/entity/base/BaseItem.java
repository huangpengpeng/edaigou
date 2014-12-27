package com.edaigou.entity.base;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.common.jdbc.BaseEntity;

/**
 * 销售商品表
 * 
 * @author zoro
 *
 */
@MappedSuperclass
public class BaseItem extends BaseEntity {

	private static final long serialVersionUID = -8120247851489980872L;

	public BaseItem() {
	}

	public BaseItem(String title, String imageByte, Long pNumIid, Long sNumIid,
			Double realSalesPrice, Double realSaleDiscount, Double realProfit, Double profitDifference,Long shopId,String pType) {
		this.setTitle(title);
		this.setImageByte(imageByte);
		this.setpNumIid(pNumIid);
		this.setsNumIid(sNumIid);
		this.setRealSalesPrice(realSalesPrice);
		this.setRealSaleDiscount(realSaleDiscount);
		this.setRealProfit(realProfit);
		this.setProfitDifference(profitDifference);
		this.setShopId(shopId);
		this.setpType(pType);
	}
	
	@Column(length=2000)
	private String propertyAlias;

	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 推广商品类型
	 */
	private String pType;

	/**
	 * 商品图片流
	 */
	@Lob
	private String imageByte;

	/**
	 * 推广商品编号
	 */
	private Long pNumIid;

	/**
	 * 销售商品编号
	 */
	private Long sNumIid;
	
	/**
	 * 最低小时价格
	 */
	private Double minPrice;

	/**
	 * 小时价格
	 */
	@Column(scale = 2, precision = 20)
	private Double realSalesPrice;

	/**
	 * 实际销售折扣
	 */
	@Column(scale = 2, precision = 20)
	private Double realSaleDiscount;

	/**
	 * 实际利润
	 */
	@Column(scale = 2, precision = 20)
	private Double realProfit;

	/**
	 * 利润差值
	 */
	@Column(scale = 2, precision = 20)
	private Double profitDifference;
	
	/**
	 * 店铺销售金额
	 */
	@Column(scale = 2, precision = 20)
	private Double shopSalePrice;
	
	private Long shopId;
	
	/**
	 * 商品状态
	 */
	private String status;
	
	@Lob
	private String detail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageByte() {
		return imageByte;
	}

	public void setImageByte(String imageByte) {
		this.imageByte = imageByte;
	}

	public Long getpNumIid() {
		return pNumIid;
	}

	public void setpNumIid(Long pNumIid) {
		this.pNumIid = pNumIid;
	}

	public Long getsNumIid() {
		return sNumIid;
	}

	public void setsNumIid(Long sNumIid) {
		this.sNumIid = sNumIid;
	}

	public Double getRealSalesPrice() {
		return realSalesPrice;
	}

	public void setRealSalesPrice(Double realSalesPrice) {
		this.realSalesPrice = realSalesPrice;
	}

	public Double getRealSaleDiscount() {
		return realSaleDiscount;
	}

	public void setRealSaleDiscount(Double realSaleDiscount) {
		this.realSaleDiscount = realSaleDiscount;
	}

	public Double getRealProfit() {
		return realProfit;
	}

	public void setRealProfit(Double realProfit) {
		this.realProfit = realProfit;
	}


	public Double getProfitDifference() {
		return profitDifference;
	}

	public void setProfitDifference(Double profitDifference) {
		this.profitDifference = profitDifference;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getShopSalePrice() {
		return shopSalePrice;
	}

	public void setShopSalePrice(Double shopSalePrice) {
		this.shopSalePrice = shopSalePrice;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getPropertyAlias() {
		return propertyAlias;
	}

	public void setPropertyAlias(String propertyAlias) {
		this.propertyAlias = propertyAlias;
	}
	
}
