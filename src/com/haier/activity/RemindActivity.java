package com.haier.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.RemindListAdapter;
import com.haier.app.AppException;
import com.haier.bean.RemindInfos;
import com.haier.bean.Reminds;
import com.haier.db.RemindDB;
import com.haier.db.RemindfzDB;
import com.haier.db.RemindmeDB;
import com.haier.db.RemindnewsDB;
import com.haier.db.RemindzjDB;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RemindActivity extends Activity implements OnClickListener{
	
	@ViewInject(R.id.remind_list)private ListView remind_listview;
	@ViewInject(R.id.top_title) private TextView top_title;
	RemindDB remindDB;
	RemindfzDB remindfzDB;
	RemindmeDB remindmeDB;
	RemindnewsDB remindnewsDB;
	RemindzjDB remindzjDB;
	List<RemindInfos> remindnews=new ArrayList<RemindInfos>();
	List<RemindInfos> remindzj=new ArrayList<RemindInfos>();
	List<RemindInfos> remindfs=new ArrayList<RemindInfos>();
	List<RemindInfos> remindme=new ArrayList<RemindInfos>();
	List<Integer> remindsize=new ArrayList<Integer>();
	RemindListAdapter remindadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		ViewUtils.inject(this);
		remindDB=new RemindDB(this);
		remindfzDB=new RemindfzDB(this);
		remindmeDB=new RemindmeDB(this);
		remindnewsDB=new RemindnewsDB(this);
		remindzjDB=new RemindzjDB(this);
		top_title.setText("消息提醒");
		inui();
		try {
			loadDatas();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void inui() {
		// TODO Auto-generated method stub
		remindadapter=new RemindListAdapter(this, remindsize);
		remind_listview.setAdapter(remindadapter);
		remind_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					if (remindsize.get(arg2)!=0) {
						Intent intent=new Intent(RemindActivity.this,RnewsActivity.class);
						startActivity(intent);
						finish();
					}else {
						UIHelper.ToastMessage(RemindActivity.this, "没有未读新闻！");
					}
					break;
				case 1:
					if (remindsize.get(arg2)!=0) {
						Intent intent=new Intent(RemindActivity.this,RemindZjActivity.class);
						startActivity(intent);
						finish();
					}else {
						UIHelper.ToastMessage(RemindActivity.this, "没有专家回复！");
					}
					break;
				case 2:
					if (remindsize.get(arg2)!=0) {
						Intent intent=new Intent(RemindActivity.this,RemindFsActivity.class);
						startActivity(intent);
						finish();
					}else {
						UIHelper.ToastMessage(RemindActivity.this, "没有好友验证！");
					}
					break;
				case 3:
					if (remindsize.get(arg2)!=0) {
						Intent intent=new Intent(RemindActivity.this,RemindmeActivity.class);
						startActivity(intent);
						finish();
					}else {
						UIHelper.ToastMessage(RemindActivity.this, "没有自己的调研！");
					}
					break;
				}
			}
		});
	}
	private void loadDatas() throws AppException {
		// TODO Auto-generated method stub
		Reminds reminds = null;
		if (!remindnewsDB.select().equals("")) {
			 reminds=Reminds.parse(remindnewsDB.select());
			 for (int i = 0; i < reminds.getFileList().size(); i++) {
					remindnews.add(reminds.getFileList().get(i));
				}
		}
		
		if (!remindzjDB.select().equals("")) {

			reminds=Reminds.parse(remindzjDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindzj.add(reminds.getFileList().get(i));
			}
		}
		if (!remindfzDB.select().equals("")) {
			reminds=Reminds.parse(remindfzDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindfs.add(reminds.getFileList().get(i));
			}
		}
		if (!remindmeDB.select().equals("")) {
			reminds=Reminds.parse(remindmeDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindme.add(reminds.getFileList().get(i));
			}
		}
		remindDB.delete();
		remindsize.add(remindnews.size());
		remindsize.add(remindzj.size());
		remindsize.add(remindfs.size());
		remindsize.add(remindme.size());
		remindadapter.notifyDataSetChanged();
	}
	@OnClick({R.id.back})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;

		}
	}

}
