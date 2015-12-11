package com.haier.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.R;
import com.haier.app.AppClient;
import com.haier.bean.Sscion;
import com.haier.bean.UserInfo;
import com.haier.db.UserDB;
import com.haier.utils.JsonUtils;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SignActivity extends Activity implements OnClickListener{
	@ViewInject(R.id.sign_one) ImageView sign_one;
	@ViewInject(R.id.sign_two) ImageView sign_two;
	@ViewInject(R.id.sign_three) ImageView sign_three;
	@ViewInject(R.id.sign_frou) ImageView sign_frou;
	@ViewInject(R.id.sign_five) ImageView sign_five;
	@ViewInject(R.id.sign_six) ImageView sign_six;
	@ViewInject(R.id.sign_btn) ImageButton sign_btn;
	@ViewInject(R.id.back) ImageButton backButton;
	@ViewInject(R.id.top_title)TextView top_title;
	@ViewInject(R.id.ask)Button ask;
	private CustomProgressDialog progressDialog = null;
	UserDB userDB=new UserDB(this);
	UserInfo userInfo=new UserInfo();
	String flag;
	SharedPreferences user;
	int loadTime=0;
	String timeString;//签到次数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		ViewUtils.inject(this);
		top_title.setText("微签到");
		/*user=getSharedPreferences("user_info",0);
		loadTime=user.getInt("name", 0);
		if (loadTime!=0) {
			sign(String.valueOf(loadTime));
		}*/
		Intent intent=getIntent();
		timeString=	intent.getStringExtra("times");
		sign(timeString);
		String[] a=userDB.select1();
		userInfo.setUserName(a[1]);
		userInfo.setTotalScore(a[2]);
		userInfo.setSessionId(Sscion.getSsid());
		ask.setVisibility(View.INVISIBLE);
	}
	@OnClick({R.id.sign_btn,R.id.back})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sign_btn:
			loadData();
			break;
		case R.id.back:
			finish();
			break;
		}
	}
	 private void startProgressDialog(){
	   		if (progressDialog == null){
	   			progressDialog = CustomProgressDialog.createDialog(this);
	   	    	progressDialog.setMessage("正在加载中...");
	   		}
	       	progressDialog.show();
	   	}
	       private void stopProgressDialog(){
	   		if (progressDialog != null){
	   			progressDialog.dismiss();
	   			progressDialog = null;
	   		}
	   	}
	private void loadData() {
		// TODO Auto-generated method stub
		startProgressDialog();
		dosign();
	}
	Handler handler=new Handler(){
		@Override
		 public void handleMessage(android.os.Message msg) {
			 if (msg.obj!=null) {
				 stopProgressDialog();
				 if (msg.obj.equals("6")&&JsonUtils.loadTime!=null) {
					 Editor editor=user.edit();
						editor.putInt("name", Integer.parseInt(JsonUtils.loadTime));
						editor.commit();
					 userInfo.setTotalScore(String.valueOf((JsonUtils.jifen+Integer.parseInt( userInfo.getTotalScore()))));
				 sign(JsonUtils.loadTime);
				 }
				 if (msg.obj.equals("12")) {
						Toast.makeText(SignActivity .this,"今天已经签到！", Toast.LENGTH_LONG).show();
					}
				}
		 }
		};

		public void sign(String flag) {
			// TODO Auto-generated method stub
					switch (Integer.parseInt(flag)) {
					case 1:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						break;
					case 2:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						sign_two.setImageResource(R.drawable.ic_sign_two);
						break;
					case 3:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						sign_two.setImageResource(R.drawable.ic_sign_two);
						sign_three.setImageResource(R.drawable.ic_sign_three);
						break;
					case 4:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						sign_two.setImageResource(R.drawable.ic_sign_two);
						sign_three.setImageResource(R.drawable.ic_sign_three);
						sign_frou.setImageResource(R.drawable.ic_sign_frou);
						break;
					case 5:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						sign_two.setImageResource(R.drawable.ic_sign_two);
						sign_three.setImageResource(R.drawable.ic_sign_three);
						sign_frou.setImageResource(R.drawable.ic_sign_frou);
						sign_five.setImageResource(R.drawable.ic_sign_five);
						break;
					case 6:
						sign_one.setImageResource(R.drawable.ic_sign_one);
						sign_two.setImageResource(R.drawable.ic_sign_two);
						sign_three.setImageResource(R.drawable.ic_sign_three);
						sign_frou.setImageResource(R.drawable.ic_sign_frou);
						sign_five.setImageResource(R.drawable.ic_sign_five);
						sign_six.setImageResource(R.drawable.ic_sign_six);
						break;
					default:
						break;
					}
		}
	
	private void dosign() {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable(){
			 @Override 
			public void run(){
				 Message msg=new Message();
				 msg.obj=AppClient.sign(userInfo.getSessionId());
				 handler.sendMessage(msg);
			}
		}).start();
	}

}