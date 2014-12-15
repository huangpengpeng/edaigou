package com.edaigou.action;

import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.entity.Shop;
import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.PageForm;
import com.edaigou.manager.ShopMng;

@Controller
public class ShopController {

	private ItemGroupForm itemGroupForm;
	private PageForm pageForm;

	public void init() {
		List<Shop> shops = manager.query();
		for (int i = 0; i < shops.size(); i++) {
			Shop shop = shops.get(i);
			itemGroupForm.getComboOfShop().add(shop.getNick());
			itemGroupForm.getComboOfShop().setData(i + "", shop.getId());
		}
		for (int i = 0; i < shops.size(); i++) {
			Shop shop = shops.get(i);
			pageForm.getComboOfsearchShop().add(shop.getNick());
			pageForm.getComboOfsearchShop().setData(i + "", shop.getId());
		}
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}



	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}




	@Autowired
	private ShopMng manager;
}
