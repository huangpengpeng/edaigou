package com.edaigou.action;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.service.TaobaoItemSvc;

@Controller
public class ItemGetController {

	private ItemGroupForm itemGroupForm;

	public void addActionListenter() {
		itemGroupForm.getButtonOfGetItem().addListener(SWT.Selection,
				new Listener() {

					@Override
					public void handleEvent(Event arg0) {
						try {
							Map<String, Object> modemMap = taobaoItemSvc
									.getAitaobaoItem(itemGroupForm.getUrl()
											.getText());
							itemGroupForm.getUrl().setToolTipText(
									modemMap.get("numIid").toString());
							Double sPrice = (Double) modemMap.get("salePrice");
							Double cRate = (Double) modemMap
									.get("commissionRate");
							itemGroupForm.getTitle().setText(
									(String) modemMap.get("title"));
							itemGroupForm.getOriginalPrice().setText(
									(String) modemMap.get("originalPrice"));
							itemGroupForm.getSalePrice().setText(
									sPrice.toString());

							Double c = (Double) modemMap.get("commission");
							itemGroupForm.getCommissionMoney().setText(
									new DecimalFormat("0.00").format(c));
							itemGroupForm.getCommissionRate().setText(
									cRate.toString());

							// 计算天猫补贴金额
							Double sRate = Double.valueOf(itemGroupForm
									.getSubsidyRate().getText());
							Double sdy = sPrice * (sRate / 100);

							itemGroupForm.getSubsidy().setText(
									new DecimalFormat("0.00").format(sdy));

							itemGroupForm.getSumCOmmissionMoney().setText(
									new DecimalFormat("0.00").format(c + sdy));
							itemGroupForm.getSumCommissionRate().setText(
									new DecimalFormat("#")
											.format(cRate + sRate));

							Double rsPrice = (Double) modemMap
									.get("realSalePrice");
							itemGroupForm.getRealSalesPrice().setText(
									rsPrice.intValue()+"");
							Double rsDiscount = (Double) modemMap
									.get("realSaleDiscount");
							itemGroupForm.getRealSaleDiscount().setText(
									rsDiscount.toString());

							Double rProfit = (rsPrice + (c + sdy) - (c + sdy)
									* 0.1 - (sdy * 0.2))
									- sPrice;
							itemGroupForm.getRealProfit().setText(
									new DecimalFormat("#").format(rProfit));
							itemGroupForm.getImage().setImage(
									ImageDescriptor.createFromURL(
											new URL(modemMap.get("image")
													+ "_80x80q90.jpg"))
											.createImage());
							itemGroupForm.getsNumIid().setText("");
							itemGroupForm.getImage().setToolTipText(
									(String) modemMap.get("image"));
						} catch (Exception e) {
							MessageText.error(e.getMessage());
							log.error(e.getMessage(), e);
						}
					}
				});
	}

	private Logger log = LoggerFactory.getLogger(ItemGetController.class);

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}
	
	
	@Autowired
	private TaobaoItemSvc taobaoItemSvc;
}
