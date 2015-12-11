package yunrui.game;


import java.util.Timer;
import java.util.TimerTask;

import yunrui.game.been.Getquestion;
import yunrui.game.util.EquipmentContentProviderUtil;
import yunrui.game.util.HttpUtil;
import yunrui.game.util.JsonUtil;
import yunrui.game.util.QuestionContentProviderUtil;
import yunrui.game.util.ScheduleInfosContentProviderUtil;
import yunrui.game.util.UserInfoContentProviderUtil;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.R;

public class MainActivity extends Activity implements android.view.SurfaceHolder.Callback{
	SurfaceView surfaceView;
	Paint paint=new Paint();
	Bitmap bitmap;
	Context context;
	 int j=0;
	TextView mark;
	 SurfaceHolder surfaceHolder;
	 boolean run = true;
	myhandler myhandler;
	Thread thread;
	SingleSelectionDialog siDialog;
	GameThread gameThread;
	DisplayMetrics dm = new DisplayMetrics();
	int width,height;
	ImageView plaus,topImage;
	RelativeLayout layout;
	boolean isclick=true;
	 int mf=0;
	int h=0;
	int itemIndex;
	TextView nameTextView,updaTextView;
	CustomDialog.Builder builder;
	 RelativeLayout.LayoutParams lp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;//宽度
		height = dm.heightPixels ;//高度
		this.context=this;
		myhandler=new myhandler();
		builder=new CustomDialog.Builder(context);
		surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
		mark=(TextView)findViewById(R.id.mark);
		layout=(RelativeLayout)findViewById(R.id.layout);
		plaus=(ImageView)findViewById(R.id.plaus);
		topImage=(ImageView)findViewById(R.id.topImage);
		nameTextView=(TextView)findViewById(R.id.uesrname);
		updaTextView=(TextView)findViewById(R.id.eupdate);
		nameTextView.setText(Test.realname);
		if (JsonUtil.qList.size()==0) {
			Toast.makeText(MainActivity.this, "没有问题", Toast.LENGTH_SHORT).show();
			finish();
		}
		lp = (RelativeLayout.LayoutParams)surfaceView.getLayoutParams();
		ViewTreeObserver vto2 = layout.getViewTreeObserver();
		
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		
		@Override
		
		public void onGlobalLayout() {
		
			layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			h=layout.getHeight()/30;
			  lp.leftMargin = 0;
			    lp.topMargin = 0;
			    lp.bottomMargin=layout.getBottom();
			    lp.width = layout.getWidth();
			    lp.height = h*30;
			    surfaceView.setLayoutParams(lp);
			    surfaceView.getHeight();
		}
		});
	
		   Getquestion.mfList.clear();
	        Getquestion.questidList.clear();
	        Getquestion.questTFList.clear();
	        Getquestion.questtimeList.clear();
	        Getquestion.questuserList.clear();
	
//		 dialog=onCreateDialog();
		surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		//创建GameThread类实例
		gameThread = new GameThread();
		surfaceView.setZOrderOnTop(true);
		surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		plaus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isclick) {
					plaus.setImageResource(R.drawable.palus1);
					run=false;
					isclick=false;
				}else {
					plaus.setImageResource(R.drawable.plaus);
					run=true;
					surfaceCreated(surfaceHolder);
					isclick=true;
				}
			
			}
		});
		
	}
	private void inDialog(){
	itemIndex=JsonUtil.qList.size();
	Test.answer=JsonUtil.qList.get(zs).getAnswer();
	Getquestion.questidList.add(JsonUtil.qList.get(zs).getId());
	Test.item=new String[JsonUtil.qList.get(zs).getOplists().size()];
	for (int i = 0; i < JsonUtil.qList.get(zs).getOplists().size(); i++) {
		Test.item[i]=i+1+"、"+JsonUtil.qList.get(zs).getOplists().get(i).getQcontext();
	}
	
	siDialog = new SingleSelectionDialog.Builder(this).setTitle(JsonUtil.qList.get(zs).getTitle()).setSingleChoiceItems(
			Test.item,
			itemIndex, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					itemIndex = which;
					
					dialog.dismiss();
				}

			}).create();
}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub\
		//启动gameThread
		try {
			
			bitmap=bitmaputil( BitmapFactory.decodeResource(getResources(), R.drawable.sprite1));
			gameThread = new GameThread();
			 thread=new Thread(gameThread); 
//			 myhandler.post(gameThread);
				 thread.start();
			} catch (Exception e) {
				// TODO: handle exception
				e.toString();
			}
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	int ii = 0;
	int m=0;
	int img=0;
	float btop=0;
	boolean isbg=false;
	boolean imf=false;
	int b=0;
	static int size=JsonUtil.qList.size();
	class GameThread implements Runnable{
		@Override
		public void run() {
		// TODO Auto-generated method stub	
			Message msg=new Message();
		if (zs==size) {
			run=false;
			msg.what=zs;
			if (j==0) {
				Canvas canvas=surfaceHolder.lockCanvas(new Rect(60, 0,surfaceView.getWidth()-60, (surfaceView.getHeight())-bitmap.getHeight()*0));
				 paint = new Paint(); 
				  paint.setAntiAlias(true);//抗锯齿 
		         // canvas 清除画布内容。 
				  paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR)); 
		         canvas.drawPaint(paint); 
		          //canvas 上，多个bitmap相交是的展示方式 
		         paint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
		         canvas.drawARGB(0, 0, 0, 0);
		         surfaceHolder.unlockCanvasAndPost(canvas);
			}
			myhandler.sendMessage(msg);
		 }
		while (imf) {
			
			Canvas canvas=surfaceHolder.lockCanvas(new Rect(60, 0,surfaceView.getWidth()-60, (surfaceView.getHeight())-bitmap.getHeight()*j));
			 paint = new Paint(); 
			  paint.setAntiAlias(true);//抗锯齿 
	         // canvas 清除画布内容。 
			  paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR)); 
	         canvas.drawPaint(paint); 
	          //canvas 上，多个bitmap相交是的展示方式 
	         paint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
	         paint.setTextSize(80);
	         paint.setColor(Color.RED);
	         canvas.drawText("+"+Getquestion.mfList.get(zs-1), surfaceView.getWidth()/2, surfaceView.getHeight()/2-img++*10, paint);
	         if (surfaceView.getHeight()/2-img*10<=0) {
				
				imf=false;
				img=0;
				if (zs==size) {
					run=false;
				}else {
					run=true;
				}
			}
	         surfaceHolder.unlockCanvasAndPost(canvas);
		}
		while(run) {
		Canvas c = null;
	
		try {	
		synchronized (surfaceHolder) {		 
		c = surfaceHolder.lockCanvas(new Rect(60,0,surfaceView.getWidth()-60, (surfaceView.getHeight())-bitmap.getHeight()*j));
		  paint = new Paint(); 
		  paint.setAntiAlias(true);//抗锯齿 
         // canvas 清除画布内容。 
		  paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR)); 
         c.drawPaint(paint); 
          //canvas 上，多个bitmap相交是的展示方式 
         paint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER)); 		
         RectF rect=new RectF();
         rect.left=60;
         rect.right=60+bitmap.getHeight();
         rect.top=ii++*10;
         rect.bottom=ii+bitmap.getHeight();
         Matrix matrix = new Matrix();
         matrix.setTranslate(rect.left, rect.top);
		c.drawBitmap(bitmap, matrix, paint);
		if (rect.top==(surfaceView.getHeight()-bitmap.getHeight()*(j+1))) {
			b++;
			btop=rect.top;
			j++;
			m++;
			msg.arg1=m;
			ii=0;
			isbg=false;
			if (m==6) {
				run=false;
				imf=true;
//				zs++;
				m=0;
				isbg=true;
				myhandler.sendMessage(msg);
			}
		
		}
		
		
		}
		}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} finally {
		if (c != null) {
		surfaceHolder.unlockCanvasAndPost(c);	
		}
		}	
		}
		}
		};

	int mf1=0;
	int zs=0;
	class myhandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg); 
			if (msg.what!=0) {
				myhandler.postAtFrontOfQueue(httpsThread);
				}
//				myhandler.post(httpsThread);
				if (msg.obj!=null) {
					run=false;
				if (msg.obj!=null) {
				switch (JsonUtil.updateFlag) {
				case 1:
					builder.setUpdate("分数降低，装备无变化");
					break;
				case 2:
					builder.setUpdate("分数增高但装备无变化");
					break;
				case 3:
					builder.setUpdate("分数增高，装备更新");
					break;
				}
				
//				builder.setMessage("玩家："+Test.name+"\n"+" 得分："+mf);
				builder.setRos(R.drawable.ic_go1);
				builder.setTitle("恭喜您得了"+mf+"分");
				
					builder.setPositiveButton("下一知识点", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {	
							dialog.dismiss();
							//设置你的操作事项
//							JsonUtil.qList.clear();
//							HttpUtil.getQuset();
//							Intent intent=new Intent(MainActivity.this,MainActivity.class);
//							startActivity(intent);
//							finish();
							System.exit(0);
						}
					});
//				}else {
//				builder.setPositiveButton("返回目录", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {	
//						dialog.dismiss();
//						//设置你的操作事项
//						JsonUtil.qList.clear();
//						finish();
//					}
//				});
//				}
				
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
								finish();
							}
						});
				run=false;
				builder.create().show();
				
//					Toast.makeText(context,JsonUtil.describe, Toast.LENGTH_LONG).show();
				}
			}
			
			 if (msg.arg1==6) {	
				 inDialog();
				 ontStart();
				 siDialog.show();
				 DataUtil.setJ(j);
				DataUtil.setMf(mf);	 
				 diss=true;
				 zs++;
			}
			 if (DataUtil.isClick()) {
				 ontStop();
				 i=0;
//				run=DataUtil.isRun();
				j=DataUtil.getJ();
				mf=DataUtil.getMf();
				DataUtil.setClick(false);
				
				surfaceCreated(surfaceHolder);
				mark.setText(""+(mf));
			}else {
			 if (msg.arg2>=30&&diss) {
				 diss=false;
				 ontStop();
				 msg.arg1=0;
				 siDialog.dismiss();					
//				run=true;
				i=0;
				j=j-0;
				mf+=(0);
				mark.setText(""+(mf));
				surfaceCreated(surfaceHolder);
			}else {
				DataUtil.setI(msg.arg2);
			}
			 }
		    }
}
	boolean diss=false;
	  int i=0;
	  Timer time=null;
		TimerTask   tt=null;
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
						Message msg=myhandler.obtainMessage();
						msg.arg2=i;
						myhandler.sendMessage(msg);
					}
				};
		}
		if (time!=null&&tt!=null) {
			time.schedule(tt, 1000, 1000);
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
	  Thread httpsThread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (Test.ssesionid!=null&&Test.kId!=null&&Test.userid!=null) {
			Message msg=new Message();
			flag= HttpUtil.httpget();
			msg.obj=flag;
			
			if (flag.equals("6")) {
				try {
					ScheduleInfosContentProviderUtil.insert(context);
					UserInfoContentProviderUtil.update(Test.userid, context);
					EquipmentContentProviderUtil.insert(context);
//					QuestionContentProviderUtil.testFind(context);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			if (flag.equals("1")) {
//				Toast.makeText(context, "访问过期！", Toast.LENGTH_LONG).show();
//				finish();
//			}
			myhandler.sendMessage(msg);
			}
		}
	});
		private Bitmap bitmaputil(Bitmap bitmap){
		    int width = bitmap.getWidth();
		    int height = bitmap.getHeight();
		    float scaleWidth = ((float) surfaceView.getWidth()-120) / width;
		    int i=surfaceView.getHeight()/300;
		    float scaleHeight = ((float) i*10) / height;
		    Matrix matrix = new Matrix();
		    matrix.postScale(scaleWidth, scaleHeight);
		    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
		    	      true);
		    float h=bitmap.getHeight();
		    return bitmap;
		}
}
