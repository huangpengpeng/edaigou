package com.edaigou.action;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.entity.Appliance;
import com.edaigou.entity.Item;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.entity.Shop;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.manager.ApplianceMng;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.ShopMng;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

@Controller
public class ItemDetailController {

	private Button buttonOfInsertDetail;
	private PageForm pageForm;

	public void addListenter() {
		buttonOfInsertDetail.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				final String nick = pageForm.getComboOfsearchShop().getText();
				if (StringUtils.isBlank(nick)) {
					MessageText.error("请选择店铺");
					return;
				}
				final String title = pageForm.getSearchText().getText();
				final String itemErrorText=pageForm.getComboOfItemErrors().getText();
				final Shop shop = shopMng.getByNick(nick);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfInsertDetail.setEnabled(false);
							}
						});
						Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
						List<ItemErrors> errors = itemErrorsMng
								.getByErrorType(itemErrorText);
						for (ItemErrors itemErrors : errors) {
							ids = (Long[]) ArrayUtils.add(ids,
									itemErrors.getItemId());
						}
						if (!StringUtils.isBlank(itemErrorText)) {
							ids = (Long[]) ArrayUtils.add(ids, 0L);
						}
						List<Item> items = manager.query(ids, title,
								shop.getId(), ItemStatus.创建.toString());
						try {
							for (int i = 0; i < items.size(); i++) {
								Item item = items.get(i);
								if (StringUtils.isNotBlank(item.getDetail())) {
									continue;
								}
								if (items.size() > 1) {
									Thread.sleep(1000);
								}
								com.taobao.api.domain.Item tItem = new TaobaoItemMngImpl()
										.get(item.getsNumIid());
								if (tItem.getDesc().contains(
										shop.getIncludeText())) {
									ItemErrors itemErrors = itemErrorsMng
											.getByItemAndType(item.getId(),
													ItemErrorsType.详情错误
															.toString());
									if (itemErrors != null) {
										itemErrorsMng.delete(itemErrors.getId());
									}
								} else {
									StringBuffer buffer = new StringBuffer(shop
											.getDetailText());
									if (tItem.getDesc() != null) {
										buffer.append(tItem.getDesc());
									}

									Appliance appliance = applianceMng
											.getByNickOfOne(nick, i);
									if (items.size() > 1) {
										Thread.sleep(3000);
									}
									new TaobaoItemMngImpl().updateDesc(
											appliance.getAppKey(),
											appliance.getAppSecret(),
											item.getsNumIid(), null,
											buffer.toString(),
											appliance.getSessionKey());
									manager.editDetail(item.getId(),
											buffer.toString());
								}
							}
						} catch (final Exception e) {
							Display.getDefault().syncExec(new Runnable() {
								@Override
								public void run() {
									MessageText.error(e.getMessage());
								}
							});
							log.error(e.getMessage(), e);
						}
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfInsertDetail.setEnabled(true);
							}
						});
					}
				}).start();
			}
		});
	}

	public void setButtonOfInsertDetail(Button buttonOfInsertDetail) {
		this.buttonOfInsertDetail = buttonOfInsertDetail;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}




	private Logger log = LoggerFactory.getLogger(ItemDetailController.class);

	@Autowired
	private ApplianceMng applianceMng;
	@Autowired
	private ShopMng shopMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemMng manager;
}
