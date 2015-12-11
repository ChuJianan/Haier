package com.haier.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.haier.R;
import com.haier.bean.Music1;

public class VidioActivity extends Activity{
	private   VideoView vidioview;
	private   Button    btnjump;
	private String VideoPath = "";  
	private String roleflag;
	String na ;
	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vidio);
		btnjump=(Button)findViewById(R.id.right_button);
		vidioview=(VideoView)findViewById(R.id.vidioView);
		 Intent intent = this.getIntent();
	     na = intent.getStringExtra("na");
	     roleflag=Music1.getRoleflag();
	     vidioview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.beijing));
	     
		 MediaController mc = new MediaController(this);
		 vidioview.setMediaController(mc);
		// vidioview.setVideoPath(VideoPath);
		 vidioview.requestFocus();
		 vidioview.start();
		 vidioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
				if (roleflag.equals("1")) {
					Intent intent=new Intent(VidioActivity.this, WeiGameActivity.class);
					startActivity(intent);
					finish();
				}else if (roleflag.equals("2")||roleflag.equals("3")) {
					Intent intent=new Intent(VidioActivity.this, WeiHaiKaSaActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
		 btnjump.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vidioview.pause();
				vidioview=null;
				if (roleflag.equals("1")) {
					Intent intent=new Intent(VidioActivity.this, WeiGameActivity.class);
					startActivity(intent);
					finish();
				}else if (roleflag.equals("2")||roleflag.equals("3")) {
					Intent intent=new Intent(VidioActivity.this, WeiHaiKaSaActivity.class);
					startActivity(intent);
					finish();
				}
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