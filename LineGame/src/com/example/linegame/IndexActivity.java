package com.example.linegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yunrui.util.JsonUtil;
import com.yunrui.util.QuestionContentProviderUtil;
import com.yunrui.util.TestUtil;

public class IndexActivity extends Activity {

	ImageView btnstar,btnexit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		btnstar=(ImageView)findViewById(R.id.star);
		btnexit=(ImageView)findViewById(R.id.exit);
		btnstar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if ( TestUtil.ssid==null|| TestUtil.ssid.equals("")) {
					Toast.makeText(getBaseContext(), "ƒ„Œ¥µ«¬º£°", Toast.LENGTH_LONG).show();
				}else{
					if (JsonUtil.qList.size()==0) {
						Toast.makeText(getBaseContext(), "√ª”– ‘Ã‚£°", Toast.LENGTH_LONG).show();
					}else {
						Intent intent=new Intent(IndexActivity.this,MainActivity.class);
						startActivity(intent);
					}
				
				}
			}
		});
		btnexit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		getParameterByIntent();
		JsonUtil.qList.clear();
		try {
			QuestionContentProviderUtil.testFind(this);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getParameterByIntent() {
		// TODO Auto-generated method stub
		Intent mIntent = this.getIntent();
		Bundle bundle=this.getIntent().getExtras();
		   TestUtil.ssid = mIntent.getStringExtra("sscion");
		   TestUtil.kid=mIntent.getStringExtra("id");
		   TestUtil.name=mIntent.getStringExtra("username");
		   TestUtil.userid=mIntent.getStringExtra("userid");
		   TestUtil.realname=mIntent.getStringExtra("realname");
		   if (TestUtil.ssid!=null) {
			   TestUtil.music1=bundle.getBoolean("music1");
			   TestUtil.music2=bundle.getBoolean("music1");
		   }
//		   Toast.makeText(this, TestUtil.ssid+"!"+TestUtil.kid+"!"+TestUtil.name+"!"+TestUtil.userid, 1000).show();
	}
	
}
