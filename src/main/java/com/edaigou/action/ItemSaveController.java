package com.edaigou.action;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;
import com.edaigou.resource.ImageUtils;

@Controller
public class ItemSaveController {

	private ItemGroupForm itemGroupForm;

	public void addActionListenter() {
		itemGroupForm.add(ItemGroupForm.ADD_ITEM, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					itemGroupForm.getButtonOfSaveItem().setEnabled(false);
					String titleValue = itemGroupForm.getTitle().getText();
					String originalPriceValue = itemGroupForm
							.getOriginalPrice().getText();
					String salePriceValue = itemGroupForm.getSalePrice()
							.getText();
					String commissionRateValue = itemGroupForm
							.getCommissionRate().getText();
					String commissionValue = itemGroupForm.getCommissionMoney()
							.getText();
					String subsidyRateValue = itemGroupForm.getSubsidyRate()
							.getText();
					String realSalesPriceValue = itemGroupForm
							.getRealSalesPrice().getText();
					String realSaleDiscountValue = itemGroupForm
							.getRealSaleDiscount().getText();
					String realProfitValue = itemGroupForm.getRealProfit()
							.getText();
					String sumCommissionRateValue = itemGroupForm
							.getSumCommissionRate().getText();
					String sumCOmmissionMoneyValue = itemGroupForm
							.getSumCOmmissionMoney().getText();
					String subsidyValue = itemGroupForm.getSubsidy().getText();
					String imageUrlValue = itemGroupForm.getImage()
							.getToolTipText();
					String imageByteValue = ImageUtils
							.imgToBase64String(itemGroupForm.getImage()
									.getImage());
					Long shopValue = (Long) itemGroupForm.getComboOfShop()
							.getData(
									itemGroupForm.getComboOfShop()
											.getSelectionIndex() + "");
					String pType = itemGroupForm.getComboOfPType().getText();
					if (shopValue == null) {
						throw new IllegalStateException("请选择店铺");
					}
					if (StringUtils.isBlank(pType)) {
						throw new IllegalStateException("亲选择推广商品分类");
					}
					String pNumIidValue = itemGroupForm.getUrl()
							.getToolTipText();
					if (promotionItemMng.getByNumIid(Long.valueOf(pNumIidValue)) != null) {
						MessageText.error("商品已经存在");
						return;
					}
					manager.add(titleValue, imageByteValue, imageUrlValue,
							Long.valueOf(pNumIidValue),
							Double.valueOf(realSalesPriceValue),
							Double.valueOf(realSaleDiscountValue),
							Double.valueOf(originalPriceValue),
							Double.valueOf(salePriceValue),
							Double.valueOf(commissionValue),
							Double.valueOf(commissionRateValue),
							Double.valueOf(subsidyRateValue),
							Double.valueOf(subsidyValue),
							Double.valueOf(sumCommissionRateValue),
							Double.valueOf(sumCOmmissionMoneyValue), shopValue,
							itemGroupForm.getUrl().getText(), pType);

					itemController.init(1);

					itemGroupForm.clear(false);
				} catch (Exception e) {
					MessageText.error(e.getMessage());
					log.error(e.getMessage(), e);
				} finally {
					itemGroupForm.getButtonOfSaveItem().setEnabled(true);
				}
			}
		});
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	@Autowired
	private ItemController itemController;
	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private ItemMng manager;

	private Logger log = LoggerFactory.getLogger(ItemSaveController.class);
}
