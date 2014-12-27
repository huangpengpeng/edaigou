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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.jdbc.page.Pagination;
import com.common.util.JxPathUtils;
import com.edaigou.entity.Appliance;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
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
import com.taobao.api.domain.Sku;
import com.taobao.biz.manager.TaoBaoItemSkuMng;
import com.taobao.biz.manager.impl.TaoBaoItemSkuMngImpl;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

@Controller
public class ItemSkuErrorsController {

	private Table tableOfSkuErrors;
	private ItemGroupForm itemGroupForm;

	public Pagination page;

	public void init(Integer pageNo) {
		Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		List<ItemErrors> errors = itemErrorsMng
				.getByErrorType(ItemErrorsType.SKU变动.toString());
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

		TableUtils.removeAll(tableOfSkuErrors);

		TableUtils.addColumn(tableOfSkuErrors, "商品图片", 90);
		TableUtils.addColumn(tableOfSkuErrors, "掌柜名称", 90);
		TableUtils.addColumn(tableOfSkuErrors, "商品标题", 190);
		TableUtils.addColumn(tableOfSkuErrors, "原始标价", 60);
		TableUtils.addColumn(tableOfSkuErrors, "原售价格", 60);
		TableUtils.addColumn(tableOfSkuErrors, "实售价格", 60);
		TableUtils.addColumn(tableOfSkuErrors, "提成总额", 60);
		TableUtils.addColumn(tableOfSkuErrors, "实际折扣", 60);
		TableUtils.addColumn(tableOfSkuErrors, "实际利润", 60);
		TableUtils.addColumn(tableOfSkuErrors, "状态", 60);
		TableUtils.addColumn(tableOfSkuErrors, "低格", 60);
		TableUtils.addColumn(tableOfSkuErrors, "差额", 60);
		TableUtils.addColumn(tableOfSkuErrors, "编号", 70);
		TableUtils.addColumn(tableOfSkuErrors, "操作", 90);

		for (Map<String, Object> map : list) {
			try {

				TableItem tableItem = new TableItem(tableOfSkuErrors, SWT.NONE);
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

				Button delistingButton = new Button(tableOfSkuErrors, SWT.NONE);
				delistingButton.setData(map);
				delistingButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						try {
							Map map = (Map) arg0.widget.getData();

							Appliance appliance = applianceMng
									.getByNickOfOne(map.get("nick").toString());

							Long pNumIid = (Long) map.get("pNumIid");
							Long sNUmIid = (Long) map.get("sNumIid");

							List<Sku> orgList = baoItemSkuMng.getByNumIid(
									appliance.getAppKey(),
									appliance.getAppSecret(), pNumIid);
							List<Sku> selfList = baoItemSkuMng.getByNumIid(
									appliance.getAppKey(),
									appliance.getAppSecret(), sNUmIid);

							Item item = new TaobaoItemMngImpl().get(pNumIid);

							new TaobaoItemMngImpl().updatePropertyAlias(
									appliance.getAppKey(),
									appliance.getAppSecret(), sNUmIid,
									item.getPropertyAlias(),
									appliance.getSessionKey());

							manager.editPropertyAlias((Long) map.get("id"),
									item.getPropertyAlias());

							try {
								for (Sku sku : orgList) {
									Sku selfSku = JxPathUtils.get(
											selfList,
											".[ properties='"
													+ sku.getProperties()
													+ "' ]");
									if (selfSku == null) {
										baoItemSkuMng.add(
												appliance.getAppKey(),
												appliance.getAppSecret(),
												sNUmIid, sku.getProperties(),
												sku.getPropertiesName(),
												sku.getQuantity(),
												Double.valueOf(sku.getPrice()),
												sku.getOuterId(), null, null,
												null, null, null, null,
												appliance.getSessionKey());
									}
								}
								for (Sku sku : selfList) {
									Sku orgSku = JxPathUtils.get(
											orgList,
											".[ properties='"
													+ sku.getProperties()
													+ "' ]");
									if (orgSku == null) {
										baoItemSkuMng.delete(
												appliance.getAppKey(),
												appliance.getAppSecret(),
												sNUmIid, sku.getProperties(),
												appliance.getSessionKey());
									}
								}
							} catch (Exception e) {
								MessageBox messageBox = new MessageBox(
										tableOfSkuErrors.getShell(), SWT.OK
												| SWT.CANCEL);
								messageBox.setMessage("是否结束? errors:"
										+ e.getMessage());
								if (messageBox.open() == SWT.OK) {
									return;
								}

							}

							ItemErrors itemErrors = itemErrorsMng
									.getByItemAndType((Long) map.get("id"),
											ItemErrorsType.SKU变动.toString());
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

		tableOfSkuErrors.addSelectionListener(new SelectionListener() {
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

	private TaoBaoItemSkuMng baoItemSkuMng = new TaoBaoItemSkuMngImpl();
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

	public void setTableOfSkuErrors(Table tableOfSkuErrors) {
		this.tableOfSkuErrors = tableOfSkuErrors;
	}

}
