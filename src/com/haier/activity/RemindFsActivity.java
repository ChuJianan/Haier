package com.haier.activity;

import java.util.ArrayList;
import java.util.List;

import com.haier.R;
import com.haier.adapter.RemindFsListAdapter;
import com.haier.app.AppClient;
import com.haier.app.AppException;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.bean.UserInfo;
import com.haier.bean.Users;
import com.haier.db.RemindfzDB;
import com.haier.db.RemindmeDB;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class RemindFsActivity extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.listView)private ListView fListView;
	@ViewInject(R.id.top_title)private TextView top_title;
	RemindFsListAdapter remindFsListAdapter;
	List<UserInfo> list=new ArrayList<UserInfo>();
	RemindfzDB remindnewsDB=new RemindfzDB(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remindfs);
		ViewUtils.inject(this);
		top_title.setText("好友验证");
		initView();
		loadDatas();
	}
	private void loadDatas() {
		// TODO Auto-generated method stub
		try {
			Users users=Users.parse(AppClient.httpgetFSList(URLs.FSLIST_URL+"sessionId="+Sscion.getSsid()+
					"&pageno=1"+"&confirmFlag=0"));
			for (int i = 0; i < users.getFileList().size(); i++) {
				list.add(users.getFileList().get(i));
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		remindFsListAdapter.notifyDataSetChanged();
	}
	private void initView() {
		// TODO Auto-generated method stub
		remindFsListAdapter=new RemindFsListAdapter(this, list);
		fListView.setAdapter(remindFsListAdapter);
	}
	@OnClick({R.id.back})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
			remindnewsDB.delete();
			/*Intent intent=new Intent(RemindFsActivity.this,RemindActivity.class);
			startActivity(intent);*/
			finish();
			break;

		}
	}
}
