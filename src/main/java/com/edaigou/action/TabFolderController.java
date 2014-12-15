package com.edaigou.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.edaigou.form.widgets.ItemGroupForm;
import com.edaigou.form.widgets.PageForm;

@Controller
public class TabFolderController {

	private CTabItem tabItemOfListing;
	private CTabFolder tabFolder;
	private PageForm pageForm;
	private Composite compositeOfListing;
	private Composite compositeOfItems;
	private CTabItem cTabItemItem;
	private ItemGroupForm itemGroupForm;
	private Composite compositeOfdelisting;
	private CTabItem ctabItemOfdelisting;

	public void addListenter() {
		tabFolder.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Composite composite = null;
				if (tabFolder.getSelection().equals(cTabItemItem)) {
					composite = compositeOfItems;
				}
				if (tabFolder.getSelection().equals(tabItemOfListing)) {
					composite = compositeOfListing;
				}
				if(tabFolder.getSelection().equals(ctabItemOfdelisting)){
					composite = compositeOfdelisting;
				}
				if(composite==null){
					return;
				}
				pageForm.setComposite(composite);
				if(tabFolder.getSelectionIndex()==0){
					itemController.init(itemController.page==null?1:itemController.page.getPageNo());
				}
				if(tabFolder.getSelectionIndex()==3){
					itemListingController.init(itemListingController.page==null?1:itemListingController.page.getPageNo());
				}
				if(tabFolder.getSelectionIndex()==4){
					itemDelistingController.init(itemDelistingController.page==null?1:itemDelistingController.page.getPageNo());
				}
			}
		});
		if(tabFolder.getSelectionIndex()==0){
			itemController.init(itemController.page==null?1:itemController.page.getPageNo());
		}
		if(tabFolder.getSelectionIndex()==3){
			itemListingController.init(itemListingController.page==null?1:itemListingController.page.getPageNo());
		}
		if(tabFolder.getSelectionIndex()==4){
			itemListingController.init(itemDelistingController.page==null?1:itemDelistingController.page.getPageNo());
		}
	}


	public void setTabItemOfListing(CTabItem tabItemOfListing) {
		this.tabItemOfListing = tabItemOfListing;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public void setCompositeOfListing(Composite compositeOfListing) {
		this.compositeOfListing = compositeOfListing;
	}

	public void setCompositeOfItems(Composite compositeOfItems) {
		this.compositeOfItems = compositeOfItems;
	}

	public void setcTabItemItem(CTabItem cTabItemItem) {
		this.cTabItemItem = cTabItemItem;
	}

	public void setItemGroupForm(ItemGroupForm itemGroupForm) {
		this.itemGroupForm = itemGroupForm;
	}
	
	
	public void setCompositeOfdelisting(Composite compositeOfdelisting) {
		this.compositeOfdelisting = compositeOfdelisting;
	}

	public void setCtabItemOfdelisting(CTabItem ctabItemOfdelisting) {
		this.ctabItemOfdelisting = ctabItemOfdelisting;
	}


	@Autowired
	private ItemDelistingController itemDelistingController;
	@Autowired
	private ItemListingController itemListingController;
	@Autowired
	private ItemController itemController;
}
