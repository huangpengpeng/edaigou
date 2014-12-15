package com.edaigou.form;

import java.awt.Toolkit;

import org.eclipse.swt.widgets.Shell;

public class WindowCenter {

	
	public void location(Shell shell){
		// 定位窗口对象的坐标
				shell.setLocation(
						((Toolkit.getDefaultToolkit().getScreenSize().width - shell
								.getBounds().width) / 2),
						((Toolkit.getDefaultToolkit().getScreenSize().height - 35 - shell
								.getBounds().height) / 2));
	}
}
