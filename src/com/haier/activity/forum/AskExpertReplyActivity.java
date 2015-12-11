package com.haier.activity.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.haier.R;
import com.haier.activity.BaseActivity;
import com.haier.adapter.ListViewForumReplyAdapter;
import com.haier.app.AppException;
import com.haier.bean.AskallInfos;
import com.haier.bean.ReplyInfo;
import com.haier.bean.Result;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.db.UserDB;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AskExpertReplyActivity extends BaseActivity implements OnClickListener{
	@ViewInject(R.id.webview)
	private TextView mWebView;
	@ViewInject(R.id.listview)
	private ListView mReplysView;
	@ViewInject(R.id.editan)
	private EditText editText;
	@ViewInject(R.id.title1)
	private TextView title;
	private ListViewForumReplyAdapter mReplyAdapter;
	private int mAskInfosId;
	private String replyContents;
	UserDB userDB=new UserDB(this);
	private CustomProgressDialog progressDialog = null;
	public List<AskallInfos> askAllInfos = Collections.emptyList();			// 查询单个问大家帖子
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_reply_askall);
		ViewUtils.inject(this);
		initData();
	}
	
	private void initData() {
		mAskInfosId = getIntent().getIntExtra("id", 0);
		loadReplys();
	}
	
	private void loadReplys() {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		params.addQueryStringParameter("askExpertId", String.valueOf(mAskInfosId));
		params.addQueryStringParameter("page", "1");
		http.send(HttpMethod.GET, URLs.URL_REPLY_ASKEXPERT, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					stopProgressDialog();
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else if (result.askAllInfos.size() > 0) {
						AskallInfos info = result.askAllInfos.get(0);
						askAllInfos.removeAll(result.askAllInfos);
						askAllInfos=result.askAllInfos;
						setTitle("微互动");
						title.setText(info.getTitle());
						mWebView.setText(Html.fromHtml(info.getContents()));
						//mWebView.loadDataWithBaseURL(URLs.HOST, info.getContents(), "text/html", "UTF-8", null);
						if (info.getAskAllReplyInfos().size() > 0) {
							mReplyAdapter = new ListViewForumReplyAdapter(AskExpertReplyActivity.this, info.getAskAllReplyInfos());
							mReplysView.setAdapter(mReplyAdapter);
						} else {
							UIHelper.ToastMessage(AskExpertReplyActivity.this, "暂无回复列表");
						}
					} else {
						UIHelper.ToastMessage(AskExpertReplyActivity.this, "未获取到数据");
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(AskExpertReplyActivity.this);
				} catch (AppException e) {
					e.makeToast(AskExpertReplyActivity.this);
				}
			}
			@Override
			public void onStart() {
				startProgressDialog();
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				stopProgressDialog();
				UIHelper.ToastMessage(AskExpertReplyActivity.this, "数据获取失败，请重试");
			}
		});
	}
	@OnClick({R.id.send,R.id.back})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){
		case R.id.send:
			replyContents=editText.getText().toString();
			editText.setText("");
			askAll(replyContents);
			break;
		case R.id.back:
			finish();
			break;
		}
	}
	static  List<ReplyInfo> replyInfos=new ArrayList<ReplyInfo>();
   private void askAll(String contents) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		params.addQueryStringParameter("askAllId", String.valueOf(mAskInfosId));
		params.addQueryStringParameter("replyContents", contents);
		http.send(HttpMethod.GET, URLs.EANWER, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else {
						UIHelper.ToastMessage(AskExpertReplyActivity.this, "回复成功");
						loadReplys();
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(AskExpertReplyActivity.this);
				} catch (AppException e) {
					e.makeToast(AskExpertReplyActivity.this);
				}
			}
			@Override
			public void onStart() {
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				UIHelper.ToastMessage(AskExpertReplyActivity.this, "数据发送失败，请重试");
			}
		});
	}
   private void startProgressDialog() {
 		if (progressDialog == null) {
 			progressDialog = CustomProgressDialog.createDialog(this);
 			progressDialog.setMessage("正在加载数据...");
 		}
 		progressDialog.show();
 	}

 	private void stopProgressDialog() {
 		if (progressDialog != null) {
 			progressDialog.dismiss();
 			progressDialog = null;
 		}
 	}
   @Override  
   public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        { 
             finish();
       }  
       return false;  
   }  
}
