package com.edaigou.action;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.jdbc.page.Pagination;
import com.common.util.WebUtils;
import com.edaigou.action.ext.PriceUtils;
import com.edaigou.entity.Item.ItemStatus;
import com.edaigou.entity.ItemErrors;
import com.edaigou.entity.ItemFilters;
import com.edaigou.entity.Shop;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.manager.ItemErrorsMng;
import com.edaigou.manager.ItemFiltersMng;
import com.edaigou.manager.ItemMng;
import com.edaigou.manager.ShopMng;

@Controller
public class BrowserController {

	private Browser browser;
	private Combo browserComboOfShop;
	private Combo browserComboOfStatus;
	private Button browserButtonOfOpertion;
	private Button browserButtonOfUp;
	private Button browserButtonOfNext;
	private Button browserButtonOfMinPrice;
	private Button browserButtonOfDetailPage;
	private Button browserButtonOfEditPrice;
	private Button browserButtonOfCmlibUrl;
	private CLabel browserCLableOfShowPage;
	private Text browserTextOfMinPrice;
	private ItemGroupForm itemGroupForm;
	private Button browserButtonOfrefresh;
	private Button browserButtonOfDeleteItem;
	private Text browserTextOfPageChange;
	private Button browserButtonOfAuto;
	private CTabItem tabItem;
	private CTabFolder tabFolder;
	private Button browserCheckButtonOfItemExists;
	private Combo browserComboOfItemErrors;

	public void init() {
		List<Shop> shops = shopMng.query();
		for (int i = 0; i < shops.size(); i++) {
			Shop shop = shops.get(i);
			browserComboOfShop.add(shop.getNick());
			browserComboOfShop.setData(i + "", shop.getId());
		}
		browserComboOfStatus.add("创建");
		browserComboOfStatus.add("下架");
		browserComboOfStatus.add("上架");

		browserButtonOfUp.setEnabled(false);
		browserButtonOfNext.addListener(SWT.Selection, new NextListener());
		browserButtonOfUp.addListener(SWT.Selection, new UpListener());

		browserTextOfMinPrice.setVisible(false);

		browserComboOfStatus.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		browserComboOfItemErrors.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		browserCheckButtonOfItemExists.addListener(SWT.Selection,
				new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						try {
							adjustPage();
						} catch (Exception e) {
							MessageText.error(e.getMessage());
						}
					}
				});

		browserComboOfShop.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		browserButtonOfrefresh.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					if (!StringUtils.isBlank(browserTextOfPageChange.getText())) {
						adjusRefreshPage(Integer
								.valueOf(browserTextOfPageChange.getText()));
						adjustBrowser();
						adjustEdit();
					} else {
						browser.refresh();
					}
				} catch (Exception e) {
				}
			}
		});

		/*
		 * 新的超级链接页面的导入的百分比,在导入新的页面时发生，此时链接地址已知
		 */
		browser.addProgressListener(new ProgressAdapter() {
			@Override
			public void completed(ProgressEvent e) {
				try {
					if (browserButtonOfEditPrice.getSelection()) {
					}
					if (browserButtonOfCmlibUrl.getSelection()) {
						if (browser.getUrl().startsWith(
								"http://sell.taobao.com")) {
							browser.execute("window.scrollTo(300,300)");
						}
					}
					if (browserButtonOfMinPrice.getSelection()) {
						browser.execute("window.scrollTo(0,400)");
					}
				} catch (Exception ex) {
					MessageText.error(ex.getMessage());
				}
			}
		});

		browserButtonOfEditPrice.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				browser.setUrl("http://tbgr.huanleguang.com/promotion/promo/index/?hpm=1");
				browserButtonOfOpertion.setEnabled(true);
				browserTextOfMinPrice.setVisible(false);
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}
		});

		browserButtonOfCmlibUrl.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				browser.setUrl("http://sell.taobao.com/auction/merchandise/auction_list.htm");
				browserButtonOfOpertion.setEnabled(true);
				browserTextOfMinPrice.setVisible(false);
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}
		});

		browserButtonOfMinPrice.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				browser.setUrl("http://s.taobao.com/search");
				browserTextOfMinPrice.setVisible(true);
				browserButtonOfOpertion.setEnabled(true);
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}
		});

		browserButtonOfDetailPage.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				browserButtonOfOpertion.setEnabled(true);
				try {
					adjustPage();
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
				browser.setUrl("");
			}
		});

		browserButtonOfAuto.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event arg0) {
				new CounterTask(arg0, browserButtonOfDetailPage.getSelection())
						.execute();
			}
		});

		browserButtonOfOpertion.addListener(SWT.Selection,
				new OpertionListenter());

		browserButtonOfDeleteItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Map map = (Map) itemGroupForm.getTitle().getData();
				if (map == null) {
					return;
				}
				try {
					if (StringUtils.equals(ItemStatus.创建.toString(),
							map.get("status").toString())) {
						itemMng.delete((Long) map.get("id"));
						adjusRefreshPage(page == null ? 1 : page.getPageNo());
						adjustBrowser();
						adjustEdit();
					} else {
						MessageText.error("此操作只有新建商品才能删除");
					}
				} catch (Exception e) {
					MessageText.error(e.getMessage());
				}
			}
		});

		itemGroupForm.getButtonOfSearchItem().addListener(SWT.Selection,
				new Listener() {

					@Override
					public void handleEvent(Event arg0) {
						try {
							adjusRefreshPage(1);
							adjustBrowser();
							adjustEdit();
							tabFolder.setSelection(tabItem);
						} catch (Exception e) {
							MessageText.error(e.getMessage());
						}
					}
				});
	}

	class CounterTask extends SwingWorker<Integer, Integer> {

		public CounterTask(Event arg0, Boolean browserButtonOfDetailPageSelec) {
			this.arg0 = arg0;
			this.browserButtonOfDetailPageSelect = browserButtonOfDetailPageSelec;
		}

		private Event arg0;
		private Boolean browserButtonOfDetailPageSelect;

		@Override
		protected Integer doInBackground() throws Exception {
			while (page == null || !page.isLastPage()) {
				if (!browserButtonOfDetailPageSelect) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						NextListener nextListener = new NextListener();
						nextListener.handleEvent(arg0);
					}
				});
				// 如果是详情页
				if (browserButtonOfDetailPageSelect) {
					Map<?, ?> map = (Map<?, ?>) page.getList().get(0);
					Double shopSalePrice = map.get("shopSalePrice") == null ? 0D
							: Double.valueOf(map.get("shopSalePrice")
									.toString());
					Double realSalesPrice = Double.valueOf(map.get(
							"realSalesPrice").toString());
					if (shopSalePrice.equals(realSalesPrice)) {
						continue;
					}
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						OpertionListenter opertionListenter = new OpertionListenter();
						opertionListenter.handleEvent(arg0);
						itemEditController.editListenter.handleEvent(arg0);
					}
				});
			}
			return 0;
		}

	}

	class OpertionListenter implements Listener {

		@Override
		public void handleEvent(Event arg0) {
			if (browserButtonOfCmlibUrl.getSelection()) {
				String js_exe = "var browser_href=KISSY.one('.photo').all('a')[0].getAttribute('href');";
				js_exe += "KISSY.all('body').append('#EDIAGOU');";
				js_exe += "KISSY.all('body').append(browser_href);";
				js_exe += "KISSY.all('body').append('#END_EDAIGOU');";
				browser.execute(js_exe);
				String text = browser.getText();
				if (text.contains("#EDIAGOU")) {
					String href = text.substring(text.indexOf("#EDIAGOU") + 8,
							text.indexOf("#END_EDAIGOU"));
					itemGroupForm.getsNumIid().setText(
							WebUtils.getParameters(href, "id"));
				} else {
					itemGroupForm.getsNumIid().setText("");
				}
			}
			if (browserButtonOfMinPrice.getSelection()) {
				String pNumIid = itemGroupForm.getUrl().getToolTipText();
				List<ItemFilters> itemFilters = itemFiltersMng.getByNumIid(Long
						.valueOf(pNumIid));
				Double minPrice = PriceUtils.getMinPrice(browser.getText(),
						itemGroupForm.getTitle().getText(), itemFilters);
				browserTextOfMinPrice.setText(minPrice.toString());
			}
			if (browserButtonOfEditPrice.getSelection()) {
				String numIid = itemGroupForm.getsNumIid().getText();
				String price = itemGroupForm.getRealSalesPrice().getText();
				String ra = itemGroupForm.getRealSaleDiscount().getText();
				String js_exe = "$(\"*[for='J_TopCheckAll']\").click();";
				js_exe += "if($('#J_SpecPrice_" + numIid + "').length>0){"
						+ "$('#J_SpecPrice_" + numIid + "').val('"
						+ Double.valueOf(price).intValue()
						+ "');$('#J_SpecPrice_" + numIid + "').change();}";
				js_exe += "else{" + "$('#J_PromoValue_" + numIid + "').val('"
						+ Double.valueOf(ra) * 10 + "');$('#J_PromoValue_"
						+ numIid + "').change();}";
				js_exe += "KISSY.all('#J_BtmAddToPromo')[0].click();";
				browser.execute(js_exe);
				browser.execute("window.scrollTo(180,0)");
			}
			if (browserButtonOfDetailPage.getSelection()) {
				String html = browser.getText();
				itemGroupForm.getRealSalesPrice().setToolTipText(
						PriceUtils.getPrice(html));
			}
		}
	}

	Pagination page = null;

	private void adjustPage() throws MalformedURLException {

		Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		List<ItemErrors> errors = itemErrorsMng
				.getByErrorType(browserComboOfItemErrors.getText());
		for (ItemErrors itemErrors : errors) {
			ids = (Long[]) ArrayUtils.add(ids, itemErrors.getItemId());
		}
		if (!StringUtils.isBlank(browserComboOfItemErrors.getText())) {
			ids = (Long[]) ArrayUtils.add(ids, 0L);
		}

		Long shopId = (Long) browserComboOfShop.getData(browserComboOfShop
				.getSelectionIndex() + "");
		String status = browserComboOfStatus.getText();
		String title = itemGroupForm.getTitle().getText();

		page = itemMng.getPageOfMap(ids, shopId, title, status, 1, 1,
				browserCheckButtonOfItemExists.getSelection());

		if (page.getTotalCount() > 0) {
			browserButtonOfNext.setEnabled(true);
		} else {
			browserButtonOfNext.setEnabled(false);
		}

		browserButtonOfUp.setEnabled(false);

		browserCLableOfShowPage.setText("0/" + page.getTotalCount());

		itemGroupForm.clear(true);

		page = null;
	}

	private void adjustNextPage() throws MalformedURLException {

		Long[] ids = ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		List<ItemErrors> errors = itemErrorsMng
				.getByErrorType(browserComboOfItemErrors.getText());
		for (ItemErrors itemErrors : errors) {
			ids = (Long[]) ArrayUtils.add(ids, itemErrors.getItemId());
		}
		if (!StringUtils.isBlank(browserComboOfItemErrors.getText())) {
			ids = (Long[]) ArrayUtils.add(ids, 0L);
		}

		Long shopId = (Long) browserComboOfShop.getData(browserComboOfShop
				.getSelectionIndex() + "");
		String status = browserComboOfStatus.getText();

		int pageNo = 1;
		if (page != null) {
			if (browserCheckButtonOfItemExists.getSelection()) {
				pageNo = page.getPageNo();
			} else if (StringUtils.isNotBlank(browserComboOfItemErrors
					.getText()) && !browserButtonOfEditPrice.getSelection()) {
				pageNo = page.getPageNo();
			} else {
				pageNo = page.getNextPage();
			}
		}

		page = itemMng.getPageOfMap(ids, shopId, null, status, pageNo, 1,
				browserCheckButtonOfItemExists.getSelection());

		if (page.isLastPage()) {
			browserButtonOfNext.setEnabled(false);
		} else {
			browserButtonOfNext.setEnabled(true);
		}

		if (page.isFirstPage()) {
			browserButtonOfUp.setEnabled(false);
		} else {
			browserButtonOfUp.setEnabled(true);
		}

		Map map = (Map) page.getList().get(0);
		itemGroupForm.write(map);

		browserCLableOfShowPage.setText(page.getPageNo() + "/"
				+ page.getTotalCount());
	}

	private void adjusRefreshPage(Integer pageNo) throws MalformedURLException {
		Long shopId = (Long) browserComboOfShop.getData(browserComboOfShop
				.getSelectionIndex() + "");
		String status = browserComboOfStatus.getText();
		String title = itemGroupForm.getTitle().getText();

		page = itemMng.getPageOfMap(null, shopId, title, status, pageNo, 1,
				browserCheckButtonOfItemExists.getSelection());

		if (page.isLastPage()) {
			browserButtonOfNext.setEnabled(false);
		} else {
			browserButtonOfNext.setEnabled(true);
		}

		if (page.isFirstPage()) {
			browserButtonOfUp.setEnabled(false);
		} else {
			browserButtonOfUp.setEnabled(true);
		}

		Map map = (Map) page.getList().get(0);
		itemGroupForm.write(map);

		browserCLableOfShowPage.setText(page.getPageNo() + "/"
				+ page.getTotalCount());
	}

	private void adjustUpPage() throws MalformedURLException {
		Long shopId = (Long) browserComboOfShop.getData(browserComboOfShop
				.getSelectionIndex() + "");
		String status = browserComboOfStatus.getText();

		page = itemMng.getPageOfMap(null, shopId, null, status,
				page.getPageSize(), 1,
				browserCheckButtonOfItemExists.getSelection());

		if (page.isLastPage()) {
			browserButtonOfNext.setEnabled(false);
		} else {
			browserButtonOfNext.setEnabled(true);
		}
		if (page.isFirstPage()) {
			browserButtonOfUp.setEnabled(false);
		} else {
			browserButtonOfUp.setEnabled(true);
		}

		Map map = (Map) page.getList().get(0);
		itemGroupForm.write(map);

		browserCLableOfShowPage.setText(page.getPageNo() + "/"
				+ page.getTotalCount());
	}

	private void adjustBrowser() {

		if (browserButtonOfMinPrice.getSelection()) {
			String js_exe = "KISSY.all('.search-combobox-input')[0].value='"
					+ itemGroupForm.getTitle().getText() + "';";
			js_exe += "KISSY.all('.icon-btn-search')[0].click();";
			browser.execute(js_exe);
		}

		if (browserButtonOfEditPrice.getSelection()) {
			String js_exe = "document.getElementById('J_SearchTitle').value='"
					+ itemGroupForm.getTitle().getText() + "';";
			js_exe += "document.getElementById('J_SearchBtn').click();";
			browser.execute(js_exe);
		}

		if (browserButtonOfCmlibUrl.getSelection()) {
			String js_exe = "document.getElementById('search-keyword').value='"
					+ itemGroupForm.getTitle().getText() + "';";
			js_exe += "KISSY.one('.search-btn')[0].click()";
			browser.execute(js_exe);
		}

		if (browserButtonOfDetailPage.getSelection()) {
			String url = "http://item.taobao.com/item.htm?spm=a1z10.1.w8853032-8986599690.1.r96Lrh&id="
					+ itemGroupForm.getsNumIid().getText();
			browser.setUrl(url);
		}

		browserTextOfMinPrice.setText("");
	}

	private void adjustEdit() {
		itemGroupForm.getGroupOfItem().setText(ItemGroupForm.EDIT_ITEM);
		itemGroupForm.getButtonOfSaveItem().setText(
				ItemGroupForm.SAVE_EDIT_ITEM);
	}

	class NextListener implements Listener {

		@Override
		public void handleEvent(Event arg0) {
			try {

				adjustNextPage();
				adjustBrowser();
				adjustEdit();

			} catch (Exception ex) {
				MessageText.error(ex.getMessage());
			}
		}
	}

	class UpListener implements Listener {

		@Override
		public void handleEvent(Event arg0) {
			try {
				adjustUpPage();
				adjustBrowser();
				adjustEdit();
			} catch (Exception e) {
				MessageText.error(e.getMessage());
			}
		}
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public void setBrowserComboOfShop(Combo browserComboOfShop) {
		this.browserComboOfShop = browserComboOfShop;
	}

	public void setBrowserComboOfStatus(Combo browserComboOfStatus) {
		this.browserComboOfStatus = browserComboOfStatus;
	}

	public void setBrowserButtonOfOpertion(Button browserButtonOfOpertion) {
		this.browserButtonOfOpertion = browserButtonOfOpertion;
	}

	public void setBrowserButtonOfUp(Button browserButtonOfUp) {
		this.browserButtonOfUp = browserButtonOfUp;
	}

	public void setBrowserButtonOfNext(Button browserButtonOfNext) {
		this.browserButtonOfNext = browserButtonOfNext;
	}

	public void setBrowserButtonOfMinPrice(Button browserButtonOfMinPrice) {
		this.browserButtonOfMinPrice = browserButtonOfMinPrice;
	}

	public void setBrowserButtonOfDetailPage(Button browserButtonOfDetailPage) {
		this.browserButtonOfDetailPage = browserButtonOfDetailPage;
	}

	public void setBrowserButtonOfEditPrice(Button browserButtonOfEditPrice) {
		this.browserButtonOfEditPrice = browserButtonOfEditPrice;
	}

	public void setBrowserButtonOfCmlibUrl(Button browserButtonOfCmlibUrl) {
		this.browserButtonOfCmlibUrl = browserButtonOfCmlibUrl;
	}

	public void setBrowserCLableOfShowPage(CLabel browserCLableOfShowPage) {
		this.browserCLableOfShowPage = browserCLableOfShowPage;
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}

	public void setBrowserTextOfMinPrice(Text browserTextOfMinPrice) {
		this.browserTextOfMinPrice = browserTextOfMinPrice;
	}

	public void setBrowserButtonOfrefresh(Button browserButtonOfrefresh) {
		this.browserButtonOfrefresh = browserButtonOfrefresh;
	}

	public void setBrowserButtonOfDeleteItem(Button browserButtonOfDeleteItem) {
		this.browserButtonOfDeleteItem = browserButtonOfDeleteItem;
	}

	public void setBrowserTextOfPageChange(Text browserTextOfPageChange) {
		this.browserTextOfPageChange = browserTextOfPageChange;
	}

	public void setBrowserButtonOfAuto(Button browserButtonOfAuto) {
		this.browserButtonOfAuto = browserButtonOfAuto;
	}

	public void setTabItem(CTabItem tabItem) {
		this.tabItem = tabItem;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public void setBrowserCheckButtonOfItemExists(
			Button browserCheckButtonOfItemExists) {
		this.browserCheckButtonOfItemExists = browserCheckButtonOfItemExists;
	}

	public void setBrowserComboOfItemErrors(Combo browserComboOfItemErrors) {
		this.browserComboOfItemErrors = browserComboOfItemErrors;
	}

	@Autowired
	private ItemErrorsMng itemErrorsMng;
	@Autowired
	private ItemFiltersMng itemFiltersMng;
	@Autowired
	private ItemEditController itemEditController;
	@Autowired
	private ItemMng itemMng;
	@Autowired
	private ShopMng shopMng;
	private org.slf4j.Logger log = LoggerFactory
			.getLogger(BrowserController.class);
}
