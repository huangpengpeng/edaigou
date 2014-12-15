package com.edaigou.action;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.entity.ItemFilters;
import com.edaigou.entity.PromotionItem;
import com.edaigou.form.MessageText;
import com.edaigou.form.widgets.TableUtils;
import com.edaigou.manager.ItemFiltersMng;
import com.edaigou.manager.PromotionItemMng;
import com.edaigou.resource.ImageUtils;

@Controller
public class ItemFiltersController {

	private Text textFilterOfItemId;
	private Text textFilterOfNick;
	private Button buttonOfFilters;
	private Table tableOfFilters;
	private Button buttonFilterOfRef;

	public void init() {

		buttonOfFilters.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String pNumIidValue = textFilterOfItemId.getText();
				String nickValue = textFilterOfNick.getText();
				if (StringUtils.isBlank(nickValue)) {
					MessageText.error("掌柜昵称不能为空");
					return;
				}
				Long pNumIid = StringUtils.isBlank(pNumIidValue)?null:Long.valueOf(pNumIidValue);
				manager.add(pNumIid, nickValue);
				textFilterOfItemId.setText("");
				textFilterOfNick.setText("");
				table();
			}
		});
		
		buttonFilterOfRef.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				table();
			}
		});

		table();
	}

	public void table() {
		java.util.List<ItemFilters> itemFilters = manager.query();
		TableUtils.removeAll(tableOfFilters);

		TableUtils.addColumn(tableOfFilters, "图片", 80);
		TableUtils.addColumn(tableOfFilters, "推广商品名称", 405);
		TableUtils.addColumn(tableOfFilters, "过滤店铺", 400);
		TableUtils.addColumn(tableOfFilters, "操作", 200);

		for (ItemFilters itemFilter : itemFilters) {
			TableItem tableItem = new TableItem(tableOfFilters, SWT.NONE);
			PromotionItem item = null;
			if (itemFilter.getpNumIid() != null) {
				item = promotionItemMng.getByNumIid(itemFilter.getpNumIid());
			}
			try {
				if(item!=null)
				TableUtils.addItemOfImage(tableItem,
						ImageUtils.base64StringToImg(item.getImageByte()), 80,
						0);
			} catch (IOException e) {
			}
			TableUtils.addItem(tableItem, item != null ? item.getTitle() : "",
					1);
			TableUtils.addItem(tableItem, itemFilter.getNick(), 2);

			Button deleteButton = new Button(tableOfFilters, SWT.NONE);
			deleteButton.setData(itemFilter);
			deleteButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					ItemFilters itemFilters = (ItemFilters) arg0.widget
							.getData();
					manager.delete(itemFilters.getId());
					table();
				}
			});
			deleteButton.setText("删除");
			TableUtils.addButton(tableItem, 3, deleteButton, 0, 200);
		}
	}

	public void setTextFilterOfItemId(Text textFilterOfItemId) {
		this.textFilterOfItemId = textFilterOfItemId;
	}

	public void setTextFilterOfNick(Text textFilterOfNick) {
		this.textFilterOfNick = textFilterOfNick;
	}

	public void setButtonOfFilters(Button buttonOfFilters) {
		this.buttonOfFilters = buttonOfFilters;
	}

	public void setTableOfFilters(Table tableOfFilters) {
		this.tableOfFilters = tableOfFilters;
	}
	
	public void setButtonFilterOfRef(Button buttonFilterOfRef) {
		this.buttonFilterOfRef = buttonFilterOfRef;
	}





	@Autowired
	private PromotionItemMng promotionItemMng;
	@Autowired
	private ItemFiltersMng manager;
}
