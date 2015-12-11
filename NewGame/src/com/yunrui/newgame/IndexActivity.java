package com.yunrui.newgame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.yunrui.util.JsonUtil;
import com.yunrui.util.QuestionContentProviderUtil;
import com.yunrui.util.TestUtil;

public class IndexActivity extends Activity{

	ImageView start,exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		init();
		getParameterByIntent();
		JsonUtil.qList.clear();
		try {
			QuestionContentProviderUtil.testFind(this);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (JsonUtil.qList.size()==0|| TestUtil.ssid ==null) {
					Toast.makeText(IndexActivity.this, "没有登陆，或者没有测试题目", Toast.LENGTH_SHORT).show();	
				}else{
					Intent intent=new Intent(IndexActivity.this,MainActivity.class);
					startActivity(intent);
				}
				
			}
		});
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				java.lang.System.exit(0);
			}
		});
	}
	private void getParameterByIntent() {
		// TODO Auto-generated method stub
		Intent mIntent = this.getIntent();
		Bundle bundle=this.getIntent().getExtras();
		   TestUtil.ssid = mIntent.getStringExtra("sscion");
		   TestUtil.kid=mIntent.getStringExtra("id");
		   TestUtil.name=mIntent.getStringExtra("username");
		   TestUtil.userid=mIntent.getStringExtra("userid");
		   if (TestUtil.ssid!=null) {
			   TestUtil.music1=bundle.getBoolean("music1");
			   TestUtil.music2=bundle.getBoolean("music1");
		   }
		   Toast.makeText(this, TestUtil.ssid+"!"+TestUtil.kid+"!"+TestUtil.name+"!"+TestUtil.userid, 1000).show();
	}
	private void init() {
		// TODO Auto-generated method stub
		start=(ImageView)findViewById(R.id.start);
		exit=(ImageView)findViewById(R.id.exit);
	}
}
