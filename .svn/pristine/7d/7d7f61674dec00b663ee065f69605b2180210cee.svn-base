package com.haier.fragment;

import com.haier.R;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class BrowserFragment extends BaseFragment {
	@ViewInject(R.id.webview)
	private WebView mWebView;
	
	private String mUrl;
	
	/**
	 * 获得一个带参数的BrowserFragment
	 * 
	 * @param knowledgeId
	 * @param keyword
	 * @return
	 */
	public static BrowserFragment newInstance(String url) {
		BrowserFragment fragment = new BrowserFragment();
		Bundle args = new Bundle();
		args.putString("url", url);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_browser, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.setVisibility(View.VISIBLE);
		
		Bundle args = getArguments();
		if (args == null) {
			UIHelper.ToastMessage(getActivity(), "无效的地址");
			getActivity().finish();
			return;
		}
		mUrl = args.getString("url");
		mWebView.loadUrl(mUrl);
	}
}
