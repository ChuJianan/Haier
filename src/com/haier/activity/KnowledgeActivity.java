package com.haier.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.R;
import com.haier.bean.ClickKnowledgeId;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.db.KnowledgeDB;
import com.haier.db.OptionListDB;
import com.haier.db.QuestionsDB;
import com.haier.db.RulesDB;
import com.haier.utils.JsonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class KnowledgeActivity extends Activity  implements OnClickListener{
	@ViewInject(R.id.back)             private ImageButton btnback;
	@ViewInject(R.id.knowledge5)       private TextView knowledge5;
	@ViewInject(R.id.knowledge4)       private TextView knowledge4;
	@ViewInject(R.id.knowledge3)       private TextView knowledge3;
	@ViewInject(R.id.knowledge2)       private TextView knowledge2;
	@ViewInject(R.id.knowledge1)       private TextView knowledge1;
	@ViewInject(R.id.imageView1) private ImageView ImageView1;
	@ViewInject(R.id.imageView2) private ImageView ImageView2;
	@ViewInject(R.id.imageView3) private ImageView ImageView3;
	@ViewInject(R.id.imageView4) private ImageView ImageView4;
	@ViewInject(R.id.imageView5) private ImageView ImageView5;
	@ViewInject(R.id.imageView)  private ImageView ImageView;
	@ViewInject(R.id.re1)        private LinearLayout RE1;
	@ViewInject(R.id.re2)        private LinearLayout RE2;
	@ViewInject(R.id.re3)        private LinearLayout RE3;
	@ViewInject(R.id.re4)        private LinearLayout RE4;
	@ViewInject(R.id.re5)        private LinearLayout RE5;
	QuestionsDB questionsDB;
	String name;
	OptionListDB optionListDB;
	RulesDB rulesDB;
	String id;
	Context context;
	KnowledgeDB knowledgeDB=new KnowledgeDB(this);
	private long count = 0;
	private boolean run = false;
	 KnowledgeDB kDB;
	 String[] name1;
	 String table="questions";
	 String db="meetreader.db";
	// Frame动画
	private AnimationDrawable animDance;
	//private Handler handler = new Handler();
	int i = 1;
	int x,y;
	int yy;
	int[] location = new int[2];
	int time=10000;
	/*private static int[] bgs = { R.drawable.ic_go1, R.drawable.ic_go2,
		R.drawable.ic_go3,  R.drawable.ic_go4,   R.drawable.ic_go5,
		R.drawable.ic_go6,  R.drawable.ic_go7,   R.drawable.ic_go8,
		R.drawable.ic_go9,  R.drawable.ic_go91,  R.drawable.ic_go92,
		R.drawable.ic_go93, R.drawable.ic_go94,  R.drawable.ic_go95,
		R.drawable.ic_go96, R.drawable.ic_go97,  R.drawable.ic_go98,
		R.drawable.ic_go99, R.drawable.ic_go991, R.drawable.ic_go992,
		R.drawable.ic_go993,R.drawable.ic_go994, R.drawable.ic_go995,
		R.drawable.ic_go996 };*/
		/*private Runnable task = new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				if (run) {
					// Ҫ������飬�����ٴε��ô�Runnable������ʵ��ÿ0.02��ʵ��һ�εĶ�ʱ�����
					handler.postDelayed(this, 0);
					if (i < 24) {
						ImageView.setImageDrawable(getResources().getDrawable(
								bgs[i]));
						i++;
					} else {
						 i = 1;
						 ImageView.setImageDrawable(getResources().getDrawable(
								bgs[i]));
					}
				}
				//tvCounter.setText("Count: " + count);
			}
		};*/
		String flag=null;
		/** Called when the activity is first created. */
	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        ViewUtils.inject(this); 
        questionsDB=new QuestionsDB(this);
        optionListDB=new OptionListDB(this);
        rulesDB=new RulesDB(this);
        context=this;
        animDance = (AnimationDrawable) this.ImageView.getBackground();
        JsonUtils.questionsDB=questionsDB;
        JsonUtils.optionListDB=optionListDB;
        JsonUtils.rulesDB=rulesDB;
	    String   na = JsonUtils.na;
	    if(na.equals("1")){
	    	name1=knowledgeDB.select();
	        knowledge5.setText(name1[0]);
	        knowledge1.setText(name1[1]);
	        knowledge2.setText(name1[2]);
	        knowledge3.setText(name1[3]);
	        knowledge4.setText(name1[4]);
	    }else if(na.equals("2")){
	    	name1=knowledgeDB.select2();
	        knowledge1.setText(name1[1]);
	        knowledge2.setText(name1[2]);
	        knowledge3.setText(name1[3]);
	        knowledge4.setText(name1[4]);
	        knowledge5.setText(name1[5]);
	    }else{
	    	    name1=knowledgeDB.select3();
		        knowledge1.setText(name1[0]);
		        knowledge2.setText(name1[1]);
		        knowledge3.setText(name1[2]);
		        knowledge4.setText(name1[3]);
		        knowledge5.setText(name1[4]);
	    }
	    deleteTableByDBName(db,table);
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
           	 Message msg=new Message();
           	 flag=getQuset();
             handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler  
            }  
        }).start();  
	    
	   // flag= getQuset();
        }
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法  
        	super.handleMessage(msg);
        	}
        };
	    @OnClick({ R.id.back ,R.id.knowledge5,R.id.knowledge4,R.id.knowledge3,R.id.knowledge2,R.id.knowledge1})
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	    	switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.knowledge5:
				name=knowledge5.getText().toString();
			    id=knowledgeDB.select4(name);
				ClickKnowledgeId.setClickknowledgeId(id);
				ImageView5.getLocationOnScreen(location);
				 x = location[0];
				 y = location[1];	
				 yy = x+10;
				 time=8000;
				start();
				break;
			case R.id.knowledge4:
				name=knowledge4.getText().toString();
			    id=knowledgeDB.select4(name);
				ClickKnowledgeId.setClickknowledgeId(id);
				ImageView4.getLocationOnScreen(location);
				 x = location[0];
				 y = location[1];	
				 yy = x+10;
				 time=5000;
				start();
				break;
			case R.id.knowledge3:
				name=knowledge3.getText().toString();
			    id=knowledgeDB.select4(name);
				ClickKnowledgeId.setClickknowledgeId(id);
				ImageView3.getLocationOnScreen(location);
				 x = location[0];
				 y = location[1];	
				 yy = x+10;
				 time=3500;
				start();
				break;
			case R.id.knowledge2:
				name=knowledge2.getText().toString();
			    id=knowledgeDB.select4(name);
				ClickKnowledgeId.setClickknowledgeId(id);
				ImageView2.getLocationOnScreen(location);
				 x = location[0];
				 y = location[1];	
				 yy = x+10;
				 time=2500;
				start();
				break;
			case R.id.knowledge1:
				name=knowledge1.getText().toString();
			    id=knowledgeDB.select4(name);
				ClickKnowledgeId.setClickknowledgeId(id);
				ImageView1.getLocationOnScreen(location);
				 x = location[0];
				 y = location[1];	
				 yy = x+10;
				 time=1000;
				start();
				break;
		}
 	}
		private void start() {
			// TODO Auto-generated method stub
			final Animation translateAnimation = new TranslateAnimation(0,
					yy, 0, 0);
			    run = true;
				animDance.start();
				translateAnimation.setDuration(time); // 
				translateAnimation.setFillAfter(true);
				translateAnimation.setFillEnabled(true);
				translateAnimation.setInterpolator(new LinearInterpolator());
				ImageView.setAnimation(translateAnimation); 
				translateAnimation.startNow();
			translateAnimation.setAnimationListener(new AnimationListener(){
				 @Override
                    public void onAnimationStart(Animation animation) {
					// getQuset();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {      
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                    	animDance.stop();
                    	if (flag==null) {
                    		Toast.makeText(KnowledgeActivity.this, "�������", Toast.LENGTH_LONG).show();
                    		Intent intent=new Intent();
                        	intent.setClass(KnowledgeActivity.this, LoginActivity.class);
                        	startActivity(intent);
                        	finish();
						}else{
                    	if (flag.equals("6")) {
                    		Intent intent=new Intent();
                        	intent.setClass(KnowledgeActivity.this, Vidio2Activity.class);
                        	startActivity(intent);
                        	finish();
						}
                    	else {
                    		Toast.makeText(KnowledgeActivity.this, JsonUtils.describe, Toast.LENGTH_LONG).show();
                    		Intent intent=new Intent();
                        	intent.setClass(KnowledgeActivity.this, LoginActivity.class);
                        	startActivity(intent);
                        	finish();
						}
                    	}
                    }
			});
		}
		
		
		private String getQuset() {
			// TODO Auto-generated method stub
			String result = null;
			String sessionId = Sscion.getSsid();
			HttpGet httpRequest = new HttpGet(URLs.TIMETABLE+"knowledgeId="+6+"&sessionId="+sessionId); 
	        String strResult = ""; 
	        try { 
	            // HttpClient���� 
	            HttpClient httpClient = new DefaultHttpClient(); 
	            // ���HttpResponse���� 
	            HttpResponse httpResponse = httpClient.execute(httpRequest); 
	            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
	                strResult = EntityUtils.toString(httpResponse.getEntity()); 
	            }  
	            result=JsonUtils.Fjson(strResult);
	          
	        } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return result;
			}
		public SQLiteDatabase openDBByName(String DBName) {
			SQLiteDatabase db;
			//deleteDBByName(DBName);
			db = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			return db;
			}

		public boolean deleteTableByDBName(String DBName, String TableName) {
		SQLiteDatabase dbDatabase = openDBByName(DBName);
		dbDatabase.delete(TableName, null, null);
		close(dbDatabase);
		// checkDBByName(DBName);
		// db.delete(table, whereClause,
		// whereArgs).deleteDatabase(GameDB.DATABASE_TILEPUZZLE_NAME);
		return false;
		}
		public void close(SQLiteDatabase db_) {
			db_.close();
			}
}
