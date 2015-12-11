package com.haier.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.haier.R;
import com.haier.activity.LoginActivity;
import com.haier.activity.MainActivity;
import com.haier.app.AppException;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RuleFragment extends Fragment {
	@ViewInject(R.id.textrules)
	private TextView rules;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.rules, container, false);
		ViewUtils.inject(this, view);
		loadMessageData();
		return view;
	}
	/**
	 * 加载消息
	 */
	String flag = null;
	public void loadMessageData() {
		HttpUtils http = new HttpUtils();
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("sessionId", Sscion.getSsid());
			params.addQueryStringParameter("ruleId", "1");
			http.send(HttpMethod.GET, URLs.RULE, params, new RequestCallBack<String>() {
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					try {
						String 	strResult = responseInfo.result;
						flag = JsonUtils.flagJson(strResult);
						if (flag != null && flag.equals("6")) {
							flag = JsonUtils.ruleJson(strResult);
							rules.setText(Html.fromHtml(flag));
						}
						if (flag != null && flag.equals("1")) {
							UIHelper.ToastMessage(getActivity(), "用户已在其他客户端登陆！");
							Intent intent = new Intent(getActivity(), LoginActivity.class);
							MainActivity.mainActivity.finish();
							startActivity(intent);
							getActivity().finish();
						}
					} catch (JsonParseException e) {
						AppException.json(e).makeToast(getActivity());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				@Override
				public void onStart() {
					
				}
				@Override
				public void onFailure(HttpException error, String msg) {
				}
			});
	}
	
	
	
}