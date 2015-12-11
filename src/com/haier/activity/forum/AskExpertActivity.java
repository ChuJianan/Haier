package com.haier.activity.forum;

import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.haier.R;
import com.haier.activity.BaseActivity;
import com.haier.app.AppException;
import com.haier.bean.QuestionInfo;
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

public class AskExpertActivity extends BaseActivity {
	@ViewInject(R.id.ask_title)
	private EditText mTxtTitle;
	@ViewInject(R.id.ask_content)
	private EditText mTxtContent;
	@ViewInject(R.id.spinner1)
	private Spinner mSpinner1;
	@ViewInject(R.id.spinner2)
	private Spinner mSpinner2;

	private SpinnerAdapter mSpinner1Adapter;
	private SpinnerAdapter mSpinner2Adapter;
	
	private ProgressDialog mLoadingDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_askexpert);
		ViewUtils.inject(this);
		
		initView();
		initData();
	}
	
	private void initView() {
		mLoadingDialog = new ProgressDialog(this);
		mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mLoadingDialog.setTitle("提示");
		mLoadingDialog.setMessage("正在加载……");
		mLoadingDialog.setCancelable(false);
		
		mSpinner1Adapter = new SpinnerAdapter(this);
		mSpinner1.setAdapter(mSpinner1Adapter);
		mSpinner2Adapter = new SpinnerAdapter(this);
		mSpinner2.setAdapter(mSpinner2Adapter);
		
		mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				QuestionInfo info = (QuestionInfo) mSpinner1Adapter.getItem(position);
				mSpinner2Adapter.setData(info.getSecondInfos());
				mSpinner2Adapter.notifyDataSetChanged();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
	private void initData() {
		loadQuestionInfo();
	}
	
	private void loadQuestionInfo() {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		http.send(HttpMethod.GET, URLs.URL_GET_EXPERTS, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					mLoadingDialog.dismiss();
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else if (result.firstInfos.size() > 0) {
						mSpinner1Adapter.setData(result.firstInfos);
						mSpinner1Adapter.notifyDataSetChanged();
					} else {
						UIHelper.ToastMessage(AskExpertActivity.this, "暂无问题分类列表");
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(AskExpertActivity.this);
				} catch (AppException e) {
					e.makeToast(AskExpertActivity.this);
				}
			}
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mLoadingDialog.dismiss();
				UIHelper.ToastMessage(AskExpertActivity.this, "数据发送失败，请重试");
			}
		});
	}
	
	/**
	 * 问专家
	 * @param title
	 * @param contents
	 */
	private void askExpert(String title, String contents, long expertId) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		params.addQueryStringParameter("title", title);
		params.addQueryStringParameter("contents", contents);
		params.addQueryStringParameter("expertId", String.valueOf(expertId));
		http.send(HttpMethod.POST, URLs.SEND_EXOERTSURL, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					mLoadingDialog.dismiss();
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else {
						UIHelper.ToastMessage(AskExpertActivity.this, "提问成功");
						finish();
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(AskExpertActivity.this);
				} catch (AppException e) {
					e.makeToast(AskExpertActivity.this);
				}
			}
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mLoadingDialog.dismiss();
				UIHelper.ToastMessage(AskExpertActivity.this, "数据发送失败，请重试");
			}
		});
	}
	
	@OnClick({R.id.submit,R.id.back})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submit:
			String title = mTxtTitle.getText().toString().trim();
			String contents = mTxtContent.getText().toString().trim();
			long expertId = mSpinner2.getSelectedItemId();
			if (expertId == 0) {
				expertId = mSpinner1.getSelectedItemId();
			}        
			askExpert(title, contents, expertId);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	public class SpinnerAdapter extends BaseAdapter {
		private Context mContext;
		/**
		 * 数据
		 */
		private List<QuestionInfo> data = Collections.emptyList();
		
		public SpinnerAdapter(Context context) {
			this.mContext = context;
		}
		public SpinnerAdapter(Context context, List<QuestionInfo> data) {
			this.mContext = context;
			this.data = data;
		}
		/**
		 * 设置数据
		 * @param data
		 */
		public void setData(List<QuestionInfo> data) {
			this.data = data;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txt;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
				txt = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(txt);
			} else {
				txt = (TextView) convertView.getTag();
			}
			QuestionInfo info = data.get(position);
			txt.setText(info.getName());
			return convertView;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.valueOf(data.get(position).getId());
		}
	}
}
