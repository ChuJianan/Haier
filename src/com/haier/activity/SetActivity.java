package com.haier.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.haier.R;
import com.haier.bean.Music1;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SetActivity extends Activity implements OnClickListener{
@ViewInject(R.id.sign_btn1) private ImageView sign1;
@ViewInject(R.id.sign_btn2) private ImageView sign2;
@ViewInject(R.id.sign_btn3) private ImageView sign3;
@ViewInject(R.id.sign_btn4) private ImageView sign4;
@ViewInject(R.id.sign_btn5) private ImageView sign5;
@ViewInject(R.id.sign_btn6) private ImageView sign6;
@ViewInject(R.id.sign_btn7) private ImageView sign7;
@ViewInject(R.id.sign_btn8) private ImageView sign8;
@ViewInject(R.id.right_button) private Button rIButton;
	    private Button btn,mbtn;
	    private OnClickListener imgViewListener;  
	    private Bitmap myBitmap;  
	    private byte[] mContent; 
	    private ImageView nub1,nub2,nub3,nub4;
	    private Button leftButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		ViewUtils.inject(this);
		user=getSharedPreferences("main_background",0);
		btn=(Button)findViewById(R.id.bgbtn);
		mbtn=(Button)findViewById(R.id.mbgbtn);
		nub1=(ImageView)findViewById(R.id.nub1);
		nub2=(ImageView)findViewById(R.id.nub2);
		nub3=(ImageView)findViewById(R.id.nub3);
		nub4=(ImageView)findViewById(R.id.nub4);
		leftButton=(Button)findViewById(R.id.left_button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   final CharSequence[] items = { "相册", "拍照" };  
	                  
	                AlertDialog dlg = new AlertDialog.Builder(SetActivity.this).setTitle("选择照片").setItems(items,   
	                        new DialogInterface.OnClickListener() {  
	                              
	                            @Override  
	                            public void onClick(DialogInterface dialog, int which) {  
	                                // TODO Auto-generated method stub   
	                            //这里item是根据选择的方式，   在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法     
	                                if(which==1){  
	                                    Intent getImageByCamera  = new Intent("android.media.action.IMAGE_CAPTURE");  
	                                    ContentValues values = new ContentValues();  
	                                  //photo为全局变量Uri photoUri=null;
	                                          photoUri = SetActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);  
	                                          getImageByCamera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);  
	                                    startActivityForResult(getImageByCamera, 1);  
	                                }else{  
	                                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);  
	                                    getImage.addCategory(Intent.CATEGORY_OPENABLE);  
	                                    getImage.setType("image/*");  
	                                    startActivityForResult(getImage, 0);  
	                                }  
	                            }  
	                        }).create();  
	                dlg.show();  
			}
		});
		mbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   Editor editor=user.edit();
					editor.putString("url","");
					editor.commit(); 
					Toast.makeText(SetActivity.this, "恢复成功！", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent);
		        	finish();
			   }
		});
		leftButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SetActivity.this,MainActivity.class);
	        	startActivity(intent);
	        	finish();
			}
		});
	}
	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        // TODO Auto-generated method stub   
	        super.onActivityResult(requestCode, resultCode, data);  
	        ContentResolver contentResolver  =getContentResolver();  
	         /** 
	         * 因为两种方式都用到了startActivityForResult方法，这个方法执行完后都会执行onActivityResult方法， 
	         * 所以为了区别到底选择了那个方式获取图片要进行判断，这里的requestCode跟startActivityForResult里面第二个参数对应 
	         */  
	        if(requestCode==0){  
	            //方式一   
	            /*try { 
	                 //获得图片的uri  
	                Uri orginalUri = data.getData(); 
	                  //将图片内容解析成字节数组  
	                mContent = readStream(contentResolver.openInputStream(Uri.parse(orginalUri.toString()))); 
	                 //将字节数组转换为ImageView可调用的Bitmap对象  
	                myBitmap  =getPicFromBytes(mContent,null); 
	                  ////把得到的图片绑定在控件上显示 
	                imageView.setImageBitmap(myBitmap); 
	            } catch (Exception e) { 
	                e.printStackTrace(); 
	                // TODO: handle exception 
	            }*/  
	            //方式二   
	            try {  
	                Uri selectedImage = data.getData();  
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	  
	                Cursor cursor = getContentResolver().query(selectedImage,  
	                        filePathColumn, null, null, null);  
	                cursor.moveToFirst();  
	  
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	                getFilePath = cursor.getString(columnIndex);  
	                cursor.close();  
	              
	            } catch (Exception e) {  
	                // TODO: handle exception   
	                e.printStackTrace();  
	            }  
	         
	              
	        }else if(requestCode==1){  
	            try {  
	            	 ContentResolver cr = this.getContentResolver();  
	                 Cursor cursor = cr.query(photoUri, null, null, null, null);  
	                 cursor.moveToFirst(); //游标移到第一条就是系统默认的路径
	                 if (cursor != null) {  
	                	 getFilePath = cursor.getString(1);  //获取到的路径可以用于图片自定义压缩了
	                     cursor.close(); 
	                 }
	            } catch (Exception e) {  
	                e.printStackTrace();  
	                // TODO: handle exception   
	            }
	            
	        }  
	        Editor editor=user.edit();
			editor.putString("url","sd:"+ getFilePath);
			editor.commit(); 
			Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(SetActivity.this,MainActivity.class);
        	startActivity(intent);
        	finish();
	    }  
	 String getFilePath;
	 Uri photoUri=null;
	 SharedPreferences user;
	 public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {   
	        if (bytes != null)   
	            if (opts != null)   
	                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);   
	            else   
	                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);   
	        return null;   
	    }   
	      
	      
	      
	 public static byte[] readStream(InputStream in) throws Exception{  
	     byte[] buffer  =new byte[1024];  
	     int len  =-1;  
	     ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	       
	     while((len=in.read(buffer))!=-1){  
	         outStream.write(buffer, 0, len);  
	     }  
	     byte[] data  =outStream.toByteArray();  
	     outStream.close();  
	     in.close();  
	     return data;  
	 }  
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {

	        switch (keyCode) {
	            case KeyEvent.KEYCODE_BACK:
	            	Intent intent=new Intent(SetActivity.this,MainActivity.class);
	            	startActivity(intent);
	            	finish();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	 @OnClick({R.id.nub1,R.id.nub2,R.id.nub3,R.id.nub4,R.id.right_button,R.id.nub5,R.id.nub6,R.id.nub7,R.id.nub8})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.nub1:
			Music1.setNub(1);
			sign1.setBackgroundResource(R.drawable.ic_duihao);
			sign2.setBackground(null);
			sign3.setBackground(null);
			sign4.setBackground(null);
			sign5.setBackground(null);
			sign6.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub2:
			Music1.setNub(2);
			sign2.setBackgroundResource(R.drawable.ic_duihao);
			sign1.setBackground(null);
			sign3.setBackground(null);
			sign4.setBackground(null);
			sign5.setBackground(null);
			sign6.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub3:
			Music1.setNub(3);
			sign3.setBackgroundResource(R.drawable.ic_duihao);
			sign2.setBackground(null);
			sign1.setBackground(null);
			sign4.setBackground(null);
			sign5.setBackground(null);
			sign6.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub4:
			Music1.setNub(4);
			sign4.setBackgroundResource(R.drawable.ic_duihao);
			sign3.setBackground(null);
			sign2.setBackground(null);
			sign1.setBackground(null);
			sign5.setBackground(null);
			sign6.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub5:
			Music1.setNub(5);
			sign5.setBackgroundResource(R.drawable.ic_duihao);
			sign4.setBackground(null);
			sign3.setBackground(null);
			sign2.setBackground(null);
			sign1.setBackground(null);
			sign6.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub6:
			Music1.setNub(6);
			sign6.setBackgroundResource(R.drawable.ic_duihao);
			sign5.setBackground(null);
			sign4.setBackground(null);
			sign3.setBackground(null);
			sign2.setBackground(null);
			sign1.setBackground(null);
			sign7.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub7:
			Music1.setNub(7);
			sign7.setBackgroundResource(R.drawable.ic_duihao);
			sign6.setBackground(null);
			sign5.setBackground(null);
			sign4.setBackground(null);
			sign3.setBackground(null);
			sign2.setBackground(null);
			sign1.setBackground(null);
			sign8.setBackground(null);
			break;
		case R.id.nub8:
			Music1.setNub(8);
			sign8.setBackgroundResource(R.drawable.ic_duihao);
			sign7.setBackground(null);
			sign6.setBackground(null);
			sign5.setBackground(null);
			sign4.setBackground(null);
			sign3.setBackground(null);
			sign2.setBackground(null);
			sign1.setBackground(null);
			break;
		case R.id.right_button:
			if (Music1.getNub()==1) {
				 Editor editor=user.edit();
					editor.putString("url", "background/nub1.png");
					editor.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent);
		        	finish();
			}else if (Music1.getNub()==2) {
				 Editor editor1=user.edit();
					editor1.putString("url", "background/nub2.png");
					editor1.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent1=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent1);
		        	finish();
			}else if (Music1.getNub()==3) {
				 Editor editor2=user.edit();
					editor2.putString("url", "background/nub3.png");
					editor2.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent2=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent2);
		        	finish();
			}else if (Music1.getNub()==4) {
				 Editor editor3=user.edit();
					editor3.putString("url", "background/nub4.png");
					editor3.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent3=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent3);
		        	finish();
			}else if (Music1.getNub()==5) {
				 Editor editor4=user.edit();
					editor4.putString("url", "background/nub5.png");
					editor4.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent4=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent4);
		        	finish();
			}else if (Music1.getNub()==6) {
				 Editor editor5=user.edit();
					editor5.putString("url", "background/nub6.png");
					editor5.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent5=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent5);
		        	finish();
			}else if (Music1.getNub()==7) {
				 Editor editor6=user.edit();
					editor6.putString("url", "background/nub7.png");
					editor6.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent6=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent6);
		        	finish();
			}else if (Music1.getNub()==8) {
				 Editor editor7=user.edit();
					editor7.putString("url", "background/nub8.png");
					editor7.commit(); 
					Toast.makeText(SetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
					Intent intent7=new Intent(SetActivity.this,MainActivity.class);
		        	startActivity(intent7);
		        	finish();
			}
			break;
		}
	}

}
