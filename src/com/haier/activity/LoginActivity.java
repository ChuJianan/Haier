package com.haier.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.R;
import com.haier.app.AppContext;
import com.haier.bean.URLs;
import com.haier.db.AccessFlagDB;
import com.haier.db.DBServer;
import com.haier.db.EquipmentDB;
import com.haier.db.KnowledgeDB;
import com.haier.db.RemindDB;
import com.haier.db.RemindfzDB;
import com.haier.db.RemindmeDB;
import com.haier.db.RemindnewsDB;
import com.haier.db.RemindzjDB;
import com.haier.db.ScheduleDB;
import com.haier.db.UserDB;
import com.haier.utils.JsonUtils;
import com.haier.utils.StringUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.networkbench.agent.impl.NBSAppAgent;

/**
 * login
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity implements OnClickListener{
    @ViewInject(R.id.login)         private Button      mLogin;
    @ViewInject(R.id.btn_forget)    private Button      findpwd;
    @ViewInject(R.id.message)       private TextView    mMessage;       
    @ViewInject(R.id.username)      private EditText    mUserName;      //用户名
    @ViewInject(R.id.password)      private EditText    mPassword;      //密码
    AppContext appContext;//全局Context
    private static final String LOG_TAG="LoginActivity";
    private CustomProgressDialog progressDialog = null;
    private DBServer db;
    UserDB userDB;
    ScheduleDB scheduleDB;
    AccessFlagDB accessFlagDB;
    EquipmentDB equimentDB;
    KnowledgeDB knowledgeDB;
    RemindDB remindDB;
    String username;
    String password ;
    String pas;
    int net=0;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        NBSAppAgent.setLicenseKey("d8bd3a33837242539d426e102f443369").withLocationServiceEnabled(true).start(this);
		db=new DBServer(this);
		scheduleDB=new ScheduleDB(this);
		accessFlagDB=new AccessFlagDB(this);
		userDB=new UserDB(this);
		equimentDB=new EquipmentDB(this);
		knowledgeDB=new KnowledgeDB(this);
		remindDB=new RemindDB(this);
		JsonUtils.knowledgeDB=knowledgeDB;
		JsonUtils.userDB=userDB;
		JsonUtils.accessFlagDB =accessFlagDB;
		JsonUtils.scheduleDB=scheduleDB;
		JsonUtils.equimentDB=equimentDB;
		JsonUtils.reminddb=remindDB;
		JsonUtils.remindfzDB=new RemindfzDB(this);
		JsonUtils.remindmeDB=new RemindmeDB(this);
		JsonUtils.remindnewsDB=new RemindnewsDB(this);
		JsonUtils.remindzjDB=new RemindzjDB(this);
    }

@OnClick({ R.id.login, R.id.btn_forget })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			username = mUserName.getText().toString().trim();
			password = mPassword.getText().toString().trim();
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				UIHelper.ToastMessage(LoginActivity.this, "学号或密码不能为空！");
			} else {
				checkNewWorkStatus();
				Login();
			}
			break;
		case R.id.btn_forget:
			Intent intent2 = new Intent();
			intent2.setClass(LoginActivity.this, FindPwd.class);
			startActivity(intent2);
			break;
		}
	}

	private void Login() {
		// TODO Auto-generated method stub
		if (net == 1) {
			startProgressDialog();
			net = 0;
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();
					msg.obj = doLogin();
					handler.sendMessage(msg);
				}
			}).start();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj != null) {
				if (msg.obj.equals("6")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				} else if (msg.obj.equals("5")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("4")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("3")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("2")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("1")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("7")) {
					stopProgressDialog();
					UIHelper.ToastMessage(LoginActivity.this,
							accessFlagDB.select1(msg.obj));
				} else if (msg.obj.equals("9")) {
					Dialog();
				}
			} else {
				stopProgressDialog();
				Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_LONG)
						.show();
			}
		}
	};
	String result = null;
	private String doLogin() {
		String query = "username=" + username + "&password=" + password;
		HttpGet httpRequest = new HttpGet(URLs.API_URL + query);
		String strResult =" ";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			result = JsonUtils.fromJson(strResult);
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
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage("正在登录...");
		}
		progressDialog.show();
	}

	private void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private void checkNewWorkStatus() {
		boolean netStatus = false;
		boolean isConnected = false;
		ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		connectManager.getActiveNetworkInfo();

		NetworkInfo activeInfo = connectManager.getActiveNetworkInfo();
		NetworkInfo mobInfo = connectManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (activeInfo != null) {
			netStatus = activeInfo.isAvailable();
			isConnected = activeInfo.isConnected();
		}
		Log.e(LOG_TAG, "当前网络是否可用:" + netStatus + ",connected:" + isConnected
				+ ",mobInfo:" + mobInfo);
		if (!netStatus) {
			UIHelper.ToastMessage(this, "网络连接不可用！");
		} else {
			net = 1;
		}
	}

	protected void Dialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(LoginActivity.this)
				.setMessage("请修改密码设置密保问题后登陆！")
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent2 = new Intent();
						intent2.setClass(LoginActivity.this,
								RegisterActivity.class);
						startActivity(intent2);
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
}