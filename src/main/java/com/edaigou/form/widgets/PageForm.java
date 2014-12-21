package com.edaigou.form.widgets;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.common.jdbc.page.Pagination;
import com.edaigou.entity.ItemErrors.ItemErrorsType;

public class PageForm {
	
	private CTabFolder tabFolder;
	private Combo comboOfItemErrors;
	private Combo comboOfsearchShop;
	private Text searchText;
	private Button searchButton;
	private Button buttonOfRef;
	
	public static final String CREATE_ITEM = "新商品";
	public static final String LiSTING_ITEM = "已上架";
	public static final String DELISTING_ITEM = "已下架";

	public PageForm(Composite composite,CTabFolder tabFolder) {
		this.composite = composite;
		this.tabFolder=tabFolder;
	}

	private Button buttonOfUp;
	private CLabel showTotalPageAndCount;
	private Button buttonOfNext;
	private Composite composite;

	public void open() {
		buttonOfUp = new Button(composite, SWT.NONE);
		buttonOfUp.setBounds(10, 7, 41, 22);
		buttonOfUp.setText("上一页");

		showTotalPageAndCount = new CLabel(composite, SWT.NONE);
		showTotalPageAndCount.setAlignment(SWT.CENTER);
		showTotalPageAndCount.setBounds(57, 11, 60, 18);
		showTotalPageAndCount.setText("0/0");

		buttonOfNext = new Button(composite, SWT.NONE);
		buttonOfNext.setBounds(124, 7, 41, 22);
		buttonOfNext.setText("下一页");
		
		comboOfItemErrors = new Combo(composite, SWT.NONE);
		comboOfItemErrors.setBounds(532, 9, 86, 20);
		comboOfItemErrors.add("");
		comboOfItemErrors.add(ItemErrorsType.低价.toString());
		comboOfItemErrors.add(ItemErrorsType.低格错误.toString());
		comboOfItemErrors.add(ItemErrorsType.利差.toString());
		comboOfItemErrors.add(ItemErrorsType.实售价不符.toString());
		comboOfItemErrors.add(ItemErrorsType.详情错误.toString());
		comboOfItemErrors.add(ItemErrorsType.淘宝客变动.toString());
		comboOfItemErrors.add(ItemErrorsType.天猫下架.toString());
		comboOfItemErrors.add(ItemErrorsType.重复编号.toString());
		comboOfItemErrors.add(ItemErrorsType.标题错误.toString());
		comboOfItemErrors.add(ItemErrorsType.SKU变动.toString());
		
		comboOfsearchShop = new Combo(composite, SWT.NONE);
		comboOfsearchShop.setBounds(625, 9, 81, 20);

		searchText = new Text(composite, SWT.BORDER);
		searchText.setBounds(712, 9, 274, 18);

		searchButton = new Button(composite, SWT.NONE);
		searchButton.setBounds(992, 7, 52, 22);
		searchButton.setText("查询");

		buttonOfRef = new Button(composite, SWT.NONE);
		buttonOfRef.setBounds(1050, 7, 34, 22);
		buttonOfRef.setText("刷新");
		
		buttonOfUp.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String name = tabFolder.getSelection().getText();
				for (Entry<String, Listener> entry : ups.entrySet()) {
					String key = entry.getKey();
					Listener listener = entry.getValue();
					if (StringUtils.equals(name, key)) {
						listener.handleEvent(arg0);
					}
				}
			}
		});
		
		buttonOfNext.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String name = tabFolder.getSelection().getText();
				for (Entry<String, Listener> entry : nexts.entrySet()) {
					String key = entry.getKey();
					Listener listener = entry.getValue();
					if (StringUtils.equals(name, key)) {
						listener.handleEvent(arg0);
					}
				}
			}
		});
		
		searchButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String name = tabFolder.getSelection().getText();
				for (Entry<String, Listener> entry : searchs.entrySet()) {
					String key = entry.getKey();
					Listener listener = entry.getValue();
					if (StringUtils.equals(name, key)) {
						listener.handleEvent(arg0);
					}
				}
			}
		});
	}

	public void showPage(Pagination page) {
		if (page.isFirstPage()) {
			buttonOfUp.setEnabled(false);
		}else{
			buttonOfUp.setEnabled(true);
		}
		if (page.isLastPage()) {
			buttonOfNext.setEnabled(false);
		}else{
			buttonOfNext.setEnabled(true);
		}
		showTotalPageAndCount.setText(page.getPageNo() + "/"
				+ page.getTotalPage() + "/" + page.getTotalCount());
	}
	

	public void addUpListener(String key,Listener listener) {
		ups.put(key, listener);
	}

	public void addNextListener(String key,Listener listener) {
		nexts.put(key, listener);
	}
	
	public void addSearchListener(String key,Listener listener) {
		searchs.put(key, listener);
	}
	
	public void setComposite(Composite composite){
		buttonOfUp.setParent(composite);
		showTotalPageAndCount.setParent(composite);
		buttonOfNext.setParent(composite);
		comboOfItemErrors.setParent(composite);
		comboOfsearchShop.setParent(composite);
		buttonOfRef.setParent(composite);
		searchText.setParent(composite);
		searchButton.setParent(composite);
	}
	
	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	
	public Combo getComboOfItemErrors() {
		return comboOfItemErrors;
	}

	public void setComboOfItemErrors(Combo comboOfItemErrors) {
		this.comboOfItemErrors = comboOfItemErrors;
	}

	public Combo getComboOfsearchShop() {
		return comboOfsearchShop;
	}

	public void setComboOfsearchShop(Combo comboOfsearchShop) {
		this.comboOfsearchShop = comboOfsearchShop;
	}

	public Text getSearchText() {
		return searchText;
	}

	public void setSearchText(Text searchText) {
		this.searchText = searchText;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public Button getButtonOfRef() {
		return buttonOfRef;
	}

	public void setButtonOfRef(Button buttonOfRef) {
		this.buttonOfRef = buttonOfRef;
	}

	public Button getButtonOfUp() {
		return buttonOfUp;
	}

	public void setButtonOfUp(Button buttonOfUp) {
		this.buttonOfUp = buttonOfUp;
	}

	public CLabel getShowTotalPageAndCount() {
		return showTotalPageAndCount;
	}

	public void setShowTotalPageAndCount(CLabel showTotalPageAndCount) {
		this.showTotalPageAndCount = showTotalPageAndCount;
	}

	public Button getButtonOfNext() {
		return buttonOfNext;
	}

	public void setButtonOfNext(Button buttonOfNext) {
		this.buttonOfNext = buttonOfNext;
	}

	public Composite getComposite() {
		return composite;
	}

	private Map<String, Listener> searchs = new HashMap<String, Listener>();
	private Map<String, Listener> nexts = new HashMap<String, Listener>();
	private Map<String, Listener> ups = new HashMap<String, Listener>();
}
