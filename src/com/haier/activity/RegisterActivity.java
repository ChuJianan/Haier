package com.haier.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.haier.R;
import com.haier.app.AppClient;
import com.haier.app.AppException;
import com.haier.bean.RegisterInfo;
import com.haier.bean.Registers;
import com.haier.bean.Sscion;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.utils.ValidateUtils;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/**
 * 首次登陆的注册页面
 * @author cjn
 *
 */
public class RegisterActivity extends Activity implements OnClickListener{
	 @ViewInject(R.id.right_button)         private Button      btnok;
	 @ViewInject(R.id.editpwd) private EditText editpwd;
	 @ViewInject(R.id.cfeditpwd)private EditText editpwdagn;
	 @ViewInject(R.id.rgspinner)private Spinner rgsSpinner;
	 @ViewInject(R.id.editquestion)private EditText equestion;
	 private  SpinnerAdapter spinnerAdapter;
	 private Context context;
	 private String  pwd;
	 private String pwdaginString;
	 private String equestiojn;
	 private List<RegisterInfo> reList= new ArrayList<RegisterInfo>();
	 private CustomProgressDialog progressDialog = null;
	 @Override
	    public void onCreate(Bundle savedInstanceState){
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        ViewUtils.inject(this);
	        context=this;
	        initView();
	        loadDatas();
	 }
private void initView() {
		// TODO Auto-generated method stub
		spinnerAdapter=new SpinnerAdapter(this);
		rgsSpinner.setAdapter(spinnerAdapter);
		
		rgsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
			
		});
			
			
	}
/**
 * 获取数据
 */
	private void loadDatas(){
		// TODO Auto-generated method stub
		try {
			Registers list=Registers.parse(AppClient.httpget(Sscion.getSsid()));
			reList.addAll(list.getFileList());
			spinnerAdapter.setData(reList);
			spinnerAdapter.notifyDataSetChanged();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick({ R.id.right_button})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.right_button:
			
			if (editpwd.getText().toString().equals(editpwdagn.getText().toString())) {
				switch (ValidateUtils.ValidatePassword(editpwd.getText().toString())) {
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
						
						break;
				}
			}else {
				UIHelper.ToastMessage(context, "两次输入的密码不一致！");
			}
			
			break;
		}
	}
	Handler handler=new Handler(){
		@Override 
		public void handleMessage(Message msg) {
			stopProgressDialog();
			if (msg.obj.equals("10")) {
				UIHelper.ToastMessage(context, JsonUtils.describe);
				Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	};
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
	private String getData() {
		// TODO Auto-generated method stub
		try {
			pwd = URLEncoder.encode(editpwd.getText().toString(), "utf-8");
			pwdaginString = URLEncoder.encode(editpwdagn.getText().toString(), "utf-8");
			equestiojn = URLEncoder.encode(equestion.getText().toString(), "utf-8");
 	    } catch (UnsupportedEncodingException e) {
 	        e.printStackTrace();
 	    }
		String flag=AppClient.httpget(Sscion.getSsid(), pwd,
				pwdaginString,getKnowledgeId() , equestiojn);
		return flag;
	}
	/**
	 * 获取当前选择模块ID
	 * @return
	 */
	public long getKnowledgeId() {
		return rgsSpinner.getSelectedItemId();
	}
	
	public class SpinnerAdapter extends BaseAdapter {
		private Context mContext;
		/**
		 * 数据
		 */
		private List<RegisterInfo> data = Collections.emptyList();
		
		public SpinnerAdapter(Context context) {
			this.mContext = context;
		}
		public SpinnerAdapter(Context context, List<RegisterInfo> data) {
			this.mContext = context;
			this.data = data;
		}
		
		/**
		 * 设置数据
		 * @param data
		 */
		public void setData(List<RegisterInfo> data) {
			this.data = data;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txt;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
				txt = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(txt);
			} else {
				txt = (TextView) convertView.getTag();
			}
			RegisterInfo kl = data.get(position);
			txt.setText(kl.getName());
			return convertView;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.valueOf(data.get(position).getValue());
		}
	}
}
