package com.edaigou.form.widgets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.edaigou.entity.Item.ItemPType;
import com.edaigou.entity.ItemErrors.ItemErrorsType;
import com.edaigou.form.MainForm;
import com.edaigou.resource.ImageUtils;

public class ItemGroupForm {

	public static final String ADD_ITEM = "增加商品";
	public static final String EDIT_ITEM = "编辑商品";
	public static final String SAVE_EDIT_ITEM = "保存编辑";
	public static final String SAVE_ADD_ITEM = "保存增加";

	public ItemGroupForm(Group groupOfItem, Browser browser,CTabFolder tabFolder) {
		this.groupOfItem = groupOfItem;
		this.browser = browser;
		this.tabFolder=tabFolder;
	}

	private CTabFolder tabFolder;
	private Button buttonOfGetItem;
	private Group groupOfItem;
	private Browser browser;
	private Button buttonOfSaveItem;
	private Button buttonOfSearchItem;
	private Text url;
	private Text title;
	private Text originalPrice;
	private Text salePrice;
	private Text commissionRate;
	private Text commissionMoney;
	private Text subsidyRate;
	private Text realSalesPrice;
	private Text realSaleDiscount;
	private Text realProfit;
	private Text sumCommissionRate;
	private Text sumCOmmissionMoney;
	private Text subsidy;
	private Button image;
	private Combo comboOfShop;
	private Combo comboOfPType;
	private Text sNumIid;
	private Label lableOfSNumIid;

	private Map<String, Listener> listenerMap = new HashMap<String, Listener>();

	public void add(String name, Listener listener) {
		listenerMap.put(name, listener);
	}

	public void clear(boolean all) {
		url.setText("");
		url.setToolTipText("");
		sNumIid.setText("");
		title.setText("");
		originalPrice.setText("");
		salePrice.setText("");
		commissionRate.setText("");
		commissionMoney.setText("");
		realSalesPrice.setText("");
		realSaleDiscount.setText("");
		realProfit.setText("");
		sumCommissionRate.setText("");
		sumCOmmissionMoney.setText("");
		subsidy.setText("");
		if (all)
			comboOfShop.setText("");
		image.setImage(SWTResourceManager.getImage(MainForm.class,
				"/com/edaigou/resource/default.jpg"));
	}

	public void write(Map map) throws MalformedURLException {

		getComboOfShop().setText(map.get("nick").toString());
		getCommissionMoney().setText(map.get("commissionMoney").toString());
		getCommissionRate().setText(map.get("commissionRate").toString());
		try {
			getImage()
					.setImage(
							ImageUtils.base64StringToImg((String) map
									.get("imageByte")));
		} catch (IOException e) {
		}
		
		getOriginalPrice().setText(map.get("originalPrice").toString());
		getRealProfit().setText(map.get("realProfit").toString());
		getRealSaleDiscount().setText(map.get("realSaleDiscount").toString());
		getRealSalesPrice().setText(String.valueOf(map.get("realSalesPrice")));
		getSalePrice().setText(String.valueOf(map.get("salePrice")));
		getSubsidy().setText(String.valueOf(map.get("subsidy")));
		getSubsidyRate().setText(String.valueOf(map.get("subsidyRate")));
		getSumCOmmissionMoney().setText(String.valueOf(map.get("sumCOmmissionMoney")));
		getSumCommissionRate().setText(map.get("sumCommissionRate").toString());
		getTitle().setText(map.get("title").toString());
		getTitle().setData(map);
		getUrl().setText(map.get("url").toString());
		getUrl().setToolTipText(map.get("pNumIid").toString());
		getsNumIid()
				.setText(
						map.get("sNumIid") == null ? "" : map.get("sNumIid")
								.toString());
		getComboOfPType().setText((String)map.get("pType"));
	}

	public void open() {

		image = new Button(groupOfItem, SWT.NONE);
		image.setImage(SWTResourceManager.getImage(MainForm.class,
				"/com/edaigou/resource/default.jpg"));
		image.setBounds(10, 16, 80, 80);

		url = new Text(groupOfItem, SWT.BORDER);
		url.setBounds(638, 13, 368, 18);

		Label lblNewLabel = new Label(groupOfItem, SWT.NONE);
		lblNewLabel.setBounds(569, 16, 54, 12);
		lblNewLabel.setText("商品链接");

		buttonOfGetItem = new Button(groupOfItem, SWT.NONE);
		buttonOfGetItem.setBounds(1012, 11, 72, 22);
		buttonOfGetItem.setText("抓取商品");

		title = new Text(groupOfItem, SWT.BORDER);
		title.setBounds(365, 13, 184, 18);

		Label label = new Label(groupOfItem, SWT.NONE);
		label.setBounds(119, 47, 54, 12);
		label.setText("原始价格");

		originalPrice = new Text(groupOfItem, SWT.BORDER);
		originalPrice.setBounds(175, 44, 70, 18);

		Label lblNewLabel_2 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_2.setBounds(262, 47, 54, 12);
		lblNewLabel_2.setText("原销价格");

		salePrice = new Text(groupOfItem, SWT.BORDER);
		salePrice.setBounds(322, 44, 70, 18);

		Label lblNewLabel_3 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_3.setBounds(403, 47, 48, 12);
		lblNewLabel_3.setText("提成比例");

		commissionRate = new Text(groupOfItem, SWT.BORDER);
		commissionRate.setBounds(478, 44, 70, 18);

		Label lblNewLabel_4 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_4.setBounds(569, 47, 54, 12);
		lblNewLabel_4.setText("提成金额");

		commissionMoney = new Text(groupOfItem, SWT.BORDER);
		commissionMoney.setBounds(638, 44, 70, 18);

		Label lblNewLabel_5 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_5.setBounds(731, 47, 54, 12);
		lblNewLabel_5.setText("天猫补贴");

		subsidyRate = new Text(groupOfItem, SWT.BORDER);
		subsidyRate.setBounds(804, 44, 70, 18);
		subsidyRate.setText("5");

		Label lblNewLabel_6 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_6.setBounds(119, 78, 54, 12);
		lblNewLabel_6.setText("实际售价");

		realSalesPrice = new Text(groupOfItem, SWT.BORDER);
		realSalesPrice.setBounds(175, 75, 70, 18);
		realSalesPrice
				.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		Label lblNewLabel_7 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_7.setBounds(262, 78, 54, 12);
		lblNewLabel_7.setText("实际折扣");

		realSaleDiscount = new Text(groupOfItem, SWT.BORDER);
		realSaleDiscount.setBounds(322, 75, 70, 18);

		Label lblNewLabel_8 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_8.setBounds(403, 78, 54, 12);
		lblNewLabel_8.setText("实际利润");

		realProfit = new Text(groupOfItem, SWT.BORDER);
		realProfit.setBounds(478, 75, 70, 18);

		Label lblNewLabel_9 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_9.setBounds(731, 78, 54, 12);
		lblNewLabel_9.setText("合计比例");

		sumCommissionRate = new Text(groupOfItem, SWT.BORDER);
		sumCommissionRate.setBounds(804, 75, 70, 18);

		lableOfSNumIid = new Label(groupOfItem, SWT.NONE);
		lableOfSNumIid.setBounds(891, 78, 54, 12);
		lableOfSNumIid.setText("实际编号");

		sNumIid = new Text(groupOfItem, SWT.BORDER);
		sNumIid.setBounds(952, 75, 54, 18);

		buttonOfSaveItem = new Button(groupOfItem, SWT.NONE);
		buttonOfSaveItem.setBounds(1012, 73, 72, 22);
		buttonOfSaveItem.setText(SAVE_ADD_ITEM);

		buttonOfSearchItem = new Button(groupOfItem, SWT.NONE);
		buttonOfSearchItem.setBounds(1012, 42, 72, 22);
		buttonOfSearchItem.setText("浏览查询");

		Label lblNewLabel_10 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_10.setBounds(569, 78, 54, 12);
		lblNewLabel_10.setText("合计佣金");

		sumCOmmissionMoney = new Text(groupOfItem, SWT.BORDER);
		sumCOmmissionMoney.setBounds(638, 75, 70, 18);

		Label lblNewLabel_11 = new Label(groupOfItem, SWT.NONE);
		lblNewLabel_11.setBounds(891, 47, 54, 12);
		lblNewLabel_11.setText("补贴金额");

		subsidy = new Text(groupOfItem, SWT.BORDER);
		subsidy.setBounds(952, 44, 54, 18);

		comboOfShop = new Combo(groupOfItem, SWT.NONE);
		comboOfShop.setBounds(120, 13, 100, 20);

		comboOfPType = new Combo(groupOfItem, SWT.NONE);
		comboOfPType.setBounds(245, 13, 100, 20);
		comboOfPType.add(ItemPType.普通.toString());
		comboOfPType.add(ItemPType.标题不一致.toString());
		comboOfPType.add(ItemPType.高级淘宝客.toString());

		buttonOfSaveItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String name = getGroupOfItem().getText();
				for (Entry<String, Listener> entry : listenerMap.entrySet()) {
					String key = entry.getKey();
					Listener listener = entry.getValue();
					if (StringUtils.equals(name, key)) {
						listener.handleEvent(arg0);
					}
				}
			}
		});

		image.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String urlValue = url.getText();
				if (StringUtils.isNotBlank(urlValue)) {
					browser.setUrl(urlValue);
					tabFolder.setSelection(1);
				}
			}
		});
	}

	public Button getButtonOfGetItem() {
		return buttonOfGetItem;
	}

	public void setButtonOfGetItem(Button buttonOfGetItem) {
		this.buttonOfGetItem = buttonOfGetItem;
	}

	public Group getGroupOfItem() {
		return groupOfItem;
	}

	public void setGroupOfItem(Group groupOfItem) {
		this.groupOfItem = groupOfItem;
	}

	public Button getButtonOfSaveItem() {
		return buttonOfSaveItem;
	}

	public void setButtonOfSaveItem(Button buttonOfSaveItem) {
		this.buttonOfSaveItem = buttonOfSaveItem;
	}

	public Text getUrl() {
		return url;
	}

	public void setUrl(Text url) {
		this.url = url;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public Text getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Text originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Text getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Text salePrice) {
		this.salePrice = salePrice;
	}

	public Text getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Text commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Text getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(Text commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public Text getSubsidyRate() {
		return subsidyRate;
	}

	public void setSubsidyRate(Text subsidyRate) {
		this.subsidyRate = subsidyRate;
	}

	public Text getRealSalesPrice() {
		return realSalesPrice;
	}

	public void setRealSalesPrice(Text realSalesPrice) {
		this.realSalesPrice = realSalesPrice;
	}

	public Text getRealSaleDiscount() {
		return realSaleDiscount;
	}

	public void setRealSaleDiscount(Text realSaleDiscount) {
		this.realSaleDiscount = realSaleDiscount;
	}

	public Text getRealProfit() {
		return realProfit;
	}

	public void setRealProfit(Text realProfit) {
		this.realProfit = realProfit;
	}

	public Text getSumCommissionRate() {
		return sumCommissionRate;
	}

	public void setSumCommissionRate(Text sumCommissionRate) {
		this.sumCommissionRate = sumCommissionRate;
	}

	public Text getSumCOmmissionMoney() {
		return sumCOmmissionMoney;
	}

	public void setSumCOmmissionMoney(Text sumCOmmissionMoney) {
		this.sumCOmmissionMoney = sumCOmmissionMoney;
	}

	public Text getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Text subsidy) {
		this.subsidy = subsidy;
	}

	public Button getImage() {
		return image;
	}

	public void setImage(Button image) {
		this.image = image;
	}

	public Combo getComboOfShop() {
		return comboOfShop;
	}

	public void setComboOfShop(Combo comboOfShop) {
		this.comboOfShop = comboOfShop;
	}

	public Text getsNumIid() {
		return sNumIid;
	}

	public void setsNumIid(Text sNumIid) {
		this.sNumIid = sNumIid;
	}

	public Label getLableOfSNumIid() {
		return lableOfSNumIid;
	}

	public void setLableOfSNumIid(Label lableOfSNumIid) {
		this.lableOfSNumIid = lableOfSNumIid;
	}

	public Button getButtonOfSearchItem() {
		return buttonOfSearchItem;
	}

	public void setButtonOfSearchItem(Button buttonOfSearchItem) {
		this.buttonOfSearchItem = buttonOfSearchItem;
	}

	public Combo getComboOfPType() {
		return comboOfPType;
	}

	public void setComboOfPType(Combo comboOfPType) {
		this.comboOfPType = comboOfPType;
	}

	
}
