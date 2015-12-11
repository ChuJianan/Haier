package com.haier.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haier.R;
import com.haier.app.AppClient;
import com.haier.bean.Music1;
import com.haier.bean.Sscion;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.utils.ValidateUtils;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class NewPwdActivity extends Activity  implements OnClickListener{
	@ViewInject(R.id.right_button)   private Button btnnext;
	@ViewInject(R.id.left_button)   private Button btnback;
	@ViewInject(R.id.pw)   private EditText pw;
	@ViewInject(R.id.newpw)   private EditText newpw;
	Context context;
	private CustomProgressDialog progressDialog = null;
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);
        context=this;
        ViewUtils.inject(this);
        }
	@OnClick({R.id.right_button,R.id.left_button})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.right_button:
			if (pw.getText().toString().equals(newpw.getText().toString())) {
				switch (ValidateUtils.ValidatePassword(newpw.getText().toString())) {
					case 1:
						UIHelper.ToastMessage(context, "密码不能为空！");
						break;
					case 2:
						UIHelper.ToastMessage(context, "密码长度为8-12位");
						break;
					case 3:
						UIHelper.ToastMessage(context, "存在特殊字符");
						break;
					case 4:
						startProgressDialog();
						new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Message msg=new Message();
								msg.obj=getData();
								handler.sendMessage(msg);
							}
						}).start();;
				}
			}else {
				UIHelper.ToastMessage(context, "两次输入的密码不一致！");
			}
			break;
		case R.id.left_button:
			Intent intent2=new Intent();
            intent2.setClass(NewPwdActivity.this, FindPwd.class);
            startActivity(intent2);
			finish();
		}
	}
	Handler handler=new Handler(){
		@Override 
		public void handleMessage(Message msg) {
			stopProgressDialog();
			if (msg.obj.equals("6")) {
				UIHelper.ToastMessage(context, JsonUtils.describe);
				Intent intent=new Intent(NewPwdActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}else{
				UIHelper.ToastMessage(context, JsonUtils.describe);
			}
		}
	};
	private String getData() {
		// TODO Auto-generated method stub
		String flag=AppClient.httptopw(Music1.getUserName(), pw.getText().toString(),
				newpw.getText().toString());
		return flag;
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
	
	
}
