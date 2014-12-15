package com.edaigou.entity.base;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.common.jdbc.BaseEntity;

@MappedSuperclass
public class BasePromotionItem  extends BaseEntity {

	private static final long serialVersionUID = 5503162410764747607L;

	public BasePromotionItem(){}
	
	public BasePromotionItem(String title, String imageByte,String imageUrl, Long numIid,
			Double originalPrice, Double salePrice, Double commissionMoney,
 Double commissionRate, Double subsidyRate,
			Double subsidy, Double sumCommissionRate,
			Double sumCOmmissionMoney, Double incomeTax, Double serviceFee,String url) {
		this.setTitle(title);
		this.setImageByte(imageByte);
		this.setNumIid(numIid);
		this.setOriginalPrice(originalPrice);
		this.setSalePrice(salePrice);
		this.setCommissionMoney(commissionMoney);
		this.setCommissionRate(commissionRate);
		this.setSubsidyRate(subsidyRate);
		this.setSubsidy(subsidy);
		this.setSumCommissionRate(sumCommissionRate);
		this.setSumCOmmissionMoney(sumCOmmissionMoney);
		this.setImageUrl(imageUrl);
		this.setIncomeTax(incomeTax);
		this.setServiceFee(serviceFee);
		this.setUrl(url);
	}
	
	public Double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(Double incomeTax) {
		this.incomeTax = incomeTax;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 所得税
	 */
	@Column(scale = 2, precision = 20)
	private Double incomeTax;

	/**
	 * 技术服务费
	 */
	@Column(scale = 2, precision = 20)
	private Double serviceFee;
	
	/**
	 * 商品图片流
	 */
	@Lob
	private String imageByte;
	
	/**
	 * 图片地址
	 */
	private String imageUrl;
	
	/**
	 * 商品编号
	 */
	private Long numIid;
	
	/**
	 * 商品原始价格
	 */
	@Column(scale = 2, precision = 20)
	private Double originalPrice;
	
	/**
	 * 商品销售价格
	 */
	@Column(scale = 2, precision = 20)
	private Double salePrice;
	
	/**
	 * 商品佣金
	 */
	@Column(scale = 2, precision = 20)
	private Double commissionMoney;
	
	
	/**
	 * 商品佣金比例
	 */
	@Column(scale = 2, precision = 20)
	private Double commissionRate;
	
	/**
	 * 天猫补贴佣金比例
	 */
	@Column(scale = 2, precision = 20)
	private Double subsidyRate;
	
	/**
	 * 天猫补贴金额
	 */
	@Column(scale = 2, precision = 20)
	private Double subsidy;

	/**
	 * 合计提成比例
	 */
	@Column(scale = 2, precision = 20)
	private Double sumCommissionRate;
	
	/**
	 * 合计提成金额
	 */
	@Column(scale = 2, precision = 20)
	private Double sumCOmmissionMoney;
	
	/**
	 * 商品地址
	 */
	private String url;
	
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

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(Double commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Double getSubsidyRate() {
		return subsidyRate;
	}

	public void setSubsidyRate(Double subsidyRate) {
		this.subsidyRate = subsidyRate;
	}

	public Double getSumCommissionRate() {
		return sumCommissionRate;
	}

	public void setSumCommissionRate(Double sumCommissionRate) {
		this.sumCommissionRate = sumCommissionRate;
	}

	public Double getSumCOmmissionMoney() {
		return sumCOmmissionMoney;
	}

	public void setSumCOmmissionMoney(Double sumCOmmissionMoney) {
		this.sumCOmmissionMoney = sumCOmmissionMoney;
	}

	public Double getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Double subsidy) {
		this.subsidy = subsidy;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
