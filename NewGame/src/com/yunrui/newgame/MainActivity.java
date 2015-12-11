package com.yunrui.newgame;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunrui.util.CustomDialog;
import com.yunrui.util.EquipmentContentProviderUtil;
import com.yunrui.util.Getquestion;
import com.yunrui.util.HttpUtil;
import com.yunrui.util.JsonUtil;
import com.yunrui.util.Mediplayer;
import com.yunrui.util.QuestionContentProviderUtil;
import com.yunrui.util.RousUtil;
import com.yunrui.util.ScheduleInfosContentProviderUtil;
import com.yunrui.util.TestUtil;
import com.yunrui.util.UserInfoContentProviderUtil;

public class MainActivity extends Activity implements OnClickListener {

	GameView gameView;
	DisplayMetrics dm=new DisplayMetrics();
	TextView mfTextView,timerTextView,name;
	RelativeLayout rlt;
	int width,height;
	ImageView imga,imgb,imgc,imgd;
	Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;
        context=this;
        TestUtil.zs=0;
        RousUtil.mf=0;
        Getquestion.mfList.clear();
        Getquestion.questidList.clear();
        Getquestion.questTFList.clear();
        Getquestion.questtimeList.clear();
        Getquestion.questuserList.clear();
        builder=new CustomDialog.Builder(this);
        gameView=(GameView)findViewById(R.id.gameView);
        mfTextView=(TextView)findViewById(R.id.mftext);
        timerTextView=(TextView)findViewById(R.id.timertext);
        rlt=(RelativeLayout)findViewById(R.id.rlt);
        name=(TextView)findViewById(R.id.name);
        name.setText(TestUtil.name);
        imga=(ImageView)findViewById(R.id.a);
        imgb=(ImageView)findViewById(R.id.b);
        imgc=(ImageView)findViewById(R.id.c);
        imgd=(ImageView)findViewById(R.id.d);
        imga.setOnClickListener(this);
        imgb.setOnClickListener(this);
        imgc.setOnClickListener(this);
        imgd.setOnClickListener(this);
        if (JsonUtil.qList.size()==0) {
			Toast.makeText(MainActivity.this, "没有问题", Toast.LENGTH_SHORT).show();
			finish();
		}
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ontStart();
        
    }
    private MediaPlayer soundPool;
    boolean istrue=false;
	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
	switch (arg0.getId()) {
	case R.id.a:
		istrue(1);
		break;
	case R.id.b:
		istrue(2);
		break;
	case R.id.c:
		istrue(3);
		break;
	case R.id.d:
		istrue(4);
		break;
	default:
		break;
	}
	}
	CustomDialog.Builder builder;
	protected boolean run=true;
	Handler myHandler=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
			if (msg.arg2>30) {
				ontStop();
				i=0;
				gameView.invalidate();
				timerTextView.setText("时间："+0);
				TestUtil.zs++;
				ontStart();
				
			}else {
				timerTextView.setText("时间："+msg.arg2);
			}
			if (TestUtil.zs==com.yunrui.util.JsonUtil.qList.size()&&run) {
				ontStop();
				myHandler.postAtFrontOfQueue(runnable);
			}
			if (flag!=null) {
				
			run=false;
			if (msg.obj!=null) {
				
				switch (JsonUtil.updateFlag) {
				case 1:
					builder.setUpdate("分数降低，装备无变化");
					break;
				case 2:
					builder.setUpdate("分数增加，无装备变化");
					break;
				case 3:
					builder.setUpdate("分数增加，获得新装备");
					break;
				}
//				builder.setMessage("玩家："+TestUtil.name+"\n"+" 得分："+RousUtil.mf);
				builder.setRos(R.drawable.ic_go1);
				builder.setTitle("恭喜您获得了"+RousUtil.mf+"分");
				if (JsonUtil.pass==2) {
					builder.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {	
							dialog.dismiss();
							//设置你的操作事项
							JsonUtil.qList.clear();
							HttpUtil.getQuset();
							Intent intent=new Intent(MainActivity.this,MainActivity.class);
							startActivity(intent);
							finish();
						}
					});
				}else {
				builder.setPositiveButton("返回目录", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {	
						dialog.dismiss();
						//设置你的操作事项
						JsonUtil.qList.clear();
						finish();
					}
				});
				}

				builder.setNegativeButton("再来一次",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								JsonUtil.qList.clear();
								try {
									QuestionContentProviderUtil.testFind(context);
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Intent intent=new Intent(MainActivity.this,MainActivity.class);
								startActivity(intent);
								TestUtil.zs=0;
								finish();
							}
						});

				builder.create().show();
			
				}
			}
			
		}
	};
	Timer time=null;
	TimerTask   tt=null;
	int i=0;
  private void ontStart(){
	  if (time==null) {
		time=new Timer();
	}
	  if (tt==null) {
			tt=new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					i++;					
					Message msg=myHandler.obtainMessage();
					msg.arg2=i;
					myHandler.sendMessage(msg);
				}
			};
	}
	if (time!=null&&tt!=null) {
		time.schedule(tt, 0, 1000);
	}
  }
  private void ontStop(){
	  
	  if (time!=null) {
		time.cancel();
		time=null;
	}
	  if (tt!=null) {
		tt.cancel();
		tt=null;
	}
  }
  String flag=null;
//  class httpsThread implements Runnable {
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
////			if (TestUtil.ssid!=null&&TestUtil.kid!=null&&TestUtil.userid!=null) {
//				
//			
//			Message msg=new Message();
//			flag= HttpUtil.httpget();
//			msg.obj=flag;
////			EquipmentContentProviderUtil.insert(context);
//			try {
////				QuestionContentProviderUtil.testFind(context);
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (flag!=null&&flag.equals("6")) {
//				ScheduleInfosContentProviderUtil.insert(context);
//				UserInfoContentProviderUtil.update(TestUtil.userid, context);
//			}
//			myHandler.sendMessage(msg);
//			}
////		}
//  }
  Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        // TODO: http request.
	    	if (TestUtil.ssid!=null&&TestUtil.kid!=null&&TestUtil.userid!=null) {
				Message msg=new Message();
				
				if (msg.obj==null) {
					flag= HttpUtil.httpget();
					msg.obj=flag;
				}
				
				if (flag!=null&&flag.equals("6")) {
					try {
						ScheduleInfosContentProviderUtil.insert(context);
						UserInfoContentProviderUtil.update("1", context);
						EquipmentContentProviderUtil.insert(context);
						QuestionContentProviderUtil.testFind(context);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (flag.equals("1")) {
					Toast.makeText(context, "访问过期！", Toast.LENGTH_LONG).show();
					MainActivity.this.finish();
					onDestroy();
				}
				
				myHandler.sendMessage(msg);
				}
			}
	    
	};
	private void istrue(int a){
		
		RousUtil.i=i;
		ontStop();
		timerTextView.setText("时间："+i);
		Getquestion.questtimeList.add(i);
		Getquestion.questuserList.add(a);
		i=0;
		istrue=RousUtil.roustest(a);
		if (istrue) {
			Getquestion.questTFList.add("1");
			if (!TestUtil.music2) {
				soundPool=Mediplayer.truesound(context);
				soundPool.start();
			}
			
		}else {
			Getquestion.questTFList.add("0");
			if (!TestUtil.music2) {
				soundPool=Mediplayer.falsePlayer(context);
				soundPool.start();
			}
		
		}
		mfTextView.setText("得分："+RousUtil.mf);
		Getquestion.mfList.add(RousUtil.mf);
		gameView.isTrue();
		start();
		ontStart();
	}
	TextView textView;
	private void start() {
		// TODO Auto-generated method stub
		textView=new TextView(MainActivity.this);
		textView.setTextColor(Color.RED);
		textView.setTextSize(50);
		textView.setX(gameView.getWidth()/2);
		textView.setY(gameView.getHeight()/2);
		textView.setText("+"+RousUtil.imf);
		rlt.addView(textView);
		final Animation translateAnimation = new TranslateAnimation(0,
				0, 0, 0);
			translateAnimation.setDuration(600); // 设置动画持续时间
			translateAnimation.setFillAfter(true);
			translateAnimation.setFillEnabled(true);
			translateAnimation.setInterpolator(new LinearInterpolator());
			textView.setAnimation(translateAnimation); // 设置动画效果
			translateAnimation.startNow();
		translateAnimation.setAnimationListener(new AnimationListener(){
			 @Override
                public void onAnimationStart(Animation animation) {
                //动画开始时
				// getQuset();
                }
                //当动画重复播放时的事件
                @Override
                public void onAnimationRepeat(Animation animation) {      
                	
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    //动画结束时
                	rlt.removeView(textView);
                	gameView.postInvalidate();
                }
		});
	}
}
