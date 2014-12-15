package com.edaigou.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.util.WebUtils;
import com.edaigou.entity.Appliance;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.TableUtils;
import com.edaigou.manager.ApplianceMng;

@Controller
public class ItemAppController {

	private CTabItem tabItemOfapp;
	private Composite compositeOfapp;
	private Table tableOfapp;
	private Text textOfappkey;
	private Text textOfappsecret;
	private Text textOfnick;
	private Browser browser;
	private CTabFolder tabFolder;
	private Button buttonOfappcurl;

	public void init() {
		table();
	}

	private Appliance app=null;
	
	public void table() {
		java.util.List<Appliance> appliances = manager.query();
		TableUtils.removeAll(tableOfapp);

		TableUtils.addColumn(tableOfapp, "appkey", 80);
		TableUtils.addColumn(tableOfapp, "appSecret", 300);
		TableUtils.addColumn(tableOfapp, "sessionKey", 500);
		TableUtils.addColumn(tableOfapp, "掌柜昵称", 100);
		TableUtils.addColumn(tableOfapp, "操作", 100);

		for (Appliance appliance : appliances) {
			TableItem tableItem = new TableItem(tableOfapp, SWT.NONE);
			TableUtils.addItem(tableItem, appliance.getAppKey(), 0);
			TableUtils.addItem(tableItem, appliance.getAppSecret(), 1);
			TableUtils.addItem(tableItem, appliance.getSessionKey(), 2);
			TableUtils.addItem(tableItem, appliance.getNick(), 3);

			Button deleteButton = new Button(tableOfapp, SWT.NONE);
			deleteButton.setData(appliance);
			deleteButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					app = (Appliance) arg0.widget.getData();
					tabFolder.setSelection(1);
					browser.setUrl("http://container.api.taobao.com/container?appkey="
							+ app.getAppKey());
				}
			});
			deleteButton.setText("获取");
			TableUtils.addButton(tableItem, 4, deleteButton, 0, 100);
		}
	}

	public void addActionListenter() {
		buttonOfappcurl.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String url=browser.getUrl();
				String sessionKey=WebUtils.getParameters(url, "top_session");
				manager.update(app.getId(), sessionKey);
				table();
			}
		});
	}

	public void setTabItemOfapp(CTabItem tabItemOfapp) {
		this.tabItemOfapp = tabItemOfapp;
	}

	public void setCompositeOfapp(Composite compositeOfapp) {
		this.compositeOfapp = compositeOfapp;
	}

	public void setTableOfapp(Table tableOfapp) {
		this.tableOfapp = tableOfapp;
	}

	public void setTextOfappkey(Text textOfappkey) {
		this.textOfappkey = textOfappkey;
	}

	public void setTextOfappsecret(Text textOfappsecret) {
		this.textOfappsecret = textOfappsecret;
	}

	public void setTextOfnick(Text textOfnick) {
		this.textOfnick = textOfnick;
	}
	
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}


	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}


	public void setButtonOfappcurl(Button buttonOfappcurl) {
		this.buttonOfappcurl = buttonOfappcurl;
	}



	@Autowired
	private ApplianceMng manager;
}
