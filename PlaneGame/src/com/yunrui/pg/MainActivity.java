package com.yunrui.pg;

import yunrui.game.util.JsonUtil;
import yunrui.game.util.QuestionContentProviderUtil;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static MainActivity instance;
	MySurfaceView mySurfaceView=null;
	// ����mideaPlayer��SoundPool
	MediaPlayer mMediaPlayer;
	//�������Ƕ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		//����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��ʾ�Զ����SurfaceView��ͼ
		mySurfaceView=new MySurfaceView(this);
		setContentView(mySurfaceView);
		// ��ʼ������
		mMediaPlayer = MediaPlayer.create(this, R.raw.action_world1);
		getParameterByIntent();
		JsonUtil.qList.clear();
		try {
			QuestionContentProviderUtil.testFind(this);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		   if(Test.music1){
			   mMediaPlayer.start();
		   }
		   Toast.makeText(this, Test.ssesionid+"!"+Test.kId+"!"+Test.name+"!"+Test.userid, 1000).show();
		 }
	public void startActivity() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,MainActivity.class);
		startActivity(intent);
	}

}