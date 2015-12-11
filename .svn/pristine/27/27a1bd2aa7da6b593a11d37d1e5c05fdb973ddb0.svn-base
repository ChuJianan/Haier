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
import com.haier.activity.forum.AskExpertReplyActivity;
import com.haier.adapter.RNewsListAdapter;
import com.haier.app.AppException;
import com.haier.bean.RemindInfos;
import com.haier.bean.Reminds;
import com.haier.db.RemindzjDB;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RemindZjActivity extends Activity implements OnClickListener{

	@ViewInject(R.id.newslist)private ListView newslist;
	@ViewInject(R.id.top_title)private TextView top_title;
	private RNewsListAdapter newsListAdapter;
	private List<RemindInfos> news=new ArrayList<RemindInfos>();
	RemindzjDB remindnewsDB=new RemindzjDB(this);
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rnews);
		ViewUtils.inject(this);
		top_title.setText("专家回复");
		initView();
		try {
			loadDatas();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadDatas() throws AppException {
		// TODO Auto-generated method stub
		Reminds reminds=Reminds.parse(remindnewsDB.select());
		for (int i = 0; i < reminds.getFileList().size(); i++) {
			news.add(reminds.getFileList().get(i));
		}
		 
		newsListAdapter.notifyDataSetChanged();
	}
	private void initView() {
		// TODO Auto-generated method stub
		newsListAdapter=new RNewsListAdapter(this, news);
		newslist.setAdapter(newsListAdapter);
		newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				RemindInfos iNews=news.get(arg2);
				Intent intent=new Intent(RemindZjActivity.this,AskExpertReplyActivity.class);
				intent.putExtra("id", Integer.valueOf(iNews.getRelateId()));
				startActivity(intent);
			}
		});
	}
	@OnClick({R.id.back})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
//			remindnewsDB.delete();
			Intent intent=new Intent(RemindZjActivity.this,RemindActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
}
