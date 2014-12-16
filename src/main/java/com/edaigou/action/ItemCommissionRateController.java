package com.edaigou.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.springframework.stereotype.Controller;

import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.taobao.top.link.endpoint.MessageIO.MessageEncoder;

@Controller
public class ItemCommissionRateController {

	private ItemGroupForm itemGroupForm;

	public void addActionListenter() {
		itemGroupForm.getCommissionRate().addModifyListener(
				new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent arg0) {
						String rPriceValue = itemGroupForm.getSalePrice()
								.getText();
						String commissionRateValue = itemGroupForm
								.getCommissionRate().getText();
						String subsidyValue = itemGroupForm.getSubsidy()
								.getText();
						String subsidyRateValue = itemGroupForm
								.getSubsidyRate().getText();
						try {
							Double commissionRate = Double
									.valueOf(commissionRateValue);
							Double originalPrice = Double.valueOf(rPriceValue);

							String commissionMoney = new DecimalFormat("0.00")
									.format(commissionRate / 100
											* originalPrice);
							itemGroupForm.getCommissionMoney().setText(
									commissionMoney);

							itemGroupForm
									.getSumCOmmissionMoney()
									.setText(
											""
													+ (Double
															.valueOf(commissionMoney) + Double
															.valueOf(subsidyValue)));

							itemGroupForm
									.getSumCommissionRate()
									.setText(
											""
													+ (Double
															.valueOf(subsidyRateValue) + Double
															.valueOf(commissionRateValue)));

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
						}
					}
				});
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

}
