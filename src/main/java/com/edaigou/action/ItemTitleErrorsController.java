package com.edaigou.action;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.jdbc.page.Pagination;
import com.edaigou.entity.Appliance;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.entity.PromotionItem;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.TableUtils;
import com.edaigou.manager.ApplianceMng;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;
import com.edaigou.manager.ShopMng;
import com.edaigou.resource.ImageUtils;
import com.edaigou.service.TaobaoItemSvc;
import com.taobao.api.domain.Item;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

@Controller
public class ItemTitleErrorsController {

	private Table tableOfTitleErrors;
	private ItemGroupForm itemGroupForm;

	public Pagination page;

	public void init(Integer pageNo) {
		Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		List<ItemErrors> errors = itemErrorsMng
				.getByErrorType(ItemErrorsType.标题错误.toString());
		for (ItemErrors itemErrors : errors) {
			ids = (Long[]) ArrayUtils.add(ids, itemErrors.getItemId());
		}
		if(ArrayUtils.isEmpty(ids)){
			ids = (Long[]) ArrayUtils.add(ids,0L);
		}
		page = manager.getPageOfMap(ids, null, null, ItemStatus.上架.toString(),
				pageNo, 100, false);

		table(page.getList(Map.class));
	}

	public void table(List<Map> list) {

		TableUtils.removeAll(tableOfTitleErrors);

		TableUtils.addColumn(tableOfTitleErrors, "商品图片", 90);
		TableUtils.addColumn(tableOfTitleErrors, "掌柜名称", 90);
		TableUtils.addColumn(tableOfTitleErrors, "商品标题", 190);
		TableUtils.addColumn(tableOfTitleErrors, "原始标价", 60);
		TableUtils.addColumn(tableOfTitleErrors, "原售价格", 60);
		TableUtils.addColumn(tableOfTitleErrors, "实售价格", 60);
		TableUtils.addColumn(tableOfTitleErrors, "提成总额", 60);
		TableUtils.addColumn(tableOfTitleErrors, "实际折扣", 60);
		TableUtils.addColumn(tableOfTitleErrors, "实际利润", 60);
		TableUtils.addColumn(tableOfTitleErrors, "状态", 60);
		TableUtils.addColumn(tableOfTitleErrors, "低格", 60);
		TableUtils.addColumn(tableOfTitleErrors, "差额", 60);
		TableUtils.addColumn(tableOfTitleErrors, "编号", 70);
		TableUtils.addColumn(tableOfTitleErrors, "操作", 90);

		for (Map<String, Object> map : list) {
			try {

				TableItem tableItem = new TableItem(tableOfTitleErrors,
						SWT.NONE);
				TableUtils.addItemOfImage(tableItem, ImageUtils
						.base64StringToImg((String) map.get("imageByte")), 80,
						0);
				TableUtils.addItem(tableItem, (String) map.get("nick"), 1);
				TableUtils.addItem(tableItem, (String) map.get("title"), 2);
				TableUtils.addItem(tableItem, map.get("originalPrice")
						.toString(), 3);
				TableUtils.addItem(tableItem, map.get("salePrice").toString(),
						4);
				TableUtils.addItem(tableItem, map.get("realSalesPrice")
						.toString(), 5);
				TableUtils.addItem(tableItem, map.get("sumCOmmissionMoney")
						.toString(), 6);
				TableUtils.addItem(tableItem, map.get("realSaleDiscount")
						.toString(), 7);
				TableUtils.addItem(tableItem, map.get("realProfit").toString(),
						8);
				TableUtils.addItem(tableItem, map.get("status").toString(), 9);
				TableUtils.addItem(tableItem, map.get("minPrice") == null ? ""
						: map.get("minPrice").toString(), 10);
				TableUtils.addItem(tableItem, new DecimalFormat("#.##")
						.format(map.get("profitDifference")), 11);

				Double minPrice = (Double) map.get("minPrice");
				if (minPrice != null) {
					if (minPrice == Double.MAX_VALUE
							|| "Infinity".equals(minPrice.toString())) {
						tableItem.setBackground(10,
								SWTResourceManager.getColor(SWT.COLOR_RED));
					} else if (minPrice < Double.valueOf(map.get(
							"realSalesPrice").toString())) {
						tableItem.setBackground(10,
								SWTResourceManager.getColor(SWT.COLOR_RED));
					}
				} else {
					tableItem.setBackground(10,
							SWTResourceManager.getColor(SWT.COLOR_RED));
				}

				String sNumIid = map.get("sNumIid") == null ? null : map.get(
						"sNumIid").toString();
				if (StringUtils.isBlank(sNumIid)) {
					tableItem.setBackground(12,
							SWTResourceManager.getColor(SWT.COLOR_RED));
				} else {
					TableUtils.addItem(tableItem, sNumIid, 12);
				}

				tableItem.setData(map);

				Button delistingButton = new Button(tableOfTitleErrors,
						SWT.NONE);
				delistingButton.setData(map);
				delistingButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						try {
							Map map = (Map) arg0.widget.getData();

							Appliance appliance = applianceMng
									.getByNickOfOne(map.get("nick").toString());

							Long id = (Long) map.get("id");
							Long pNumIid = (Long) map.get("pNumIid");
							Long sNUmIid = (Long) map.get("sNumIid");

							try {
								Item item = new TaobaoItemMngImpl()
										.get(pNumIid);

								PromotionItem pItem = promotionItemMng.get(id);
								com.edaigou.entity.Item eItem = manager.get(id);

								new TaobaoItemMngImpl().updateDesc(
										appliance.getAppKey(),
										appliance.getAppSecret(), sNUmIid,
										item.getTitle(), null,
										appliance.getSessionKey());

								manager.edit(id, eItem.getpNumIid(),
										item.getTitle(), eItem.getImageByte(),
										pItem.getImageUrl(),
										eItem.getRealSalesPrice(),
										eItem.getRealSaleDiscount(),
										pItem.getOriginalPrice(),
										pItem.getSalePrice(),
										pItem.getCommissionMoney(),
										pItem.getCommissionRate(),
										pItem.getSubsidyRate(),
										pItem.getSubsidy(),
										pItem.getSumCommissionRate(),
										pItem.getSumCOmmissionMoney(),
										eItem.getShopId(), pItem.getUrl(),
										eItem.getsNumIid(),
										eItem.getMinPrice().isInfinite()?0D:eItem.getMinPrice(),
										eItem.getShopSalePrice(),
										eItem.getDetail(), eItem.getpType());
							} catch (Exception e) {
								MessageText.error(e.getMessage());
								return;
							}

							ItemErrors itemErrors = itemErrorsMng
									.getByItemAndType((Long) map.get("id"),
											ItemErrorsType.标题错误.toString());
							itemErrorsMng.delete(itemErrors.getId());
							init(page.getPageNo());
						} catch (Exception e) {
							MessageText.error(e.getMessage());
							log.error(e.getMessage(), e);
						}
					}
				});
				delistingButton.setText("修复");

				TableUtils.addButton(tableItem, 13, delistingButton, SWT.None,
						90);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void addActionListener() {

		tableOfTitleErrors.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Map map = (Map) arg0.item.getData();

				try {
					itemGroupForm.write(map);
				} catch (Exception e) {
					MessageText.error(e.getMessage());
					log.error(e.getMessage(), e);
				}
				itemGroupForm.getGroupOfItem().setText(ItemGroupForm.EDIT_ITEM);
				itemGroupForm.getButtonOfSaveItem().setText(
						ItemGroupForm.SAVE_EDIT_ITEM);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
	}

	@Autowired
	private ApplianceMng applianceMng;
	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private TaobaoItemSvc taobaoItemSvc;
	@Autowired
	private ShopMng shopMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemMng manager;

	private Logger log = LoggerFactory.getLogger(ItemListingController.class);

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	public void setTableOfTitleErrors(Table tableOfTitleErrors) {
		this.tableOfTitleErrors = tableOfTitleErrors;
	}

}
