package com.haier.activity.forum;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.BaseActivity;
import com.haier.bean.Sscion;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AskMeActivity extends BaseActivity{

	@ViewInject(R.id.top_title)private TextView top_title;
	@ViewInject(R.id.webView)private WebView webView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		ViewUtils.inject(this);
		top_title.setText("问自己");
		String askSelfId=getIntent().getStringExtra("id");
		webView.loadUrl("http://27.223.14.58:8080/gamelearning/askSelf/getAskSelfById?sessionId="+
		Sscion.getSsid()+"&askSelfId="+askSelfId);
	}
}
