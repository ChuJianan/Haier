package yunrui.game;

import java.util.ArrayList;

import yunrui.game.util.JsonUtil;
import yunrui.game.util.QuestionContentProviderUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.game.R;

public class IndexActivity extends Activity{

	ImageView newgame,about,exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);
		inView();
		getParameterByIntent();
		JsonUtil.qList.clear();
		try {
			QuestionContentProviderUtil.testFind(this);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newgame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (JsonUtil.qList.size()==0||Test.ssesionid==null) {
				Toast.makeText(IndexActivity.this, "没有登陆，或者没有测试题目", Toast.LENGTH_SHORT).show();	
				}else {
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
	private void inView() {
		// TODO Auto-generated method stub
		newgame=(ImageView)findViewById(R.id.newgame);
		about=(ImageView)findViewById(R.id.about);
		exit=(ImageView)findViewById(R.id.exit);
	}
	public void getParameterByIntent() {
		   Intent mIntent = this.getIntent();
		   Test.ssesionid = mIntent.getStringExtra("sscion");
		   Test.kId=mIntent.getStringExtra("id");
		   Test.name=mIntent.getStringExtra("username");
		   Test.userid=mIntent.getStringExtra("userid");
		   Test.realname=mIntent.getStringExtra("realname");
		   if (Test.ssesionid!=null) {
			   Test.music1=mIntent.getExtras().getBoolean("music1");
			   Test.music2=mIntent.getExtras().getBoolean("music2");
		   }
//		   Toast.makeText(this, Test.ssesionid+"!"+Test.kId+"!"+Test.name+"!"+Test.userid, 1000).show();
		 }
	
}
