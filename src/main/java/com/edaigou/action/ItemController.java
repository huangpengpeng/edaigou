package com.edaigou.action;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import com.edaigou.entity.Item;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.Appliance;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.PromotionItem;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.form.widgets.TableUtils;
import com.edaigou.manager.ApplianceMng;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.PromotionItemMng;
import com.edaigou.manager.ShopMng;
import com.edaigou.resource.ImageUtils;
import com.edaigou.resource.ResourceUtils;
import com.edaigou.service.TaobaoItemSvc;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

@Controller
public class ItemController {

	private Button buttonOfShowSaveItem;
	private Table table;
	private PageForm pageForm;
	private ItemGroupForm itemGroupForm;
	private Button buttonOfExport;
	private Button createItemOfShangJia;
	private Button checkboxOfItemExists;

	private Long getComboOfsearchShop() {
		Object value = pageForm.getComboOfsearchShop().getData(
				pageForm.getComboOfsearchShop().getSelectionIndex() + "");
		if (value != null) {
			return Long.valueOf(value.toString());
		}
		return null;
	}

	public Pagination page;

	public void init(Integer pageNo) {

		Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		List<ItemErrors> errors = itemErrorsMng.getByErrorType(pageForm
				.getComboOfItemErrors().getText());
		for (ItemErrors itemErrors : errors) {
			ids = (Long[]) ArrayUtils.add(ids, itemErrors.getItemId());
		}
		if (!StringUtils.isBlank(pageForm.getComboOfItemErrors().getText())) {
			ids = (Long[]) ArrayUtils.add(ids, 0L);
		}
		Boolean ifItemExists = checkboxOfItemExists.getSelection();
		page = manager.getPageOfMap(ids, getComboOfsearchShop(), pageForm
				.getSearchText().getText(), ItemStatus.创建.toString(), pageNo,
				10, ifItemExists);
		pageForm.showPage(page);

		table(page.getList(Map.class));
	}

	public void table(List<Map> list) {

		TableUtils.removeAll(table);

		TableUtils.addColumn(table, "商品图片", 90);
		TableUtils.addColumn(table, "掌柜名称", 70);
		TableUtils.addColumn(table, "商品标题", 190);
		TableUtils.addColumn(table, "原始标价", 58);
		TableUtils.addColumn(table, "原售价格", 50);
		TableUtils.addColumn(table, "实售价格", 50);
		TableUtils.addColumn(table, "提成总额", 50);
		TableUtils.addColumn(table, "实际折扣", 60);
		TableUtils.addColumn(table, "实际利润", 60);
		TableUtils.addColumn(table, "状态", 60);
		TableUtils.addColumn(table, "低格", 60);
		TableUtils.addColumn(table, "差额", 60);
		TableUtils.addColumn(table, "编号", 70);
		TableUtils.addColumn(table, "店价", 50);
		TableUtils.addColumn(table, "操作", 95);

		for (Map<String, Object> map : list) {
			try {

				TableItem tableItem = new TableItem(table, SWT.NONE);
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

				TableUtils.addItem(
						tableItem,
						map.get("shopSalePrice") == null ? "" : map.get(
								"shopSalePrice").toString(), 13);

				tableItem.setData(map);

				Button deleteButton = new Button(table, SWT.NONE);
				deleteButton.setData(map);
				deleteButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						Map map = (Map) arg0.widget.getData();
						if (map.get("sNumIid") != null
								&& StringUtils.isNotBlank(map.get("sNumIid")
										.toString())) {
							try{
							Appliance appliance = applianceMng
									.getByNickOfOne(map.get("nick").toString());
							new TaobaoItemMngImpl().delisting(
									appliance.getAppKey(),
									appliance.getAppSecret(),
									Long.valueOf(map.get("sNumIid").toString()),
									appliance.getSessionKey());
							}catch(Exception e){
								MessageText.error(e.getMessage());
								return;
							}
						}
						manager.delete((Long) map.get("id"));
						init(page.getPageNo());
					}
				});
				deleteButton.setText("删除");
				Button upButton = new Button(table, SWT.NONE);
				upButton.setData(map);
				upButton.setText("上架");
				upButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						Map map = (Map) arg0.widget.getData();
						manager.edit((Long) map.get("id"),
								ItemStatus.上架.toString());
						init(page.getPageNo());
					}
				});

				TableUtils.addButton(tableItem, 14, deleteButton, SWT.LEFT, 45);
				TableUtils.addButton(tableItem, 14, upButton, SWT.RIGHT, 45);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

	}

	public void addActionListener() {
		pageForm.addUpListener(PageForm.CREATE_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(page.getPrePage());
			}
		});
		pageForm.addNextListener(PageForm.CREATE_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(page.getNextPage());
			}
		});

		pageForm.addSearchListener(PageForm.CREATE_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(1);
			}
		});

		buttonOfShowSaveItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				itemGroupForm.getGroupOfItem().setText(ItemGroupForm.ADD_ITEM);
				itemGroupForm.getButtonOfSaveItem().setText(
						ItemGroupForm.SAVE_ADD_ITEM);
				itemGroupForm.clear(true);
			}
		});

		buttonOfExport.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String filename = ResourceUtils.getResource() + "url.txt";
				List<Item> createItems = manager.getByStatus(ItemStatus.创建
						.toString());
				try {
					List<String> urls = new ArrayList<String>();
					for (Item item : createItems) {
						PromotionItem pItem = promotionItemMng.get(item.getId());
						urls.add(pItem.getUrl());
					}
					FileUtils.writeLines(new File(filename), urls);
					pageForm.getSearchText().setText(filename);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		});

		pageForm.getButtonOfRef().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (pageForm.getTabFolder().getSelectionIndex() == 0) {
					init(page == null ? 1 : page.getPageNo());
				}
			}
		});

		createItemOfShangJia.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				MessageBox messageBox = new MessageBox(createItemOfShangJia
						.getShell(), SWT.OK | SWT.CANCEL);
				messageBox.setMessage("确认商家全部新品?");
				if (messageBox.open() != SWT.OK) {
					return;
				}
				List<Item> items = manager.getByStatus(ItemStatus.创建.toString());
				try {
					createItemOfShangJia.setEnabled(false);
					for (Item item : items) {
						if (item.getsNumIid() == null) {
							continue;
						}
						manager.edit(item.getId(), ItemStatus.上架.toString());
					}
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				} finally {
					createItemOfShangJia.setEnabled(true);
				}
			}
		});

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 80;
			}
		});

		table.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Map map = (Map) arg0.item.getData();

				try {
					itemGroupForm.write(map);
				} catch (Exception e) {
					MessageText.error(e.getMessage());
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

	public void setButtonOfShowSaveItem(Button buttonOfShowSaveItem) {
		this.buttonOfShowSaveItem = buttonOfShowSaveItem;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	public void setButtonOfExport(Button buttonOfExport) {
		this.buttonOfExport = buttonOfExport;
	}

	public void setCreateItemOfShangJia(Button createItemOfShangJia) {
		this.createItemOfShangJia = createItemOfShangJia;
	}

	public void setCheckboxOfItemExists(Button checkboxOfItemExists) {
		this.checkboxOfItemExists = checkboxOfItemExists;
	}

	@Autowired
	private ApplianceMng applianceMng;
	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ShopMng shopMng;
	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private ItemMng manager;
	@Autowired
	private TaobaoItemSvc taobaoItemSvc;
	private Logger log = LoggerFactory.getLogger(ItemController.class);
}
