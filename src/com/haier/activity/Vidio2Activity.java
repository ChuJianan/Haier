package com.haier.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.haier.R;
import com.haier.widgets.MyDialog;
import com.haier.widgets.MyDialog.onDialogClickListener;
import com.lidroid.xutils.view.annotation.event.OnClick;

@SuppressLint("WrongViewCast")
public class Vidio2Activity extends Activity implements onDialogClickListener, OnClickListener{
	private    VideoView  vidioview;
	private    Button     btnjump;
	String VideoPath="";
	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vidio);
		btnjump=(Button)findViewById(R.id.right_button);
		vidioview=(VideoView)findViewById(R.id.vidioView);
	     vidioview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.zhishidian));
		 MediaController mc = new MediaController(this);
		 vidioview.setMediaController(mc);
		// vidioview.setVideoPath(VideoPath);
		 vidioview.requestFocus();
		 vidioview.start();
		 vidioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				new MyDialog(Vidio2Activity.this, R.layout.mydialog,R.style.MyDialog,Vidio2Activity.this).show();
			}
		});
		 btnjump.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vidioview.pause();
				//showGameAlert();
				new MyDialog(Vidio2Activity.this, R.layout.mydialog,R.style.MyDialog, Vidio2Activity.this).show();
			}
		});
	}
	@OnClick({R.id.ok,R.id.second})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok:
			vidioview=null;
			   Intent intent2=new Intent();
               intent2.setClass(Vidio2Activity.this, GameListActivity.class);
               startActivity(intent2);
               finish();
			break;
		case R.id.second:
			vidioview=null;
			   Intent intent21=new Intent();
               intent21.setClass(Vidio2Activity.this, Vidio2Activity.class);
               startActivity(intent21);
               finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDialogViewClick(MyDialog dialog, OnClickListener listener) {
		// TODO Auto-generated method stub
		dialog.findViewById(R.id.ok).setOnClickListener(listener);
		dialog.findViewById(R.id.second).setOnClickListener(listener);
	}
	@SuppressLint("WrongViewCast")
	private void showGameAlert() {
		  AlertDialog dlg = new AlertDialog.Builder(this).create();

		 Window window = dlg.getWindow();
		        // *** 主要就是在这里实现这种效果的.
		        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		 window.setContentView(R.layout.mydialog);
		 dlg.setView(vidioview, 0, 0, 0, 0);   
		 dlg.show();
		        // 为确认按钮添加事件,执行退出应用操作
		 ImageButton ok = (ImageButton) window.findViewById(R.id.ok);
		 ok.setOnClickListener(new View.OnClickListener() {
		  @Override
		public void onClick(View v) {
			  Intent intent2=new Intent();
              intent2.setClass(Vidio2Activity.this, GameListActivity.class);
              startActivity(intent2);
              finish();
		  }
		 });
		        // 关闭alert对话框架
		        ImageButton cancel = (ImageButton) window.findViewById(R.id.second);
		        cancel.setOnClickListener(new View.OnClickListener() {
		   @Override
		public void onClick(View v) {
			   Intent intent21=new Intent();
               intent21.setClass(Vidio2Activity.this, Vidio2Activity.class);
               startActivity(intent21);
               finish();
		  }
		   });
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
}
