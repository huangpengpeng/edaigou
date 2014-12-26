package com.edaigou.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.util.JxPathUtils;
import com.edaigou.entity.Appliance;
import com.edaigou.entity.Item;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.Shop;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.manager.ApplianceMng;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.ShopMng;
import com.taobao.api.domain.Sku;
import com.taobao.biz.manager.TaoBaoItemSkuMng;
import com.taobao.biz.manager.impl.TaoBaoItemSkuMngImpl;

@Controller
public class KutongItemController {

	private Button buttonOfkutongitem;
	private PageForm pageForm;
	private Button buttonOfAuto;

	public void addActionListenter() {
		buttonOfkutongitem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				final String nick = pageForm.getComboOfsearchShop().getText();
				if (StringUtils.isBlank(nick)) {
					MessageText.error("请选择店铺");
					return;
				}
				final String title = pageForm.getSearchText().getText();
				final Shop shop = shopMng.getByNick(nick);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfkutongitem.setEnabled(false);
								isStop=false;
							}
						});

						final List<Item> items = manager.query(null, title,
								shop.getId(), ItemStatus.上架.toString());
						for (int i = 0; i < items.size(); i++) {
							final int j = i;
							Display.getDefault().syncExec(new Runnable() {
								@Override
								public void run() {
									buttonOfkutongitem.setText(j + "/"
											+ items.size());
								}
							});
							Appliance appliance = applianceMng
									.getByNickOfOne(nick);
							Item item = items.get(i);
							try {
								List<Sku> orgList = taoBaoItemSkuMng
										.getByNumIid(appliance.getAppKey(),
												appliance.getAppSecret(),
												item.getpNumIid());
								if (items.size() > 1) {
									Thread.sleep(100);
								}
								List<Sku> selfList = taoBaoItemSkuMng
										.getByNumIid(appliance.getAppKey(),
												appliance.getAppSecret(),
												item.getsNumIid());
								if (orgList.size() != selfList.size()) {
									itemErrorsMng.add(item.getId(),
											ItemErrors.ItemErrorsType.SKU变动
													.toString());
									continue;
								}
								for (Sku sku : orgList) {
									Sku selfSku = JxPathUtils.get(
											selfList,
											".[ properties='"
													+ sku.getProperties()
													+ "' ]");
									if(selfSku==null){
										itemErrorsMng.add(item.getId(),
												ItemErrors.ItemErrorsType.SKU变动
														.toString());
										continue;
									}
									if (selfList == null) {
										continue;
									}
									if (items.size() > 1) {
										Thread.sleep(200);
									}

									taoBaoItemSkuMng.updateBySkuQuantity(
											appliance.getAppKey(),
											appliance.getAppSecret(),
											selfSku.getNumIid(),
											selfSku.getSkuId(),
											sku.getQuantity(),
											appliance.getSessionKey());
								}
							} catch (final Exception e) {
								Display.getDefault().syncExec(new Runnable() {
									@Override
									public void run() {
										if (!buttonOfAuto.getSelection()) {
											MessageBox messageBox = new MessageBox(
													buttonOfkutongitem
															.getShell(), SWT.OK
															| SWT.CANCEL);
											messageBox
													.setMessage("是否结束? errors:"
															+ e.getMessage());
											if (messageBox.open() == SWT.OK) {
												buttonOfkutongitem.setText("库存同步");
												buttonOfkutongitem.setEnabled(true);
												isStop=true;
											}
										}
									}
								});
								if (isStop) {
									return;
								}
								log.error(e.getMessage(), e);
							}
						}
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfkutongitem.setText("库存同步");
								buttonOfkutongitem.setEnabled(true);
							}
						});
					}
				}).start();
			}
		});
	}
	
	private boolean isStop=false;

	public void setButtonOfkutongitem(Button buttonOfkutongitem) {
		this.buttonOfkutongitem = buttonOfkutongitem;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	

	public void setButtonOfAuto(Button buttonOfAuto) {
		this.buttonOfAuto = buttonOfAuto;
	}



	private Logger log = LoggerFactory.getLogger(KutongItemController.class);

	private TaoBaoItemSkuMng taoBaoItemSkuMng = new TaoBaoItemSkuMngImpl();

	@Autowired
	private ApplianceMng applianceMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemMng manager;
	@Autowired
	private ShopMng shopMng;
}
