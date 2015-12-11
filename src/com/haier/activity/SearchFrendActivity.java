package com.haier.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.FriendListAdapter;
import com.haier.app.AppClient;
import com.haier.bean.Friend;
import com.haier.bean.Friends;
import com.haier.bean.INews;
import com.haier.bean.Sscion;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SearchFrendActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.left_button)
	private Button backButton;
	@ViewInject(R.id.search)
	private TextView search;
	@ViewInject(R.id.list)
	private ListView news_list;
	@ViewInject(R.id.editfrend)
	private EditText edit;
	private FriendListAdapter newsListAdapter;
	private List<Friend> news = new ArrayList<Friend>();
	private View mNewsFooter;
	private String username;
	private String userString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_layout);
		ViewUtils.inject(this);
		inview();
	}

	private void loadNewsDatas() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					Friends list = Friends.parse(AppClient.httpgetFriend(
							Sscion.getSsid(), userString));
					msg.what = list.getFileList().size();
					msg.obj = list;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what > 0) {
				Friends list = (Friends) msg.obj;
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					news.clear();
					news = list.getFileList();
				} else {
					List<Friend> iList = new ArrayList<Friend>(
							list.getFileList());
					news.removeAll(iList);
					news.addAll(iList);
				}
				newsListAdapter.notifyDataSetChanged();
			}
		}
	};

	private void inview() {
		// TODO Auto-generated method stub
		newsListAdapter = new FriendListAdapter(this, news);
		news_list.setAdapter(newsListAdapter);
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (edit.getText().toString().equals("")) {
				} else {
					username = edit.getText().toString();
					try {
						news.clear();
						userString = URLEncoder.encode(username, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					loadNewsDatas();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		/**
		 * 列表点击事件
		 */
		/*news_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//点击头部、底部栏无效
        		if(position == 0 || view == mNewsFooter) return;
        		Friend file = (Friend)newsListAdapter.getItem(position);
				String flag=AppClient.httpgetaddFriend(Sscion.getSsid(), file.getId());
				if (flag.equals("6")) {
					UIHelper.ToastMessage(SearchFrendActivity.this, JsonUtils.describe);
				}else {
					UIHelper.ToastMessage(SearchFrendActivity.this, JsonUtils.describe);
				}
			}
		});*/
	}

	@OnClick({ R.id.left_button })
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.left_button:
			finish();
			break;
		case R.id.search:
			username = edit.getText().toString();
			if (username.equals("")) {
				UIHelper.ToastMessage(this, "不能为空");
			} else {
				try {
					news.clear();
					userString = URLEncoder.encode(username, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				loadNewsDatas();
			}
			break;
		}
	}
}
