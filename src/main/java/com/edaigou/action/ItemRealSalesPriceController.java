package com.edaigou.action;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.springframework.stereotype.Controller;

import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;

@Controller
public class ItemRealSalesPriceController {

	private ItemGroupForm itemGroupForm;

	public void addActionListenter() {
		itemGroupForm.getRealSalesPrice().addModifyListener(
				new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent arg0) {
						try {

							if (StringUtils.isBlank(itemGroupForm
									.getSubsidyRate().getText())) {
								return;
							}
							if (StringUtils.isBlank(itemGroupForm
									.getSalePrice().getText())) {
								return;
							}
							if (StringUtils.isBlank(itemGroupForm
									.getCommissionMoney().getText())) {
								return;
							}

							if (StringUtils.isBlank(itemGroupForm
									.getRealSalesPrice().getText())) {
								return;
							}

							Double sRate = Double.valueOf(itemGroupForm
									.getSubsidyRate().getText());
							Double sPrice = Double.valueOf(itemGroupForm
									.getSalePrice().getText());
							Double sdy = sPrice * (sRate / 100);
							Double c = Double.valueOf(itemGroupForm
									.getCommissionMoney().getText());

							Double rsPrice = Double.valueOf(itemGroupForm
									.getRealSalesPrice().getText());
							Double rProfit = (rsPrice + (c + sdy) - (c + sdy)
									* 0.1 - (sdy * 0.2))
									- sPrice;
							itemGroupForm.getRealProfit().setText(
									new DecimalFormat("#").format(rProfit));
						} catch (Exception e) {
							MessageText.error(e.getMessage());
						}
					}
				});
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

}
