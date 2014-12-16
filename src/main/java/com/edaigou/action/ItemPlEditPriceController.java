package com.edaigou.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.entity.Item;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.entity.PromotionItem;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;

@Controller
public class ItemPlEditPriceController {

	private Button buttonOfPlEditPrice;
	private PageForm pageForm;
	
	
	public void addActionListenter(){
		buttonOfPlEditPrice.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				MessageBox messageBox = new MessageBox(buttonOfPlEditPrice
						.getShell(), SWT.OK | SWT.CANCEL);
				messageBox.setMessage("确认批量修改最低价格吗?");
				if (messageBox.open() != SWT.OK) {
					return;
				}
				String status = ItemStatus.创建.toString();
				if (pageForm.getTabFolder().getSelectionIndex() == 3) {
					status = ItemStatus.上架.toString();
				}
				try {
					buttonOfPlEditPrice.setEnabled(false);
					List<ItemErrors> errors = itemErrorsMng
							.getByErrorType(ItemErrorsType.低价.toString());
					for (ItemErrors itemErrors : errors) {
						Item item = manager.get(itemErrors.getItemId());
						if (item == null) {
							itemErrorsMng.delete(itemErrors.getId());
							continue;
						}
						if (StringUtils.equals(item.getStatus(), status)) {
							PromotionItem promotionItem = promotionItemMng
									.get(item.getId());
							manager.edit(item.getId(), item.getpNumIid(),
									item.getTitle(), item.getImageByte(),
									promotionItem.getImageUrl(),
									item.getMinPrice() - 1,
									item.getRealSaleDiscount(),
									promotionItem.getOriginalPrice(),
									promotionItem.getSalePrice(),
									promotionItem.getCommissionMoney(),
									promotionItem.getCommissionRate(),
									promotionItem.getSubsidyRate(),
									promotionItem.getSubsidy(),
									promotionItem.getSumCommissionRate(),
									promotionItem.getSumCOmmissionMoney(),
									item.getShopId(), promotionItem.getUrl(),
									item.getsNumIid(), item.getMinPrice(),
									item.getShopSalePrice(), item.getDetail(),item.getpType());
						}
					}
				} catch (Exception e) {
					MessageText.error(e.getMessage());
					log.error(e.getMessage(), e);
				} finally {
					buttonOfPlEditPrice.setEnabled(true);
				}
			}
		});
	}
	

	public void setButtonOfPlEditPrice(Button buttonOfPlEditPrice) {
		this.buttonOfPlEditPrice = buttonOfPlEditPrice;
	}
	
	
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}


	private Logger log=LoggerFactory.getLogger(ItemPlEditPriceController.class);

	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemMng manager;
}
