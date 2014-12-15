package com.edaigou.form.widgets;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public abstract class TableUtils {

	public static void removeAll(Table table) {
		removeControlAndEditorFormUserTable(table);
		table.removeAll();
	}

	/**
	 * 增加列
	 * 
	 * @param table
	 * @param text
	 * @param width
	 */
	public static void addColumn(Table table, String text, Integer width) {
		TableColumn[] cs = table.getColumns();
		for (TableColumn tableColumn : cs) {
			if (StringUtils.equals(tableColumn.getText().trim(), text)) {
				return;
			}
		}
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(width);
		tblclmnNewColumn.setText(text);
		tblclmnNewColumn.setAlignment(SWT.CENTER);
	}

	public static void addItem(TableItem tableItem, String text, Integer column) {
		tableItem.setText(column, text);
	}

	public static void addItemOfImage(TableItem tableItem, Image image,
			Integer height, int column) {
		tableItem.setImage(column, image);
	}

	public static void addButton(TableItem tableItem, int column,
			Button button, int arg0, int width) {
		TableEditor editor = new TableEditor(tableItem.getParent());
		editor.horizontalAlignment = arg0;
		editor.minimumWidth = width;
		editor.setEditor(button, tableItem, column);
		for (int i = 0; i < 3; i++) {
			if (tableItem.getData(i + "e") == null) {
				tableItem.setData(i + "e", editor);
				break;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (tableItem.getData(i + "b") == null) {
				tableItem.setData(i + "b", button);
				break;
			}
		}
	}

	public static void removeControlAndEditorFormUserTable(Table table) {
		TableItem[] items = table.getItems();
		for (TableItem item : items) {
			for (int i = 0; i < 3; i++) {
				TableEditor ctl = (TableEditor) item.getData(i + "e");
				if (ctl != null) {
					ctl.dispose();
				}
				Button button = (Button) item.getData(i + "b");
				if (button != null) {
					button.dispose();
				}
			}
		}
	}
}
