package com.haier.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.R;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.db.UserDB;
import com.haier.utils.SelectGamePagerAdapter;
import com.haier.widgets.MyDialog;
import com.haier.widgets.MyDialog.onDialogClickListener;
import com.haier.widgets.SendDialog;

public class SelectGameActivity extends  FragmentActivity implements onDialogClickListener, OnClickListener, com.haier.widgets.SendDialog.onDialogClickListener{
    private ImageButton     imageButton;
    private TextView        textname;
	private FragmentManager mFragmentManager;
	private SelectGamePagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
	private ImageButton soundButton;
	private ImageButton noteButton;
	private MyDialog dialog;
	private SendDialog sendDialog;
	private String note=" ";
	private String  accessFlag;
	private String urlname;
    public EditText y_type;
	private String sid;
	View DialogView;
	UserDB userDB;
	Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //ViewUtils.inject(this); 
        setContentView(R.layout.activity_weiyouxi);
        mFragmentManager = getSupportFragmentManager();
        context=this;
        findView();
        TextPaint tp = textname.getPaint(); 
        tp.setFakeBoldText(true);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        noteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendDialog=new  SendDialog(SelectGameActivity.this, R.layout.senddialog,R.style.MyDialog,SelectGameActivity.this);
				sendDialog.show();
			}
		});
        soundButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    dialog=new MyDialog(SelectGameActivity.this, R.layout.sounddialog,R.style.MyDialog,SelectGameActivity.this);
				dialog.show();
			}
		});
    }
    
    private void findView() {
        // TODO Auto-generated method stub
        imageButton=(ImageButton)findViewById(R.id.back);
        textname=(TextView)findViewById(R.id.user);
        final RadioGroup ll=(RadioGroup)findViewById(R.id.radio_group);
        LayoutInflater factorys =(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View textEntryView = factorys.inflate(R.layout.senddialog, null);
        y_type = (EditText) textEntryView.findViewById(R.id.sendedit);
        soundButton = (ImageButton)findViewById(R.id.include1).findViewById(R.id.sound);
        noteButton=(ImageButton)findViewById(R.id.include1).findViewById(R.id.send1);
		mPagerAdapter = new SelectGamePagerAdapter(mFragmentManager,this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {	
				for (int i = 0; i < ll.getChildCount(); i++) {
					ll.getChildAt(i).setSelected(false);
				}
				int i = arg0 < 2 ? arg0 : arg0 + 1;
				ll.getChildAt(i).setSelected(true);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
		View.OnClickListener cl = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < ll.getChildCount(); i++) {
					ll.getChildAt(i).setSelected(false);
				}
				v.setSelected(true);
				
				switch (v.getId()) {
				case R.id.radio_kasa:
					mViewPager.setCurrentItem(0, false);
					break;
				case R.id.radio_tongshuai:
					mViewPager.setCurrentItem(1, false);
					break;
				}
			}
		};
		findViewById(R.id.radio_kasa).setSelected(true);
		findViewById(R.id.radio_kasa).setOnClickListener(cl);
		findViewById(R.id.radio_tongshuai).setOnClickListener(cl);
    }
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.okok:
            dialog.dismiss();
			break;
		case R.id.sound1:
			Toast.makeText(this, "你点击了音乐！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.sound2:
			Toast.makeText(this, "你点击了音效！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.send:
			Toast.makeText(this, "你点击了发送！", Toast.LENGTH_SHORT).show();
            note = y_type.getText().toString();
            if(note.equals("")){
            	Toast.makeText(this, "发送失败！不能为空", Toast.LENGTH_SHORT).show();
            }else{
            	try {
         	        urlname = URLEncoder.encode(note, "utf-8");
         	    } catch (UnsupportedEncodingException e) {
         	        e.printStackTrace();
         	    }
            	sendNote();
            }
			sendDialog.dismiss();
			break;
			}
	}
	private void sendNote() {
		// TODO Auto-generated method stub
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
           	 Message msg=new Message();
           	 try {
				msg.obj=doSend();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                handler.sendMessage(msg);
            }  
        }).start();  
	}
	String result = null;
	String describe;
	protected String doSend() throws JSONException {
		// TODO Auto-generated method stub
		sid=Sscion.getSsid();
		String title="sessionId="+sid+"&title="+urlname;
		HttpGet httpRequest = new HttpGet(URLs.SEND_URL+title); 
        String strResult = "";
        try { 
            // HttpClient对象 
            HttpClient httpClient = new DefaultHttpClient(); 
            // 获得HttpResponse对象 
            HttpResponse httpResponse = httpClient.execute(httpRequest); 
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
                // 取得返回的数据 
                strResult = EntityUtils.toString(httpResponse.getEntity()); 
            }  
            JSONArray jsonArray = new JSONArray(strResult);
        	for (int i = 0; i < jsonArray.length(); i++) {
    			JSONObject sc = jsonArray.getJSONObject(i);
                JSONObject item = sc.getJSONObject("accessFlag"); 
                accessFlag=item.getString("flag");
                describe=item.getString("describe");
        	}
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return accessFlag;
	}
	Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {
        	super.handleMessage(msg);
        	if(msg.obj!=null){
             toast1();
        	}else{
            toast2();
			}
        	}
        };
	@Override
	public void onDialogViewClick(MyDialog dialog, OnClickListener listener) {
		// TODO Auto-generated method stub
		dialog.findViewById(R.id.okok).setOnClickListener(listener);
		dialog.findViewById(R.id.sound1).setOnClickListener(listener);
		dialog.findViewById(R.id.sound2).setOnClickListener(listener);
	}
	protected void toast2() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "发表失败！", Toast.LENGTH_SHORT).show();
	}
	protected void toast1() {
		// TODO Auto-generated method stub
		Toast.makeText(this, describe,Toast.LENGTH_SHORT).show();
	}
	public void onDialogViewClick(SendDialog sendDialog, OnClickListener listener) {
		// TODO Auto-generated method stub
		sendDialog.findViewById(R.id.send).setOnClickListener(listener);
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	System.gc();
}
}
