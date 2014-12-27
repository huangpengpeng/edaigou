package com.edaigou.form.event;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.edaigou.action.BrowserController;
import com.edaigou.action.ItemAppController;
import com.edaigou.action.ItemCommissionRateController;
import com.edaigou.action.ItemController;
import com.edaigou.action.ItemDelistingController;
import com.edaigou.action.ItemDetailController;
import com.edaigou.action.ItemEditController;
import com.edaigou.action.ItemFiltersController;
import com.edaigou.action.ItemGetController;
import com.edaigou.action.ItemListingController;
import com.edaigou.action.ItemListingJkController;
import com.edaigou.action.ItemPlEditPriceController;
import com.edaigou.action.ItemRealSalesPriceController;
import com.edaigou.action.ItemSaveController;
import com.edaigou.action.ItemSkuErrorsController;
import com.edaigou.action.ItemSubsidyRateController;
import com.edaigou.action.ItemTitleErrorsController;
import com.edaigou.action.KutongItemController;
import com.edaigou.action.ShopController;
import com.edaigou.action.TabFolderController;
import com.edaigou.form.ApplicationContextUtils.ApplicationContextEvent;
import com.edaigou.form.FormUtils;
import com.edaigou.form.MainForm;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.PageForm;

@Component
public class MainFromNewInstanceApplicationListener implements
		ApplicationListener<ApplicationEvent> {
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		ApplicationContextEvent applicationContextEvent = null;
		if (event instanceof ApplicationContextEvent) {
			applicationContextEvent = (ApplicationContextEvent) event;
		}
		if (applicationContextEvent == null) {
			return;
		}
		MainForm form = applicationContextEvent.getBean(MainForm.class);
		ItemController itemController = applicationContextEvent
				.getBean(ItemController.class);

		itemController.setButtonOfShowSaveItem((Button) FormUtils.get(
				"buttonOfShowSaveItem", form));

		itemController.setTable((Table) FormUtils.get("table", form));
		itemController.setPageForm((PageForm) FormUtils.get("pageForm", form));

		itemController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));

		itemController.setButtonOfExport((Button) FormUtils.get(
				"buttonOfExport", form));
		itemController.setCreateItemOfShangJia((Button) FormUtils.get(
				"createItemOfShangJia", form));
		itemController.setCheckboxOfItemExists((Button)FormUtils.get("checkboxOfItemExists", form));

		itemController.init(null);
		itemController.addActionListener();

		ShopController shopController = applicationContextEvent
				.getBean(ShopController.class);
		shopController.setPageForm((PageForm) FormUtils.get("pageForm", form));
		shopController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));
		shopController.init();

		BrowserController browserController = applicationContextEvent
				.getBean(BrowserController.class);
		browserController.setBrowser((Browser) FormUtils.get("browser", form));
		browserController.setBrowserComboOfShop((Combo) FormUtils.get(
				"browserComboOfShop", form));
		browserController.setBrowserComboOfStatus((Combo) FormUtils.get(
				"browserComboOfStatus", form));
		browserController.setBrowserButtonOfOpertion((Button) FormUtils.get(
				"browserButtonOfOpertion", form));
		browserController.setBrowserButtonOfNext((Button) FormUtils.get(
				"browserButtonOfNext", form));
		browserController.setBrowserButtonOfUp((Button) FormUtils.get(
				"browserButtonOfUp", form));
		browserController.setBrowserButtonOfCmlibUrl((Button) FormUtils.get(
				"browserButtonOfCmlibUrl", form));
		browserController.setBrowserButtonOfDetailPage((Button) FormUtils.get(
				"browserButtonOfDetailPage", form));
		browserController.setBrowserButtonOfEditPrice((Button) FormUtils.get(
				"browserButtonOfEditPrice", form));
		browserController.setBrowserButtonOfMinPrice((Button) FormUtils.get(
				"browserButtonOfMinPrice", form));
		browserController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));
		browserController.setBrowserTextOfMinPrice(FormUtils.getText(
				"browserTextOfMinPrice", form));
		browserController.setBrowserCLableOfShowPage((CLabel) FormUtils.get(
				"browserCLableOfShowPage", form));
		browserController.setBrowserButtonOfrefresh((Button) FormUtils.get(
				"browserButtonOfrefresh", form));
		browserController.setBrowserButtonOfDeleteItem((Button) FormUtils.get(
				"browserButtonOfDeleteItem", form));
		browserController.setBrowserTextOfPageChange(FormUtils.getText(
				"browserTextOfPageChange", form));
		browserController.setBrowserButtonOfAuto((Button) FormUtils.get(
				"browserButtonOfAuto", form));
		browserController.setTabItem((CTabItem) FormUtils.get("tabItem", form));
		browserController.setTabFolder((CTabFolder) FormUtils.get("tabFolder",
				form));
		browserController.setBrowserCheckButtonOfItemExists((Button)FormUtils.get("browserCheckButtonOfItemExists", form));
		browserController.setBrowserComboOfItemErrors((Combo)FormUtils.get("browserComboOfItemErrors", form));
		
		browserController.init();

		ItemFiltersController itemFiltersController = applicationContextEvent
				.getBean(ItemFiltersController.class);
		itemFiltersController.setButtonOfFilters((Button) FormUtils.get(
				"buttonOfFilters", form));
		itemFiltersController.setTextFilterOfItemId((Text) FormUtils.getText(
				"textFilterOfItemId", form));
		itemFiltersController.setTextFilterOfNick((Text) FormUtils.getText(
				"textFilterOfNick", form));
		itemFiltersController.setTableOfFilters((Table) FormUtils.get(
				"tableOfFilters", form));
		itemFiltersController.setButtonFilterOfRef((Button) FormUtils.get(
				"buttonFilterOfRef", form));

		itemFiltersController.init();

		ItemDetailController itemDetailController = applicationContextEvent
				.getBean(ItemDetailController.class);
		itemDetailController.setButtonOfInsertDetail((Button) FormUtils.get(
				"buttonOfInsertDetail", form));
		itemDetailController.setPageForm((PageForm) FormUtils.get("pageForm",
				form));

		itemDetailController.addListenter();

		ItemListingController itemListingController = applicationContextEvent
				.getBean(ItemListingController.class);
		itemListingController.setTableOfListing((Table) FormUtils.get(
				"tableOfListing", form));
		itemListingController.setPageForm((PageForm) FormUtils.get("pageForm",
				form));
		itemListingController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));

		itemListingController.init(null);
		itemListingController.addActionListener();

		ItemListingJkController itemListingJkController = applicationContextEvent
				.getBean(ItemListingJkController.class);
		itemListingJkController.setButtonOfListingJk((Button) FormUtils.get(
				"buttonOfListingJk", form));
		itemListingJkController.setButtonOfAuto((Button)FormUtils.get("buttonOfAuto", form));
		itemListingJkController.setPageForm((PageForm) FormUtils.get(
				"pageForm", form));

		itemListingJkController.addActionListener();

		ItemGetController itemGetController = applicationContextEvent
				.getBean(ItemGetController.class);
		itemGetController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));
		itemGetController.addActionListenter();

		ItemRealSalesPriceController itemRealSalesPriceController = applicationContextEvent
				.getBean(ItemRealSalesPriceController.class);
		itemRealSalesPriceController.setItemGroupForm((ItemGroupForm) FormUtils
				.get("itemGroupForm", form));
		itemRealSalesPriceController.addActionListenter();

		ItemSaveController itemSaveController = applicationContextEvent
				.getBean(ItemSaveController.class);
		itemSaveController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));
		itemSaveController.addActionListenter();

		ItemSubsidyRateController itemSubsidyRateController = applicationContextEvent
				.getBean(ItemSubsidyRateController.class);
		itemSubsidyRateController.setItemGroupForm((ItemGroupForm) FormUtils
				.get("itemGroupForm", form));
		itemSubsidyRateController.addActionListenter();
		
		ItemEditController itemEditController=applicationContextEvent.getBean(ItemEditController.class);
		itemEditController.setBrowserTextOfMinPrice(FormUtils.getText("browserTextOfMinPrice", form));
		itemEditController.setItemGroupForm((ItemGroupForm)FormUtils.get("itemGroupForm", form));
		itemEditController.addActionListenter();
		
		ItemPlEditPriceController itemPlEditPriceController=applicationContextEvent.getBean(ItemPlEditPriceController.class);
		itemPlEditPriceController.setButtonOfPlEditPrice((Button)FormUtils.get("buttonOfPlEditPrice", form));
		itemPlEditPriceController.setPageForm((PageForm)FormUtils.get("pageForm", form));
		itemPlEditPriceController.addActionListenter();
		
		
		ItemDelistingController itemDelistingController=applicationContextEvent.getBean(ItemDelistingController.class);
		itemDelistingController.setItemGroupForm((ItemGroupForm)FormUtils.get("itemGroupForm", form));
		itemDelistingController.setPageForm((PageForm)FormUtils.get("pageForm", form));
		itemDelistingController.setTableOfdelisting((Table)FormUtils.get("tableOfdelisting", form));
		itemDelistingController.init(null);
		itemDelistingController.addActionListener();
		
		
		ItemAppController itemAppController=applicationContextEvent.getBean(ItemAppController.class);
		itemAppController.setCompositeOfapp((Composite)FormUtils.get("compositeOfapp", form));
		itemAppController.setTabItemOfapp((CTabItem)FormUtils.get("tabItemOfapp", form));
		itemAppController.setTableOfapp((Table)FormUtils.get("tableOfapp", form));
		itemAppController.setTextOfappkey(FormUtils.getText("textOfappkey", form));
		itemAppController.setTextOfappsecret(FormUtils.getText("textOfappsecret", form));
		itemAppController.setTextOfnick(FormUtils.getText("textOfnick", form));
		itemAppController.setBrowser((Browser)FormUtils.get("browser", form));
		itemAppController.setTabFolder((CTabFolder)FormUtils.get("tabFolder", form));
		itemAppController.setButtonOfappcurl((Button)FormUtils.get("buttonOfappcurl", form));
		itemAppController.init();
		itemAppController.addActionListenter();
		
		
		KutongItemController kutongItemController=applicationContextEvent.getBean(KutongItemController.class);
		kutongItemController.setButtonOfkutongitem((Button)FormUtils.get("buttonOfkutongitem", form));
		kutongItemController.setPageForm((PageForm)FormUtils.get("pageForm", form));
		kutongItemController.setButtonOfAuto((Button)FormUtils.get("buttonOfAuto", form));
		kutongItemController.addActionListenter();
		
		
		ItemCommissionRateController ttemCommissionRateController=applicationContextEvent.getBean(ItemCommissionRateController.class);
		ttemCommissionRateController.setItemGroupForm((ItemGroupForm)FormUtils.get("itemGroupForm", form));
		ttemCommissionRateController.addActionListenter();
		
		ItemTitleErrorsController titleErrorsController=applicationContextEvent.getBean(ItemTitleErrorsController.class);
		titleErrorsController.setItemGroupForm((ItemGroupForm)FormUtils.get("itemGroupForm", form));
		titleErrorsController.setTableOfTitleErrors((Table)FormUtils.get("tableOfTitleErrors", form));
		titleErrorsController.init(null);
		titleErrorsController.addActionListener();
		
		ItemSkuErrorsController itemSkuErrorsController=applicationContextEvent.getBean(ItemSkuErrorsController.class);
		itemSkuErrorsController.setItemGroupForm((ItemGroupForm)FormUtils.get("itemGroupForm", form));
		itemSkuErrorsController.setTableOfSkuErrors((Table)FormUtils.get("tableOfSkuErrors", form));
		itemSkuErrorsController.init(null);
		itemSkuErrorsController.addActionListener();
		
		/*******************这段必须放到最后执行  分页显示才不会乱**********************/
		TabFolderController tabFolderController = applicationContextEvent
				.getBean(TabFolderController.class);
		tabFolderController.setTabItemOfListing((CTabItem) FormUtils.get(
				"tabItemOfListing", form));
		tabFolderController.setTabFolder((CTabFolder) FormUtils.get(
				"tabFolder", form));
		tabFolderController.setPageForm((PageForm) FormUtils.get("pageForm",
				form));
		tabFolderController.setCompositeOfListing((Composite) FormUtils.get(
				"compositeOfListing", form));
		tabFolderController.setcTabItemItem((CTabItem) FormUtils.get(
				"cTabItemItem", form));
		tabFolderController.setCompositeOfItems((Composite) FormUtils.get(
				"compositeOfItems", form));
		tabFolderController.setItemGroupForm((ItemGroupForm) FormUtils.get(
				"itemGroupForm", form));
		tabFolderController.setCompositeOfdelisting((Composite) FormUtils.get("compositeOfdelisting", form));
		tabFolderController.setCtabItemOfdelisting((CTabItem)FormUtils.get("ctabItemOfdelisting", form));

		tabFolderController.addListenter();
	}
};
