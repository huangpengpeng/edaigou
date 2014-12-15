package com.edaigou.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public abstract class MessageText {

	public static void error(String errorText) {
		Shell shell = FormUtils.get("shell",
				ApplicationContextUtils.getBean(MainForm.class));
		MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage(errorText == null ? "参数错误" : errorText);
		messageBox.open();
	}

}
