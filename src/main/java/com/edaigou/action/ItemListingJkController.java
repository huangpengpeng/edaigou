package com.edaigou.action;

import java.util.List;
import java.util.Map;

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

import com.common.util.NumberUtils;
import com.edaigou.entity.Item;
import com.edaigou.entity.Item.ItemPType;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.entity.PromotionItem;
import com.edaigou.entity.Shop;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;
import com.edaigou.manager.ShopMng;
import com.edaigou.service.TaobaoItemSvc;

@Controller
public class ItemListingJkController {

	private Button buttonOfListingJk;
	private PageForm pageForm;

	public void addActionListener() {
		buttonOfListingJk.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				final String nick = pageForm.getComboOfsearchShop().getText();
				if (StringUtils.isBlank(nick)) {
					MessageText.error("请选择店铺");
					return;
				}
				final String title = pageForm.getSearchText().getText();
				final String itemErrorText = pageForm.getComboOfItemErrors()
						.getText();
				final Shop shop = shopMng.getByNick(nick);
				new Thread(new Runnable() {

					protected void jktitle(Item item,
							Map<String, Object> modemMap,
							PromotionItem promotionItem) {
						if (itemErrorsMng.getByItemAndType(item.getId(),
								ItemErrorsType.标题错误.toString()) != null) {
							return;
						}
						if (StringUtils.equals(item.getpType(),
								ItemPType.标题不一致.toString())) {
							return;
						}
						if (!StringUtils.equals(item.getTitle(),
								(String) modemMap.get("title"))) {
							itemErrorsMng.add(item.getId(),
									ItemErrorsType.标题错误.toString());
						}
					}

					public void jktk(Item item, Map<String, Object> modemMap,
							PromotionItem promotionItem) {
						if (itemErrorsMng.getByItemAndType(item.getId(),
								ItemErrorsType.淘宝客变动.toString()) != null) {
							return;
						}
						if (itemErrorsMng.getByItemAndType(item.getId(),
								ItemErrorsType.天猫下架.toString()) != null) {
							return;
						}
						if (StringUtils.equals(item.getpType(),
								ItemPType.高级淘宝客.toString())) {
							return;
						}


							Double cRate = (Double) modemMap
									.get("commissionRate");
							if (cRate == null) {
								itemErrorsMng.add(item.getId(),
										ItemErrorsType.淘宝客变动.toString());
								return;
							}
							if (!NumberUtils.equals(cRate,
									promotionItem.getCommissionRate())) {
								itemErrorsMng.add(item.getId(),
										ItemErrorsType.淘宝客变动.toString());
								return;
							}
					
					}

					@Override
					public void run() {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfListingJk.setEnabled(false);
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
						final List<Item> items = manager.query(ids, title,
								shop.getId(), ItemStatus.上架.toString());
						for (int i = 0; i < items.size(); i++) {
							try {

								final int j = i;
								Display.getDefault().syncExec(new Runnable() {
									@Override
									public void run() {
										buttonOfListingJk.setText(j + "/"
												+ items.size());
									}
								});

								Item item = items.get(i);

								PromotionItem promotionItem = promotionItemMng
										.get(item.getId());
								if (items.size() > 1) {
									Thread.sleep(1000);
								}
								Map<String, Object> modemMap = null;
								try {
									modemMap = taobaoItemSvc
											.getAitaobaoItem(promotionItem
													.getUrl());
								} catch (IllegalStateException e) {
									if ("此商品没有淘客".equals(e.getMessage())) {
										itemErrorsMng.add(item.getId(),
												ItemErrorsType.天猫下架.toString());
									}
									continue;
								}

								jktk(item, modemMap, promotionItem);

								jktitle(item, modemMap, promotionItem);

							} catch (final Exception e) {
								if (items.size() == 1) {
									Display.getDefault().syncExec(
											new Runnable() {
												@Override
												public void run() {
													MessageText.error(e
															.getMessage());
												}
											});
								}
								log.error(e.getMessage(), e);
							}
						}
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								buttonOfListingJk.setText("监控");
								buttonOfListingJk.setEnabled(true);
							}
						});
					}
				}).start();
			}
		});
	}

	public void setButtonOfListingJk(Button buttonOfListingJk) {
		this.buttonOfListingJk = buttonOfListingJk;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	private Logger log = LoggerFactory.getLogger(ItemListingJkController.class);

	@Autowired
	private ItemMng manager;
	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private TaobaoItemSvc taobaoItemSvc;
	@Autowired
	private ShopMng shopMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
}
