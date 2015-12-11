package com.haier.activity.forum;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonSyntaxException;
import com.haier.R;
import com.haier.activity.BaseActivity;
import com.haier.app.AppException;
import com.haier.bean.Result;
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
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AskAllActivity extends BaseActivity {
	@ViewInject(R.id.ask_title)
	private EditText mTxtTitle;
	@ViewInject(R.id.ask_content)
	private EditText mTxtContent;
	
	private ProgressDialog mLoadingDialog;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_askall);
		ViewUtils.inject(this);
		initView();
	}
	private void initView() {
		mLoadingDialog = new ProgressDialog(this);
		mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mLoadingDialog.setTitle("提示");
		mLoadingDialog.setMessage("正在加载……");
		mLoadingDialog.setCancelable(false);
	}
	
	@OnClick({R.id.submit,R.id.back})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submit:
			String title = mTxtTitle.getText().toString().trim();
			String contents = mTxtContent.getText().toString().trim();
			askAll(title, contents);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 问大家
	 * @param title
	 * @param contents
	 */
	private void askAll(String title, String contents) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		params.addQueryStringParameter("title", title);
		params.addQueryStringParameter("contents", contents);
		http.send(HttpMethod.GET, URLs.SEND_URL, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					mLoadingDialog.dismiss();
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else {
						UIHelper.ToastMessage(AskAllActivity.this, "提问成功");
						finish();
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(AskAllActivity.this);
				} catch (AppException e) {
					e.makeToast(AskAllActivity.this);
				}
			}
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mLoadingDialog.dismiss();
				UIHelper.ToastMessage(AskAllActivity.this, "数据发送失败，请重试");
			}
		});
	}
}
