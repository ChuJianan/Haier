package com.haier.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.haier.bean.Music1;
import com.haier.bean.RegisterInfo;
import com.haier.bean.Registers;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FindPwd extends BaseActivity  implements OnClickListener{
	@ViewInject(R.id.right_button)   private Button btnnext;
	@ViewInject(R.id.left_button)    private Button btnback;
	@ViewInject(R.id.editname)       private EditText username;
	@ViewInject(R.id.editquestion)   private EditText anwser;
	@ViewInject(R.id.spinner1)       private Spinner qsSpinner;
	 private  SpinnerAdapter spinnerAdapter;
	 private Context context;
	 private List<RegisterInfo> reList= new ArrayList<RegisterInfo>();
	 private CustomProgressDialog progressDialog = null;
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        ViewUtils.inject(this);
        context=this;
        initView();
        loadDatas();
        }
	private void loadDatas() {
		// TODO Auto-generated method stub
		try {
			Registers list=Registers.parse(AppClient.httpgetpw());
			reList.addAll(list.getFileList());
			spinnerAdapter.setData(reList);
			spinnerAdapter.notifyDataSetChanged();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		spinnerAdapter=new SpinnerAdapter(this);
		qsSpinner.setAdapter(spinnerAdapter);
		
		qsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
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


	@OnClick({R.id.right_button,R.id.left_button})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.right_button:
			if(username.getText().toString().equals("")||anwser.getText().toString().equals("")){
				UIHelper.ToastMessage(context, "用户名或密保答案不能为空！");
			}else{
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
			break;
		case R.id.left_button:
			finish();
		}
	}
	Handler handler=new Handler(){
		@Override 
		public void handleMessage(Message msg) {
			stopProgressDialog();
			if (msg.obj.equals("6")) {
				Music1.setUserName(username.getText().toString());
				UIHelper.ToastMessage(context, JsonUtils.describe);
				Intent intent=new Intent(FindPwd.this,NewPwdActivity.class);
				startActivity(intent);
				finish();
			}else{
				UIHelper.ToastMessage(context, JsonUtils.describe);
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
		String flag=AppClient.httptest(username.getText().toString(),
				getKnowledgeId(), anwser.getText().toString());
		return flag;
	}
	/**
	 * 获取当前选择模块ID
	 * @return
	 */
	public long getKnowledgeId() {
		return qsSpinner.getSelectedItemId();
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
