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
import com.edaigou.entity.Appliance;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
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
import com.edaigou.service.TaobaoItemSvc;
import com.taobao.biz.manager.impl.TaobaoItemMngImpl;

/**
 * 上架商品处理控制器
 * 
 * @author zoro
 *
 */
@Controller
public class ItemListingController {

	private Table tableOfListing;
	private PageForm pageForm;
	private ItemGroupForm itemGroupForm;

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
		page = manager.getPageOfMap(ids, getComboOfsearchShop(), pageForm
				.getSearchText().getText(), ItemStatus.上架.toString(), pageNo,
				10, false);
		pageForm.showPage(page);

		table(page.getList(Map.class));
	}

	public void table(List<Map> list) {

		TableUtils.removeAll(tableOfListing);

		TableUtils.addColumn(tableOfListing, "商品图片", 90);
		TableUtils.addColumn(tableOfListing, "掌柜名称", 90);
		TableUtils.addColumn(tableOfListing, "商品标题", 190);
		TableUtils.addColumn(tableOfListing, "原始标价", 60);
		TableUtils.addColumn(tableOfListing, "原售价格", 60);
		TableUtils.addColumn(tableOfListing, "实售价格", 60);
		TableUtils.addColumn(tableOfListing, "提成总额", 60);
		TableUtils.addColumn(tableOfListing, "实际折扣", 60);
		TableUtils.addColumn(tableOfListing, "实际利润", 60);
		TableUtils.addColumn(tableOfListing, "状态", 60);
		TableUtils.addColumn(tableOfListing, "低格", 60);
		TableUtils.addColumn(tableOfListing, "差额", 60);
		TableUtils.addColumn(tableOfListing, "编号", 70);
		TableUtils.addColumn(tableOfListing, "操作", 90);

		for (Map<String, Object> map : list) {
			try {

				TableItem tableItem = new TableItem(tableOfListing, SWT.NONE);
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

				Button delistingButton = new Button(tableOfListing, SWT.NONE);
				delistingButton.setData(map);
				delistingButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						try {
							Map map = (Map) arg0.widget.getData();

							Appliance appliance = applianceMng
									.getByNickOfOne(map.get("nick").toString());

							try{
							new TaobaoItemMngImpl().delisting(
									appliance.getAppKey(),
									appliance.getAppSecret(),
									(Long) map.get("sNumIid"),
									appliance.getSessionKey());
							}catch(Exception e){
								MessageBox messageBox = new MessageBox(tableOfListing
										.getShell(), SWT.OK | SWT.CANCEL);
								messageBox.setMessage("淘宝店铺商品下架错误，Msg:"+e.getMessage()+" 是否还执行系统下架？");
								if (messageBox.open() != SWT.OK) {
									return;
								}
							}

							manager.edit((Long) map.get("id"),
									ItemStatus.下架.toString());

							init(page.getPageNo());
						} catch (Exception e) {
							MessageText.error(e.getMessage());
							log.error(e.getMessage(), e);
						}
					}
				});
				delistingButton.setText("下架");
				Button upButton = new Button(tableOfListing, SWT.NONE);
				upButton.setData(map);
				upButton.setText("创建");
				upButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						Map map = (Map) arg0.widget.getData();
						manager.edit((Long) map.get("id"),
								ItemStatus.创建.toString());
						init(page.getPageNo());
					}
				});

				TableUtils.addButton(tableItem, 13, delistingButton, SWT.LEFT, 45);
				TableUtils.addButton(tableItem, 13, upButton, SWT.RIGHT, 45);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void addActionListener() {

		pageForm.addUpListener(PageForm.LiSTING_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(page.getPrePage());
			}
		});

		pageForm.addNextListener(PageForm.LiSTING_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(page.getNextPage());
			}
		});

		pageForm.getButtonOfRef().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (pageForm.getTabFolder().getSelectionIndex() == 3) {
					init(page == null ? 1 : page.getPageNo());
				}
			}
		});

		pageForm.addSearchListener(PageForm.LiSTING_ITEM, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				init(1);
			}
		});

		tableOfListing.addSelectionListener(new SelectionListener() {
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

	private Long getComboOfsearchShop() {
		Object value = pageForm.getComboOfsearchShop().getData(
				pageForm.getComboOfsearchShop().getSelectionIndex() + "");
		if (value != null) {
			return Long.valueOf(value.toString());
		}
		return null;
	}

	public void setTableOfListing(Table tableOfListing) {
		this.tableOfListing = tableOfListing;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public void setItemErrorsMng(ItemErrorsMng itemErrorsMng) {
		this.itemErrorsMng = itemErrorsMng;
	}

	public void setManager(ItemMng manager) {
		this.manager = manager;
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	private Logger log = LoggerFactory.getLogger(ItemListingController.class);

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
}
