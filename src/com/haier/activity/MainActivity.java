package com.haier.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.haier.R;
import com.haier.app.AppException;
import com.haier.bean.Music1;
import com.haier.bean.RemindInfos;
import com.haier.bean.Reminds;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.bean.User;
import com.haier.db.RemindDB;
import com.haier.db.RemindfzDB;
import com.haier.db.RemindnewsDB;
import com.haier.db.RemindzjDB;
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

/**
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	@ViewInject(R.id.ima_btn1)
	private ImageButton iButton;
	@ViewInject(R.id.ima_btn5)
	private ImageButton newsButton;
	@ViewInject(R.id.right_button)
	private Button btnexit;
	@ViewInject(R.id.ima_btn4)
	private ImageButton iSign;
	@ViewInject(R.id.mainRl)
	private RelativeLayout mainLayout;
	@ViewInject(R.id.remindsize)
	private TextView remindsize;
	@ViewInject(R.id.rnewsize)
	private TextView nsize;
	@ViewInject(R.id.rels2)
	private RelativeLayout rels2;
	@ViewInject(R.id.rfsize)
	private TextView fsize;
	@ViewInject(R.id.rels)
	private RelativeLayout rels;
	@ViewInject(R.id.rhdsize)
	private TextView hsize;
	@ViewInject(R.id.rels1)
	private RelativeLayout rels1;
	String accessFlag;
	SharedPreferences user;
	RemindDB remindDB;
	int timeer = 1;
	Drawable drawable;
	String table = "userInfo";
	String db = "meetreader.db";
	UserDB userDB;
	String[] users;
	String userstore;
	String username;
	List<RemindInfos> remindfs = new ArrayList<RemindInfos>();
	RemindfzDB remindfzDB;
	RemindzjDB remindzjDB;
	List<RemindInfos> remindzj = new ArrayList<RemindInfos>();
	RemindnewsDB remindnewsDB;
	List<RemindInfos> remindnews = new ArrayList<RemindInfos>();
	public static Activity mainActivity;
	private CustomProgressDialog progressDialog = null;
	private CustomProgressDialog progressDialog2 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		mainActivity = this;
		remindDB = new RemindDB(this);
		remindfzDB = new RemindfzDB(this);
		remindzjDB = new RemindzjDB(this);
		remindnewsDB = new RemindnewsDB(this);
		userDB = new UserDB(this);
		users = userDB.select1();
		username = users[1];
		userstore = users[2];
		User.setUserName(username);
		User.setUserscore(userstore);
		user = getSharedPreferences("main_background", 0);
		try {
			inui();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	private void inui() throws AppException {
		// TODO Auto-generated method stub
		String loadTime = user.getString("url","");
		if (loadTime.equals("")) {
			mainLayout.setBackgroundResource(R.drawable.ic_mainbg2);
		} else {
			if (loadTime.startsWith("sd:")) {
				loadTime = loadTime.substring(3);
				mainLayout.setBackground(new BitmapDrawable(BitmapFactory
						.decodeFile(loadTime)));
			} else {
				try {
					mainLayout.setBackground(new BitmapDrawable(BitmapFactory
							.decodeStream(getAssets().open(loadTime))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Reminds reminds = null;
		if (!remindfzDB.select().equals("")) {
			reminds = Reminds.parse(remindfzDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindfs.add(reminds.getFileList().get(i));
			}
		}
		if (remindfs.size() == 0) {
			rels.setVisibility(View.INVISIBLE);
		} else {
			fsize.setText(String.valueOf(remindfs.size()));
		}
		if (!remindzjDB.select().equals("")) {
			reminds = Reminds.parse(remindzjDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindzj.add(reminds.getFileList().get(i));
			}
		}
		if (remindzj.size() == 0) {
			rels1.setVisibility(View.INVISIBLE);
		} else {
			hsize.setText(String.valueOf(remindzj.size()));
		}
		if (!remindnewsDB.select().equals("")) {
			reminds = Reminds.parse(remindnewsDB.select());
			for (int i = 0; i < reminds.getFileList().size(); i++) {
				remindnews.add(reminds.getFileList().get(i));
			}
		}
		if (remindnews.size() == 0) {
			rels2.setVisibility(View.INVISIBLE);
		} else {
			nsize.setText(String.valueOf(remindnews.size()));
		}
	}

	@OnClick({ R.id.ima_btn1, R.id.ima_btn2, R.id.right_button, R.id.ima_btn5,
			R.id.ima_btn4, R.id.ima_btn6, R.id.left_button, R.id.remindbtn,
			R.id.ima_btn3 })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ima_btn1:
			startProgressDialog();
			loaddata();
			break;
		case R.id.ima_btn2:
			if (remindfs.size() != 0) {
				remindfzDB.delete();
			}
			Intent search = new Intent(MainActivity.this, RankingActivity.class);
			startActivity(search);
			break;
		case R.id.ima_btn5:
			if (remindnews.size() != 0) {
				remindnewsDB.delete();
			}
			Intent intent1 = new Intent(MainActivity.this, INewsActivity.class);
			startActivity(intent1);
			break;
		case R.id.ima_btn4:
			loadMessageData();
			/*Intent intent2 = new Intent(MainActivity.this, SignActivity.class);
			startActivity(intent2);*/
			break;
		case R.id.ima_btn6:
			Intent intent3 = new Intent(MainActivity.this, GloryActivity.class);
			startActivity(intent3);
			break;
		case R.id.left_button:
			Intent intent4 = new Intent(MainActivity.this, SetActivity.class);
			startActivity(intent4);
			finish();
			break;
		case R.id.remindbtn:
			Intent remind = new Intent(MainActivity.this, RemindActivity.class);
			startActivity(remind);
			break;
		case R.id.ima_btn3:
			if (remindzj.size() != 0) {
				remindzjDB.delete();
			}
			Intent interact = new Intent(MainActivity.this,
					InteractActivity.class);
			startActivity(interact);
			break;
		case R.id.right_button:
			startProgressDialog2();
			deleteTableByDBName(db, table);
			if (timeer == 1) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						timeer = 0;
						try {
							msg.obj = outlogin();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.sendMessage(msg);
					}
				}).start();
			}
			break;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			stopProgressDialog2();
			if (msg.obj != null) {
				Dialog(describe);
			}
		}
	};

	private String outlogin() throws JSONException {
		HttpGet httpRequest = new HttpGet(URLs.ASK_TEACHER +"sessionId="+ Sscion.getSsid());
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			JSONArray jsonArray = new JSONArray(strResult);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject sc = jsonArray.getJSONObject(i);
				JSONObject item = sc.getJSONObject("accessFlag");
				accessFlag = item.getString("flag");
				describe = item.getString("describe");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessFlag;
	}
	String describe;
	protected void Dialog(String event) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(MainActivity.this).setTitle(event)
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						timeer = 1;
						dialog.cancel();
					}
				}).show();
	}
	/**
	 * 请求签到次数
	 */
	public void loadMessageData() {
		HttpUtils http = new HttpUtils();
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("sessionId", Sscion.getSsid());
			http.send(HttpMethod.GET, URLs.qIANDAOTIME, params, new RequestCallBack<String>() {
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					stopProgressDialog();
					try {
						JSONArray jsonObject=new JSONArray(responseInfo.result);
						String aaString=jsonObject.getJSONObject(0).get("loadTimes").toString();
						Intent intent2 = new Intent(MainActivity.this, SignActivity.class);
						intent2.putExtra("times", aaString);
						startActivity(intent2);
						/*JSONObject accessFlagArray = jsonArray.getJSONObject(0).getJSONObject("loadTimes");
						flag=accessFlagArray.getString("flag");
						if (flag.equals("6")) {
						}
						describe=accessFlagArray.getString("describe");*/
					} catch (JsonParseException e) {
						AppException.json(e).makeToast(MainActivity.this);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				@Override
				public void onStart() {
					startProgressDialog();
				}
				@Override
				public void onFailure(HttpException error, String msg) {
					stopProgressDialog();
				}
			});
	}
	public SQLiteDatabase openDBByName(String DBName) {
		SQLiteDatabase db;
		// deleteDBByName(DBName);
		db = this.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
		return db;
	}

	public boolean deleteTableByDBName(String DBName, String TableName) {
		SQLiteDatabase dbDatabase = openDBByName(DBName);
		dbDatabase.delete(TableName, null, null);
		close(dbDatabase);
		return false;
	}

	public void close(SQLiteDatabase db_) {
		db_.close();
	}
	private void loaddata() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				search();
				handler1.sendMessage(msg);
			}
		}).start();
	}
	String result = null;

	private String search() {
		String query = "sessionId=" + Sscion.getSsid() + "&" + "pageno=" + 1;
		HttpGet httpRequest = new HttpGet(URLs.ASK_URL + query);
		String strResult = " ";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			result = JsonUtils.fjson2(strResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			 stopProgressDialog();
			Music1.setRESULTS(result);
			Intent intent = new Intent(MainActivity.this, VidioActivity.class);
			startActivity(intent);
			//text.setText(result);
		}
	};
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
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

	
	private void startProgressDialog2() {
		if (progressDialog2 == null) {
			progressDialog2 = CustomProgressDialog.createDialog(this);
			progressDialog2.setMessage("正在退出...");
		}
		progressDialog2.show();
	}

	private void stopProgressDialog2() {
		if (progressDialog2 != null) {
			progressDialog2.dismiss();
			progressDialog2 = null;
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startProgressDialog2();
			deleteTableByDBName(db, table);
			if (timeer == 1) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						timeer = 0;
						try {
							msg.obj = outlogin();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.sendMessage(msg);
					}
				}).start();
			}
		}
		return false;
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * 
	 * switch (keyCode) { case KeyEvent.KEYCODE_BACK: return true; } return
	 * super.onKeyDown(keyCode, event); }
	 */
}
