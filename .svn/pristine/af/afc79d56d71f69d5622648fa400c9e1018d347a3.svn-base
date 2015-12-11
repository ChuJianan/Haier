package com.haier.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class SendDialog extends Dialog {
	/**
	 * 使用时，必须实现onMyDialogInnerViewClickListener接口
	 * 
	 * @param context
	 * @param layoutResID
	 * @param mydialog 
	 * @param listener
	 */
	public SendDialog(Context context, int layoutResID, int mystyle,onDialogClickListener listener) {
		super(context,mystyle);
		setContentView(layoutResID);
		listener.onDialogViewClick(this, (android.view.View.OnClickListener) context);
	}
	/** 得到当前dialog弹出框的view */
	public View getCurrentView() {
		return this.getWindow().getDecorView();
	}
	public interface onDialogClickListener {
		/** 绑定监听 */
		public void onDialogViewClick(SendDialog senddialog, android.view.View.OnClickListener listener);
	}
}
