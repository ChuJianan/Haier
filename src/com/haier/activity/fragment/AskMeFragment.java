package com.haier.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.haier.R;
import com.haier.bean.Sscion;
import com.haier.fragment.BaseFragment;
import com.haier.widgets.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AskMeFragment extends BaseFragment {

	@ViewInject(R.id.newslistview)
	private PullToRefreshListView news_list;
	@ViewInject(R.id.back)
	private Button back;
	@ViewInject(R.id.webView)private WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_askme, null);
		ViewUtils.inject(this, view);
		loaDatas();
		return view;
	}


	private void loaDatas() {
		// TODO Auto-generated method stub
	webView.loadUrl("http://42.96.134.201:8080/gamelearning/askSelf/getAskSelfPublish?sessionId="+Sscion.getSsid());
	}
}
