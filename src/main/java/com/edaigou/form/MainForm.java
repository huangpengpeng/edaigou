package com.edaigou.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.PageForm;

@Component
public class MainForm {

	protected Shell shell;
	private Group groupOfItem;
	private Composite compositeOfItems;
	
	private Table table;
	private Button buttonOfShowSaveItem;
	private CTabItem cTabItemItem;
	private PageForm pageForm;
	private ItemGroupForm itemGroupForm;
	private Button buttonOfExport;
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
	private Text browserTextOfPageChange;
	private Button browserButtonOfrefresh;
	private Button browserButtonOfDeleteItem;
	private Button browserButtonOfAuto;
	private CTabItem tabItem;
	private CTabFolder tabFolder;
	private CTabItem ctabItemOfFilters;
	private Composite compositeOfFilters;
	private Text textFilterOfItemId;
	private Text textFilterOfNick;
	private Button buttonOfFilters;
	private Table tableOfFilters;
	private Button buttonFilterOfRef;
	private Button buttonOfInsertDetail;
	private Button createItemOfShangJia;
	private CTabItem tabItemOfListing ;
	private Composite compositeOfListing ;
	private Table tableOfListing;
	private Button buttonOfListingJk;
	private Button buttonOfPlEditPrice;
	private Button checkboxOfItemExists;
	private Button browserCheckButtonOfItemExists;
	private Combo browserComboOfItemErrors;
	private CTabItem ctabItemOfdelisting;
	private Composite compositeOfdelisting;
	private Table tableOfdelisting;
	
	private CTabItem tabItemOfapp;
	private Composite compositeOfapp;
	private Table tableOfapp;
	private Text textOfappkey;
	private Text textOfappsecret;
	private Text textOfnick;
	private Button buttonOfappcurl;
	private Button buttonOfkutongitem;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContextUtils.newInstance();
			MainForm window = ApplicationContextUtils.getBean(MainForm.class);
			window.open();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		new WindowCenter().location(shell);

		tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setBounds(0, 106, 1094, 569);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabFolder.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if ("新商品".equals(tabFolder.getSelection().getText())) {
					browserTextOfMinPrice.setText("");
				}
			}
		});

		cTabItemItem = new CTabItem(tabFolder, SWT.NONE);
		cTabItemItem.setText("新商品");
		tabFolder.setSelection(0);

		compositeOfItems = new Composite(tabFolder, SWT.NONE);
		cTabItemItem.setControl(compositeOfItems);

		table = new Table(compositeOfItems, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 35, 1094, 505);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		buttonOfShowSaveItem = new Button(compositeOfItems, SWT.NONE);
		buttonOfShowSaveItem.setBounds(185, 7, 60, 22);
		buttonOfShowSaveItem.setText("创建商品");

		pageForm = new PageForm(compositeOfItems,tabFolder);

		buttonOfExport = new Button(compositeOfItems, SWT.NONE);
		buttonOfExport.setBounds(251, 7, 52, 22);
		buttonOfExport.setText("导出链接");

		buttonOfInsertDetail = new Button(compositeOfItems, SWT.NONE);
		buttonOfInsertDetail.setBounds(309, 7, 52, 22);
		buttonOfInsertDetail.setText("插入详情");
		
		createItemOfShangJia = new Button(compositeOfItems, SWT.NONE);
		createItemOfShangJia.setBounds(367, 7, 34, 22);
		createItemOfShangJia.setText("上架");
		
		buttonOfPlEditPrice = new Button(compositeOfItems, SWT.NONE);
		buttonOfPlEditPrice.setBounds(405, 7, 42, 22);
		buttonOfPlEditPrice.setText("修低价");
		
		checkboxOfItemExists = new Button(compositeOfItems, SWT.CHECK);
		checkboxOfItemExists.setBounds(453, 10, 52, 16);
		checkboxOfItemExists.setText("商品");

		tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("浏览器");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);

		browser = new Browser(composite, SWT.NONE);
		browser.setBounds(0, 33, 1088, 507);

		browserComboOfStatus = new Combo(composite, SWT.NONE);
		browserComboOfStatus.setBounds(176, 7, 63, 20);

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(139, 10, 24, 17);
		label.setText("状态");

		browserButtonOfOpertion = new Button(composite, SWT.NONE);
		browserButtonOfOpertion.setEnabled(false);
		browserButtonOfOpertion.setBounds(1006, 5, 72, 22);
		browserButtonOfOpertion.setText("操作");

		browserButtonOfUp = new Button(composite, SWT.NONE);
		browserButtonOfUp.setEnabled(false);
		browserButtonOfUp.setBounds(843, 5, 72, 22);
		browserButtonOfUp.setText("上一条");

		browserButtonOfNext = new Button(composite, SWT.NONE);
		browserButtonOfNext.setEnabled(false);
		browserButtonOfNext.setBounds(924, 5, 72, 22);
		browserButtonOfNext.setText("下一条");

		browserButtonOfMinPrice = new Button(composite, SWT.RADIO);
		browserButtonOfMinPrice.setBounds(639, 11, 41, 16);
		browserButtonOfMinPrice.setText("低价");

		browserButtonOfDetailPage = new Button(composite, SWT.RADIO);
		browserButtonOfDetailPage.setBounds(780, 11, 57, 16);
		browserButtonOfDetailPage.setText("详页");

		browserButtonOfEditPrice = new Button(composite, SWT.RADIO);
		browserButtonOfEditPrice.setBounds(733, 11, 41, 16);
		browserButtonOfEditPrice.setText("修价");

		browserButtonOfCmlibUrl = new Button(composite, SWT.RADIO);
		browserButtonOfCmlibUrl.setBounds(686, 11, 41, 16);
		browserButtonOfCmlibUrl.setText("抓地");

		browserComboOfShop = new Combo(composite, SWT.NONE);
		browserComboOfShop.setBounds(47, 7, 86, 20);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 24, 22);
		lblNewLabel.setText("店铺");

		browserCLableOfShowPage = new CLabel(composite, SWT.NONE);
		browserCLableOfShowPage.setAlignment(SWT.CENTER);
		browserCLableOfShowPage.setBounds(365, 9, 41, 18);
		browserCLableOfShowPage.setText("0/0");

		browserTextOfMinPrice = new Text(composite, SWT.BORDER);
		browserTextOfMinPrice.setBounds(592, 9, 41, 18);

		browserButtonOfrefresh = new Button(composite, SWT.NONE);
		browserButtonOfrefresh.setBounds(438, 7, 31, 22);
		browserButtonOfrefresh.setText("刷新");

		browserButtonOfDeleteItem = new Button(composite, SWT.NONE);
		browserButtonOfDeleteItem.setBounds(470, 7, 31, 22);
		browserButtonOfDeleteItem.setText("删除");

		browserTextOfPageChange = new Text(composite, SWT.BORDER);
		browserTextOfPageChange.setBounds(412, 9, 24, 18);

		browserButtonOfAuto = new Button(composite, SWT.NONE);
		browserButtonOfAuto.setBounds(502, 7, 31, 22);
		browserButtonOfAuto.setText("自动");
		pageForm.open();

		itemGroupForm = new ItemGroupForm(groupOfItem, browser, tabFolder);
		
		browserCheckButtonOfItemExists = new Button(composite, SWT.CHECK);
		browserCheckButtonOfItemExists.setBounds(320, 10, 41, 16);
		browserCheckButtonOfItemExists.setText("商品");
		
		browserComboOfItemErrors = new Combo(composite, SWT.NONE);
		browserComboOfItemErrors.setBounds(242, 7, 72, 25);
		browserComboOfItemErrors.add("");
		browserComboOfItemErrors.add(ItemErrorsType.低价.toString());
		browserComboOfItemErrors.add(ItemErrorsType.低格错误.toString());
		browserComboOfItemErrors.add(ItemErrorsType.利差.toString());
		browserComboOfItemErrors.add(ItemErrorsType.实售价不符.toString());
		browserComboOfItemErrors.add(ItemErrorsType.详情错误.toString());
		browserComboOfItemErrors.add(ItemErrorsType.淘宝客变动.toString());
		browserComboOfItemErrors.add(ItemErrorsType.天猫下架.toString());
		browserComboOfItemErrors.add(ItemErrorsType.重复编号.toString());

		ctabItemOfFilters = new CTabItem(tabFolder, SWT.NONE);
		ctabItemOfFilters.setText("商品过滤");

		compositeOfFilters = new Composite(tabFolder, SWT.NONE);
		ctabItemOfFilters.setControl(compositeOfFilters);

		textFilterOfItemId = new Text(compositeOfFilters, SWT.BORDER);
		textFilterOfItemId.setBounds(70, 7, 142, 18);

		Label lblNewLabel_1 = new Label(compositeOfFilters, SWT.NONE);
		lblNewLabel_1.setBounds(10, 10, 54, 12);
		lblNewLabel_1.setText("推广商品：");

		Label lblNewLabel_2 = new Label(compositeOfFilters, SWT.NONE);
		lblNewLabel_2.setBounds(218, 10, 54, 12);
		lblNewLabel_2.setText("掌柜昵称：");

		textFilterOfNick = new Text(compositeOfFilters, SWT.BORDER);
		textFilterOfNick.setBounds(278, 7, 142, 18);

		buttonOfFilters = new Button(compositeOfFilters, SWT.NONE);
		buttonOfFilters.setBounds(426, 5, 72, 22);
		buttonOfFilters.setText("确认增加");

		tableOfFilters = new Table(compositeOfFilters, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableOfFilters.setBounds(0, 31, 1088, 509);
		tableOfFilters.setHeaderVisible(true);
		tableOfFilters.setLinesVisible(true);

		buttonFilterOfRef = new Button(compositeOfFilters, SWT.NONE);
		buttonFilterOfRef.setBounds(1024, 5, 54, 22);
		buttonFilterOfRef.setText("刷新");
		
		 tabItemOfListing = new CTabItem(tabFolder, SWT.NONE);
		tabItemOfListing.setText("已上架");
		
		compositeOfListing = new Composite(tabFolder, SWT.NONE);
		tabItemOfListing.setControl(compositeOfListing);
		
		tableOfListing = new Table(compositeOfListing, SWT.BORDER | SWT.FULL_SELECTION);
		tableOfListing.setBounds(0, 35, 1094, 505);
		tableOfListing.setHeaderVisible(true);
		tableOfListing.setLinesVisible(true);
		
		buttonOfListingJk = new Button(compositeOfListing, SWT.NONE);
		buttonOfListingJk.setBounds(192, 7, 46, 22);
		buttonOfListingJk.setText("监控");
		
		
		buttonOfkutongitem = new Button(compositeOfListing, SWT.NONE);
		buttonOfkutongitem.setBounds(245, 7, 60, 22);
		buttonOfkutongitem.setText("同步库存");
		
		ctabItemOfdelisting = new CTabItem(tabFolder, SWT.NONE);
		ctabItemOfdelisting.setText("已下架");
		
		compositeOfdelisting = new Composite(tabFolder, SWT.NONE);
		ctabItemOfdelisting.setControl(compositeOfdelisting);
		
		tableOfdelisting = new Table(compositeOfdelisting, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableOfdelisting.setBounds(0, 31, 1088, 509);
		tableOfdelisting.setHeaderVisible(true);
		tableOfdelisting.setLinesVisible(true);
		
		tabItemOfapp = new CTabItem(tabFolder, SWT.NONE);
		tabItemOfapp.setText("店铺授权");
		
		compositeOfapp = new Composite(tabFolder, SWT.NONE);
		tabItemOfapp.setControl(compositeOfapp);
		
		tableOfapp = new Table(compositeOfapp, SWT.BORDER | SWT.FULL_SELECTION);
		tableOfapp.setBounds(0, 34, 1088, 506);
		tableOfapp.setHeaderVisible(true);
		tableOfapp.setLinesVisible(true);
		
		textOfappkey = new Text(compositeOfapp, SWT.BORDER);
		textOfappkey.setBounds(69, 5, 122, 23);
		
		Label lblNewLabel_3 = new Label(compositeOfapp, SWT.NONE);
		lblNewLabel_3.setBounds(10, 8, 53, 23);
		lblNewLabel_3.setText("appkey");
		
		Label lblNewLabel_4 = new Label(compositeOfapp, SWT.NONE);
		lblNewLabel_4.setBounds(206, 8, 71, 17);
		lblNewLabel_4.setText("appSecret");
		
		textOfappsecret = new Text(compositeOfapp, SWT.BORDER);
		textOfappsecret.setBounds(283, 5, 224, 23);
		
		Label lblNewLabel_5 = new Label(compositeOfapp, SWT.NONE);
		lblNewLabel_5.setBounds(526, 8, 53, 17);
		lblNewLabel_5.setText("掌柜名称");
		
		textOfnick = new Text(compositeOfapp, SWT.BORDER);
		textOfnick.setBounds(585, 5, 150, 23);
		
		Button btnNewButton = new Button(compositeOfapp, SWT.NONE);
		btnNewButton.setBounds(769, 3, 80, 27);
		btnNewButton.setText("保存");
		
		buttonOfappcurl = new Button(compositeOfapp, SWT.NONE);
		buttonOfappcurl.setBounds(998, 3, 80, 27);
		buttonOfappcurl.setText("抓取");
		
		itemGroupForm.open();

		ApplicationContextUtils.publishEvent();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.BORDER | SWT.MIN);
		shell.setSize(1100, 700);
		shell.setText("易代购");

		groupOfItem = new Group(shell, SWT.NONE);
		groupOfItem.setBounds(0, 0, 1094, 100);
		groupOfItem.setText(ItemGroupForm.ADD_ITEM);
	}

	private final static org.slf4j.Logger LOG = LoggerFactory
			.getLogger(MainForm.class);
}
