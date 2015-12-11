package com.haier.activity.fragment;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.LoginActivity;
import com.haier.activity.Vidio2Activity;
import com.haier.bean.ClickKnowledgeId;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.db.KnowledgeDB;
import com.haier.db.OptionListDB;
import com.haier.db.QuestionsDB;
import com.haier.db.RulesDB;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/**
 * @author Administrator
 * 
 */
public class KnowLedgeFragment extends Fragment implements OnClickListener {
	@ViewInject(R.id.knowledge5)
	private TextView knowledge5;
	@ViewInject(R.id.knowledge4)
	private TextView knowledge4;
	@ViewInject(R.id.knowledge3)
	private TextView knowledge3;
	@ViewInject(R.id.knowledge2)
	private TextView knowledge2;
	@ViewInject(R.id.knowledge1)
	private TextView knowledge1;
	@ViewInject(R.id.imageView1)
	private ImageView ImageView1;
	@ViewInject(R.id.imageView2)
	private ImageView ImageView2;
	@ViewInject(R.id.imageView3)
	private ImageView ImageView3;
	@ViewInject(R.id.imageView4)
	private ImageView ImageView4;
	@ViewInject(R.id.imageView5)
	private ImageView ImageView5;
	@ViewInject(R.id.imageView)
	private ImageView ImageView;
	@ViewInject(R.id.rela2)
	private RelativeLayout rela2;
	@ViewInject(R.id.re1)
	private LinearLayout RE1;
	@ViewInject(R.id.re2)
	private LinearLayout RE2;
	@ViewInject(R.id.re3)
	private LinearLayout RE3;
	@ViewInject(R.id.re4)
	private LinearLayout RE4;
	@ViewInject(R.id.re5)
	private LinearLayout RE5;
	QuestionsDB questionsDB;
	String name;
	OptionListDB optionListDB;
	KnowledgeDB knowledgeDB;
	RulesDB rulesDB;
	String id;
	Bitmap bitmap = null;
	Context context;
	KnowledgeDB kDB;
	String[] name1;
	private CustomProgressDialog progressDialog = null;
	/**
	 * table和db这两个字段用来清空questions这个表数据
	 */
	String table = "questions";
	String db = "meetreader.db";
	// Frame动画
	private AnimationDrawable animDance;
	int i = 1;
	/**
	 * 获取控件坐标
	 */
	int x, y;
	int yy;
	int[] location = new int[2];
	/**
	 * 小人移动时间
	 */
	int time = 10000;
	String flag = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.knowledgefragment,
				container, false);
		ViewUtils.inject(this, newsLayout);
		iniw();
		return newsLayout;
	}

	private void iniw() {
		// TODO Auto-generated method stub
		context = getActivity();
		questionsDB = new QuestionsDB(getActivity());
		optionListDB = new OptionListDB(getActivity());
		knowledgeDB = new KnowledgeDB(getActivity());
		rulesDB = new RulesDB(getActivity());
		bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
				R.anim.walk);
		BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView.setBackgroundResource(R.anim.walk);
		animDance = (AnimationDrawable) this.ImageView.getBackground();
		JsonUtils.questionsDB = questionsDB;
		JsonUtils.optionListDB = optionListDB;
		JsonUtils.rulesDB = rulesDB;
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg0004);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView1.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg0005);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView2.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg0006);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView3.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg0007);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView4.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg0008);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		ImageView5.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_knowledge_bg);
		bd = new BitmapDrawable(context.getResources(), bitmap);
		rela2.setBackground(bd);
		String na = JsonUtils.na;
		if (na.equals("1")) {
			name1 = knowledgeDB.select();
			knowledge5.setText(name1[0]);
			knowledge1.setText(name1[1]);
			knowledge2.setText(name1[2]);
			knowledge3.setText(name1[3]);
			knowledge4.setText(name1[4]);
		} else if (na.equals("2")) {
			name1 = knowledgeDB.select2();
			knowledge1.setText(name1[1]);
			knowledge2.setText(name1[2]);
			knowledge3.setText(name1[3]);
			knowledge4.setText(name1[4]);
			knowledge5.setText(name1[5]);
		} else {
			name1 = knowledgeDB.select3();
			knowledge1.setText(name1[0]);
			knowledge2.setText(name1[1]);
			knowledge3.setText(name1[2]);
			knowledge4.setText(name1[3]);
			knowledge5.setText(name1[4]);
		}
		deleteTableByDBName(db, table);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
			super.handleMessage(msg);
			stopProgressDialog();
			start();
		}
	};
	@OnClick({ R.id.knowledge5, R.id.knowledge4, R.id.knowledge3,
			R.id.knowledge2, R.id.knowledge1 })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.knowledge5:
			startProgressDialog();
			name = knowledge5.getText().toString();
			id = knowledgeDB.select4(name);
			ClickKnowledgeId.setClickknowledgeId(id);
			ImageView5.getLocationOnScreen(location);
			x = location[0];
			y = location[1];
			yy = x + 10;
			time = 8000;
			 new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						flag = getQuset();
						handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
					}
				}).start();
			break;
		case R.id.knowledge4:
			startProgressDialog();
			name = knowledge4.getText().toString();
			id = knowledgeDB.select4(name);
			ClickKnowledgeId.setClickknowledgeId(id);
			
			ImageView4.getLocationOnScreen(location);
			x = location[0];
			y = location[1];
			yy = x + 10;
			time = 5000;
			 new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						flag = getQuset();
						handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
					}
				}).start();
			break;
		case R.id.knowledge3:
			startProgressDialog();
			name = knowledge3.getText().toString();
			id = knowledgeDB.select4(name);
			ClickKnowledgeId.setClickknowledgeId(id);
			
			ImageView3.getLocationOnScreen(location);
			x = location[0];
			y = location[1];
			yy = x + 10;
			time = 3500;
			 new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						flag = getQuset();
						handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
					}
				}).start();
			break;
		case R.id.knowledge2:
			startProgressDialog();
			name = knowledge2.getText().toString();
			id = knowledgeDB.select4(name);
			ClickKnowledgeId.setClickknowledgeId(id);
			
			ImageView2.getLocationOnScreen(location);
			x = location[0];
			y = location[1];
			yy = x + 10;
			time = 2500;
			 new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						flag = getQuset();
						handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
					}
				}).start();
			break;
		case R.id.knowledge1:
			startProgressDialog();
			name = knowledge1.getText().toString();
			id = knowledgeDB.select4(name);
			ClickKnowledgeId.setClickknowledgeId(id);
			ImageView1.getLocationOnScreen(location);
			x = location[0];
			y = location[1];
			yy = x + 10;
			time = 1000;
			 new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						flag = getQuset();
						handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
					}
				}).start();
			break;
		}
	}
	private void start() {
		// TODO Auto-generated method stub
		final Animation translateAnimation = new TranslateAnimation(0, yy, 0, 0);
		animDance.start();
		translateAnimation.setDuration(time); //
		translateAnimation.setFillAfter(true);
		translateAnimation.setFillEnabled(true);
		translateAnimation.setInterpolator(new LinearInterpolator());
		ImageView.setAnimation(translateAnimation);
		translateAnimation.startNow();
		translateAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// getQuset();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animDance.stop();
				if (flag == null) {
					UIHelper.ToastMessage(context, "错误");
					Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
				} else {
					if (flag.equals("6")) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), Vidio2Activity.class);
						startActivity(intent);
						getActivity().finish();
					} else {
						UIHelper.ToastMessage(context, JsonUtils.describe);
						Intent intent = new Intent();
						intent.setClass(getActivity(), LoginActivity.class);
						startActivity(intent);
						getActivity().finish();
					}
				}
			}
		});
	}
	private String getQuset() {
		// TODO Auto-generated method stub
		String result = null;
		String sessionId = Sscion.getSsid();
		HttpGet httpRequest = new HttpGet(URLs.TIMETABLE + "knowledgeId=" + ClickKnowledgeId.getClickknowledgeId()
				+ "&sessionId=" + sessionId);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			result = JsonUtils.Fjson(strResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public SQLiteDatabase openDBByName(String DBName) {
		SQLiteDatabase db;
		// deleteDBByName(DBName);
		db = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
		return db;
	}
	public boolean deleteTableByDBName(String DBName, String TableName) {
		SQLiteDatabase dbDatabase = openDBByName(DBName);
		dbDatabase.delete(TableName, null, null);
		close(dbDatabase);
		return false;
	}
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(getActivity());
			progressDialog.setMessage("正在加载中...");
		}
		progressDialog.show();
	}

	private void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	public void close(SQLiteDatabase db_) {
		db_.close();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		bitmap.recycle();
		BitmapDrawable bd = null;
		ImageView.setBackgroundResource(0);
		animDance.setCallback(null);
		animDance = null;
		bd = (BitmapDrawable) ImageView1.getBackground();
		ImageView1.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) ImageView2.getBackground();
		ImageView2.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		;
		bd = (BitmapDrawable) ImageView3.getBackground();
		ImageView3.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) ImageView4.getBackground();
		ImageView4.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) ImageView5.getBackground();
		ImageView5.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) rela2.getBackground();
		rela2.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		System.gc();
	}
}
