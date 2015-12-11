package yunrui.game;




import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.game.R;

public class GameSufaceView extends SurfaceView implements android.view.SurfaceHolder.Callback{

	public static final String tag = "GameView";
	float a=100;
//	GameThread gameThread;
	Paint paint=new Paint();
	Bitmap bitmap,bgbitmap;
	Context context;
	int j=0;
	SurfaceHolder surfaceHolder = getHolder();
	boolean run = true;
	myhandler myhandler;
	Thread thread;
	GameThread gameThread;
	public GameSufaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context=context;
		myhandler=new myhandler();
//		SurfaceHolder surfaceHolder = getHolder();
		//��ӻص�����
		surfaceHolder.addCallback(this);
		//����GameThread��ʵ��
		gameThread = new GameThread();
		 thread=new Thread(gameThread);  
         textView=new TextView(context);
		paint.setColor(Color.WHITE);
	
	}
	public GameSufaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		myhandler=new myhandler();
//		SurfaceHolder surfaceHolder = getHolder();
		//��ӻص�����
		surfaceHolder.addCallback(this);
		//����GameThread��ʵ��
		gameThread = new GameThread();
		 thread=new Thread(gameThread);  
		   textView=new TextView(context);
		paint.setTextSize(a);
		paint.setColor(Color.RED);
		
	}
	public GameSufaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		   textView=new TextView(context);
		//��ӻص�����
		surfaceHolder.addCallback(this);
		//����GameThread��ʵ��
		myhandler=new myhandler();
		gameThread = new GameThread();
		 thread=new Thread(gameThread);  
		
		paint.setTextSize(a);
		paint.setColor(Color.RED);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v(tag, "surfaceCreated");
		//����gameThread
		try {
		bitmap=bitmaputil( BitmapFactory.decodeResource(getResources(), R.drawable.sprite1));
		bgbitmap=bgbitmaputil( BitmapFactory.decodeResource(getResources(), R.drawable.background));
			gameThread = new GameThread();
			 thread=new Thread(gameThread);  
			 thread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.toString();
		}
	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v(tag, "surfaceDestroyed");
		//ͨ�����run()����ķ�������gameThread�����GameThread��Ķ���
		run = false;
		thread.interrupt();
	}
	
	boolean isbg=false;
	class GameThread implements Runnable{
		@Override
		public void run() {
		// TODO Auto-generated method stub
		float i = 9;
		int m=0;
		
		 if (zs==5) {
				run=false;		
		}
		Message msg=new Message();
		while(run) {
		Log.v(tag, "GameThread");
		Canvas c = null;
		try {
			
		synchronized (surfaceHolder) {		 
		c = surfaceHolder.lockCanvas(new Rect(60, 100, getWidth()-60, (getHeight())-145-bitmap.getHeight()*j));
	
		c.drawBitmap(bgbitmap, 0, 0, paint);
		
		
		
		c.drawBitmap(bitmap, 60, i++*10, paint);
		
		if (i*10>=(getHeight()-145-bitmap.getHeight()*(j+1))) {
			j++;
			m++;
//			b.putInt("color", j);
			msg.arg1=m;
			i=9;
			
			isbg=false;
			if (m==6) {
				run=false;
				zs++;
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
		
	protected Dialog onCreateDialog() {	
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(Test.title)
		       .setIcon(android.R.drawable.ic_dialog_info) .setCancelable(false)
		       .setItems(Test.item,  
		    		   new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int which) {		   
		    	   onStop();
		    	   if (Test.dotest(which)) {
	        		if (i>0&&i<=5) {
	    				run=true;
	    				 i=0;
	    				j=j-6;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			if (i>5&&i<=10) {
	    				run=true;
	    				 i=0;
	    				j=j-5;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			if (i>10&&i<=15) {
	    				run=true;
	    				 i=0;
	    				j=j-4;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			if (i>15&&i<=20) {
	    				run=true;
	    				 i=0;
	    				j=j-3;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			if (i>20&&i<=25) {
	    				run=true;
	    				 i=0;
	    				j=j-2;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			if (i>25&&i<=30) {
	    				run=true;
	    				 i=0;
	    				j=j-1;
	    				surfaceCreated(surfaceHolder);
	    			}
	    			}else {
	    				run=true;
	    				 i=0;
	    				j=j-0;
	    				surfaceCreated(surfaceHolder);
					}        
		      }		      
		       });
		AlertDialog dialog=builder.create();
		return dialog;
	} 
	int zs=0;
	  class myhandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);  
			 if (msg.arg1==6) {				
				 onStart();
				 
				 textView.setText(""+msg.what);
				 onCreateDialog().show();
			}

		    }
		
}
	  int i=0;
	  Timer time=null;
	TextView textView;
		TimerTask   tt=null;
	  private void onStart(){
		  if (time==null) {
			time=new Timer();
		}
		  if (tt==null) {
				tt=new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						i++;
						
						 if (i>30) {
							 run=true;
							 onCreateDialog().dismiss(); 
		    				 i=0;
		    				j=j-0;
		    				surfaceCreated(surfaceHolder);
		    				onStop();
						}
						 myhandler.sendEmptyMessage(i);
					}
				};
		}
		if (time!=null&&tt!=null) {
			time.schedule(tt, 1000, 1000);
		}
	  }
	  private void onStop(){
		  if (time!=null) {
			time.cancel();
			time=null;
		}
		  if (tt!=null) {
			tt.cancel();
			tt=null;
		}
	  }
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	};
	private Bitmap bitmaputil(Bitmap bitmap){
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();
	    float scaleWidth = ((float) getWidth()-120) / width;
	    float scaleHeight = ((float) 40) / height;
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
	    	      true);
	    return bitmap;
	}
	private Bitmap bgbitmaputil(Bitmap bitmap){
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();
	    float scaleWidth = ((float) getWidth())/ width;
	    float scaleHeight = ((float) getHeight()) / height;
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
	    	      true);
	    return bitmap;
	}
}
