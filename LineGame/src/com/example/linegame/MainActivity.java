package com.example.linegame;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yunrui.util.CustomDialog;
import com.yunrui.util.EquipmentContentProviderUtil;
import com.yunrui.util.Getquestion;
import com.yunrui.util.HttpUtil;
import com.yunrui.util.JsonUtil;
import com.yunrui.util.QuestionContentProviderUtil;
import com.yunrui.util.RousUtil;
import com.yunrui.util.ScheduleInfosContentProviderUtil;
import com.yunrui.util.TestUtil;
import com.yunrui.util.UserInfoContentProviderUtil;

public class MainActivity extends Activity {

	private LineView lineView;
	ImageView btnok;
	Context context;
	@ViewInject(R.id.t1)
	TextView t1;
	@ViewInject(R.id.t2)
	TextView t2;
	@ViewInject(R.id.t3)
	TextView t3;
	@ViewInject(R.id.t4)
	TextView t4;
	@ViewInject(R.id.t5)
	TextView t5;
	@ViewInject(R.id.t6)
	TextView t6;
	@ViewInject(R.id.t7)
	TextView t7;
	@ViewInject(R.id.t8)
	TextView t8;
	@ViewInject(R.id.t9)
	TextView t9;
	@ViewInject(R.id.t10)
	TextView t10;
	@ViewInject(R.id.rt1)
	TextView rt1;
	@ViewInject(R.id.rt2)
	TextView rt2;
	@ViewInject(R.id.rt3)
	TextView rt3;
	@ViewInject(R.id.rt4)
	TextView rt4;
	@ViewInject(R.id.rt5)
	TextView rt5;
	@ViewInject(R.id.rt6)
	TextView rt6;
	@ViewInject(R.id.rt7)
	TextView rt7;
	@ViewInject(R.id.rt8)
	TextView rt8;
	@ViewInject(R.id.rt9)
	TextView rt9;
	@ViewInject(R.id.rt10)
	TextView rt10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		lineView = (LineView) findViewById(R.id.lineview);
		btnok = (ImageView) findViewById(R.id.btnok);
		builder = new CustomDialog.Builder(this);
		context = MainActivity.this;
		inData();
		TestUtil.zs = 0;
		RousUtil.mf = 0;
		Getquestion.mfList.clear();
		Getquestion.questidList.clear();
		Getquestion.questTFList.clear();
		Getquestion.questtimeList.clear();
		Getquestion.questuserList.clear();
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Thread thread=new Thread(runnable);
				// thread.start();
				myHandler.post(runnable);
				// new Thread(){
				// public void run() {
				// // TODO: http request.
				//
				//
				// }.start();
			}
		});
		t1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				indata();
				lineView.postInvalidate();
			}
		});
	}

	private void inData() {
		// TODO Auto-generated method stub
		t1.setText(JsonUtil.qList.get(0).getTitle());
		t2.setText(JsonUtil.qList.get(1).getTitle());
		t3.setText(JsonUtil.qList.get(2).getTitle());
		t4.setText(JsonUtil.qList.get(3).getTitle());
		rt1.setText(JsonUtil.qList.get(0).getOplists().get(0).getQcontext());
		rt2.setText(JsonUtil.qList.get(1).getOplists().get(0).getQcontext());
		rt3.setText(JsonUtil.qList.get(2).getOplists().get(0).getQcontext());
		rt4.setText(JsonUtil.qList.get(3).getOplists().get(0).getQcontext());
		if (JsonUtil.qList.size()==5) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
		}
		if (JsonUtil.qList.size()==6) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
			t6.setText(JsonUtil.qList.get(5).getTitle());
			rt6.setText(JsonUtil.qList.get(5).getOplists().get(0).getQcontext());
		}
		if (JsonUtil.qList.size() == 7) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
			t6.setText(JsonUtil.qList.get(5).getTitle());
			rt6.setText(JsonUtil.qList.get(5).getOplists().get(0).getQcontext());
			t7.setText(JsonUtil.qList.get(6).getTitle());
			rt7.setText(JsonUtil.qList.get(6).getOplists().get(0).getQcontext());
		}
		if (JsonUtil.qList.size() == 8) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
			t6.setText(JsonUtil.qList.get(5).getTitle());
			rt6.setText(JsonUtil.qList.get(5).getOplists().get(0).getQcontext());
			t7.setText(JsonUtil.qList.get(6).getTitle());
			rt7.setText(JsonUtil.qList.get(6).getOplists().get(0).getQcontext());
			t8.setText(JsonUtil.qList.get(7).getTitle());
			rt8.setText(JsonUtil.qList.get(7).getOplists().get(0).getQcontext());
		}
		if (JsonUtil.qList.size() == 9) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
			t6.setText(JsonUtil.qList.get(5).getTitle());
			rt6.setText(JsonUtil.qList.get(5).getOplists().get(0).getQcontext());
			t7.setText(JsonUtil.qList.get(6).getTitle());
			rt7.setText(JsonUtil.qList.get(6).getOplists().get(0).getQcontext());
			t8.setText(JsonUtil.qList.get(7).getTitle());
			rt8.setText(JsonUtil.qList.get(7).getOplists().get(0).getQcontext());
			t9.setText(JsonUtil.qList.get(8).getTitle());
			rt9.setText(JsonUtil.qList.get(8).getOplists().get(0).getQcontext());
		}
		if (JsonUtil.qList.size() == 10) {
			t5.setText(JsonUtil.qList.get(4).getTitle());
			rt5.setText(JsonUtil.qList.get(4).getOplists().get(0).getQcontext());
			t6.setText(JsonUtil.qList.get(5).getTitle());
			rt6.setText(JsonUtil.qList.get(5).getOplists().get(0).getQcontext());
			t7.setText(JsonUtil.qList.get(6).getTitle());
			rt7.setText(JsonUtil.qList.get(6).getOplists().get(0).getQcontext());
			t8.setText(JsonUtil.qList.get(7).getTitle());
			rt8.setText(JsonUtil.qList.get(7).getOplists().get(0).getQcontext());
			t9.setText(JsonUtil.qList.get(8).getTitle());
			rt9.setText(JsonUtil.qList.get(8).getOplists().get(0).getQcontext());
			t10.setText(JsonUtil.qList.get(9).getTitle());
			rt10.setText(JsonUtil.qList.get(9).getOplists().get(0).getQcontext());
		}
		
	}
//	private void setTextView(){
//		if (JsonUtil.qList.size()>4) {
//			int i=JsonUtil.qList.size()-4;
//			for (int j =0; j <= i; j++) {
//				TextView t=new TextView(this);
//				t.setText(JsonUtil.qList.get(4+j).getTitle());
//			}
//		}
//	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		indata();
		lineView.postInvalidate();
	}
	public void indata(){
		Point point = new Point();
		int[] location = new int[2];  
		point.index = JsonUtil.qList.get(0).getId();
		t1.getLocationOnScreen(location);
		point.x = t1.getX() + t1.getWidth();
		point.y = t1.getY() + t1.getHeight() / 2-30;
		
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(1).getId();
		point.x = t2.getX() + t2.getWidth();
		point.y = t2.getY() + t2.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(2).getId();
		point.x = t3.getX() + t3.getWidth();
		point.y = t3.getY() + t3.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(3).getId();
		point.x = t4.getX() + t4.getWidth();
		point.y = t4.getY() + t4.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(0).getOplists().get(0).getOrderIndex();
		point.x = rt1.getX() -70;
		point.y = t1.getY() + t1.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(1).getOplists().get(0).getOrderIndex();
		point.x = rt2.getX() -70;
		point.y = t2.getY() + t2.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(2).getOplists().get(0).getOrderIndex();
		point.x = rt3.getX() -70;
		point.y = t3.getY() + t3.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		point = new Point();
		point.index = JsonUtil.qList.get(3).getOplists().get(0).getOrderIndex();
		point.x = rt4.getX() -70;
		point.y = t4.getY() + t4.getHeight() / 2-30;
		TestUtil.keyPoints.add(point);
		if (JsonUtil.qList.size()==5) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		if (JsonUtil.qList.size()==6) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t6.getX() + t6.getWidth();
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		if (JsonUtil.qList.size()==7) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t6.getX() + t6.getWidth();
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t7.setVisibility(View.VISIBLE);
			
			
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t7.getX() + t7.getWidth();
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt7.getX() -70;
			point.y = t7.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		if (JsonUtil.qList.size()==8) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t6.getX() + t6.getWidth();
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t7.getX() + t7.getWidth();
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt7.getX() -70;
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t8.getX() + t8.getWidth();
			point.y = t8.getY() + t8.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt8.getX() -70;
			point.y = t8.getY() + t8.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		if (JsonUtil.qList.size()==9) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t7.getX() + t7.getWidth();
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt7.getX() -70;
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t8.getX() + t8.getWidth();
			point.y = t9.getY() + t8.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt8.getX() -70;
			point.y = t8.getY() + t8.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t9.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t9.getX() + t9.getWidth();
			point.y = t9.getY() + t9.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt9.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt9.getX() -70;
			point.y = t9.getY() + t9.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		if (JsonUtil.qList.size()==10) {
			t5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t5.getX() + t5.getWidth();
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt5.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt5.getX() -70;
			point.y = t5.getY() + t5.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt6.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt6.getX() -70;
			point.y = t6.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t7.getX() + t7.getWidth();
			point.y = t7.getY() + t7.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt7.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt7.getX() -70;
			point.y = t7.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t8.getX() + t8.getWidth();
			point.y = t9.getY() + t8.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt8.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt8.getX() -70;
			point.y = t8.getY() + t6.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t9.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t9.getX() + t9.getWidth();
			point.y = t9.getY() + t9.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt9.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt9.getX() -70;
			point.y = t9.getY() + t9.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			t10.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(3).getId();
			point.x = t10.getX() + t10.getWidth();
			point.y = t10.getY() + t10.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
			rt10.setVisibility(View.VISIBLE);
			point = new Point();
			point.index = JsonUtil.qList.get(4).getOplists().get(0).getOrderIndex();
			point.x = rt10.getX() -70;
			point.y = t10.getY() + t10.getHeight() / 2-30;
			TestUtil.keyPoints.add(point);
		}
		
	}
	
	// 直接返回
	@Override
	public void onBackPressed() {
		if (!lineView.undo()) {
			super.onBackPressed();
		}
	}

	
	
	CustomDialog.Builder builder;

	Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// myHandler.post(runnable);
			if (msg.obj != null) {
				switch (JsonUtil.updateFlag) {
				case 1:
					builder.setUpdate("分数降低，装备无变化");
					break;
				case 2:
					builder.setUpdate("分数增高但装备无变化");
					break;
				case 3:
					builder.setUpdate("分数增高，装备更新");
					break;
				}
				// builder.setMessage("玩家："+TestUtil.name+"\n"+" 得分："+RousUtil.mf);
				builder.setRos(R.drawable.ic_go1);
				builder.setTitle("恭喜您获得了" + RousUtil.mf + "分");
				// if (JsonUtil.pass==2) {
				builder.setPositiveButton("下一知识点",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// 设置你的操作事项
								// JsonUtil.qList.clear();
								// HttpUtil.getQuset();
								// Intent intent=new
								// Intent(MainActivity.this,MainActivity.class);
								// startActivity(intent);
								// finish();
								System.exit(0);
							}
						});
				// }else {
				// builder.setPositiveButton("返回目录", new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int which) {
				// dialog.dismiss();
				// //设置你的操作事项
				// JsonUtil.qList.clear();
				// finish();
				// }
				// });
				// }

				builder.setNegativeButton("再来一次",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								JsonUtil.qList.clear();
								try {
									QuestionContentProviderUtil
											.testFind(context);
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Intent intent = new Intent(MainActivity.this,
										MainActivity.class);
								startActivity(intent);
								TestUtil.zs = 0;
								finish();
							}
						});

				builder.create().show();
			}

		}
	};
	String flag = null;
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO: http request.
			Message msg = new Message();
			if (Getquestion.questuserList.size() != 0) {
				if (TestUtil.ssid != null && TestUtil.kid != null
						&& TestUtil.userid != null) {

					flag = HttpUtil.httpget();
					msg.obj = flag;
					if (flag != null && flag.equals("6")) {
						try {
							ScheduleInfosContentProviderUtil.insert(context);
							UserInfoContentProviderUtil.update("1", context);
							EquipmentContentProviderUtil.insert(context);
							QuestionContentProviderUtil.testFind(context);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (flag.equals("1")) {
						// Toast.makeText(context, "访问过期！",
						// Toast.LENGTH_LONG).show();
						// MainActivity.this.finish();
						// onDestroy();
					}
				} else {
					Toast.makeText(context, "你未登陆！", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(context, "你没有作答！", Toast.LENGTH_LONG).show();
			}

			myHandler.sendMessage(msg);
		}

	};
}
