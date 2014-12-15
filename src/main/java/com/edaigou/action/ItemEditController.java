package com.edaigou.action;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.entity.Item;
import com.edaigou.entity.Shop;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.ShopMng;
import com.edaigou.resource.ImageUtils;

@Controller
public class ItemEditController {

	private ItemGroupForm itemGroupForm;
	private Text browserTextOfMinPrice;
	public EditListenter editListenter;

	public void addActionListenter() {
		if(editListenter==null){
			editListenter=new EditListenter();
		}
		itemGroupForm.add(ItemGroupForm.EDIT_ITEM, new EditListenter());
	}
	
	
	public class EditListenter implements Listener{

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
				String shopSalePriceValue = itemGroupForm
						.getRealSalesPrice().getToolTipText();
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
				String shopName = itemGroupForm.getComboOfShop().getText();
				String pNumIidValue = itemGroupForm.getUrl()
						.getToolTipText();
				String sNumIid = itemGroupForm.getsNumIid().getText();
				Double minPrice = null;
				if (!StringUtils.isBlank(browserTextOfMinPrice.getText())) {
					minPrice = Double.valueOf(browserTextOfMinPrice
							.getText());
				}
				if (shopName == null) {
					throw new IllegalStateException("请选择店铺");
				}
				Shop shop = shopMng.getByNick(shopName);
				Item item = manager.getByPNumIid(Long.valueOf(pNumIidValue));
				manager.edit(
						item.getId(),
						item.getpNumIid(),
						titleValue,
						imageByteValue,
						imageUrlValue,
						Double.valueOf(realSalesPriceValue),
						Double.valueOf(realSaleDiscountValue),
						Double.valueOf(originalPriceValue),
						Double.valueOf(salePriceValue),
						Double.valueOf(commissionValue),
						Double.valueOf(commissionRateValue),
						Double.valueOf(subsidyRateValue),
						Double.valueOf(subsidyValue),
						Double.valueOf(sumCommissionRateValue),
						Double.valueOf(sumCOmmissionMoneyValue),
						shop.getId(),
						itemGroupForm.getUrl().getText(),
						StringUtils.isBlank(sNumIid) ? null : Long
								.valueOf(sNumIid),
						minPrice,
						shopSalePriceValue == null ? null : Double
								.valueOf(shopSalePriceValue), null);
			} catch (Exception e) {
				MessageText.error(e.getMessage());
				log.error(e.getMessage(), e);
			} finally {
				itemGroupForm.getButtonOfSaveItem().setEnabled(true);
			}
		}
		
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	public void setBrowserTextOfMinPrice(Text browserTextOfMinPrice) {
		this.browserTextOfMinPrice = browserTextOfMinPrice;
	}

	@Autowired
	private ShopMng shopMng;
	@Autowired
	private ItemMng manager;

	private Logger log = LoggerFactory.getLogger(ItemEditController.class);
}
