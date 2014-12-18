package com.edaigou.action;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.springframework.stereotype.Controller;

import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;

/**
 * 天猫补贴比例修改控制器
 * 
 * @author zoro
 *
 */
@Controller
public class ItemSubsidyRateController {

	private ItemGroupForm itemGroupForm;

	public void addActionListenter() {
		itemGroupForm.getSubsidyRate().addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					if (StringUtils.isBlank(itemGroupForm.getSalePrice()
							.getText())) {
						return;
					}
					if (StringUtils.isBlank(itemGroupForm.getCommissionMoney()
							.getText())) {
						return;
					}
					if (StringUtils.isBlank(itemGroupForm
							.getSumCommissionRate().getText())) {
						return;
					}
					if (StringUtils.isBlank(itemGroupForm.getRealSalesPrice()
							.getText())) {
						return;
					}

					Double sRate = Double.valueOf(itemGroupForm
							.getSubsidyRate().getText());
					Double sPrice = Double.valueOf(itemGroupForm.getSalePrice()
							.getText());
					Double sdy = sPrice * (sRate / 100);
					itemGroupForm.getSubsidy().setText(
							new DecimalFormat("0.00").format(sdy));

					Double c = Double.valueOf(itemGroupForm
							.getCommissionMoney().getText());
					itemGroupForm.getSumCOmmissionMoney().setText(
							new DecimalFormat("0.00").format(c + sdy));

					Double cRate = Double.valueOf(itemGroupForm
							.getCommissionRate().getText());
					itemGroupForm.getSumCommissionRate().setText(
							new DecimalFormat("#").format(cRate + sRate));

					Double rsPrice = Double.valueOf(itemGroupForm
							.getRealSalesPrice().getText());
					
					Double rProfit = (rsPrice + (c + sdy) - (c + sdy) * 0.1 - (sdy * 0.2))
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
