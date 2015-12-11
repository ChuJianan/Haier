package com.yunrui.newgame;

import java.util.ArrayList;
import java.util.List;

import yunrui.game.been.Question;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.view.View;

import com.yunrui.util.Getquestion;
import com.yunrui.util.JsonUtil;
import com.yunrui.util.QuestionContentProviderUtil;
import com.yunrui.util.RousUtil;
import com.yunrui.util.TestUtil;

public class GameView extends View{

	Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
	boolean isTrue=false;//判断是否正确
	Bitmap bitmap;
	Bitmap bgbitmap;
	Bitmap usbitmap;
	Context context;
	int i;
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO 自动生成的构造函数存根
		this.context=context;
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		this.context=context;
		init();
	}

	public GameView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		this.context=context;
		init();
	}

	private void init(){
		i=RousUtil.j;
		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.sprit);
		bgbitmap=bitmapUtil( BitmapFactory.decodeResource(getResources(), R.drawable.dialo_bg));
		usbitmap=bitmapUtils(BitmapFactory.decodeResource(getResources(), R.drawable.baozha));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
//		canvas.drawBitmap(bgbitmap, 10, 40, paint);
		if (TestUtil.zs==JsonUtil.qList.size()) {
			canvas.drawBitmap(bitmap,left , 500, paint);
			int l=(left+bitmap.getWidth()/2)-usbitmap.getWidth()/2+17*i;
			int t=(500+bitmap.getHeight()/2)-usbitmap.getHeight()/2+17*i;
			canvas.drawBitmap(usbitmap,l,t , paint);
		}else {
			getdata();
		drawtest(canvas);
		if (isTrue) {
			drawtrue(canvas);
		}else {
			drawbitmap(canvas);
		}
		}
	}

	private void getdata() {
		// TODO Auto-generated method stub
	    
	    qList=JsonUtil.qList;
	}
	List<Question> qList=new ArrayList<Question>();
	private void drawtest(Canvas canvas) {
		// TODO 自动生成的方法存根
		paint.setTextSize(40);
		paint.setColor(Color.RED);
		TextPaint textPaint = new TextPaint();
		textPaint.setColor(Color.RED);
		textPaint.setTextSize(40.0F);
		String title=qList.get(TestUtil.zs).getTitle();
		Getquestion.questidList.add(qList.get(TestUtil.zs).getId());
		StaticLayout layout = new StaticLayout(title, textPaint, 550, Alignment.ALIGN_NORMAL, 1.0F,
				0.0F, true);
		TestUtil.answer=qList.get(TestUtil.zs).getAnswer();
		canvas.save();
		canvas.translate((float) (30), (float) (50));
		layout.draw(canvas);
		canvas.restore();
		
//		canvas.drawText(title, 30, 50, paint);
		for (int i = 0; i <qList.get(TestUtil.zs).getOplists().size(); i++) {
			String opl=qList.get(TestUtil.zs).getOplists().get(i).getQcontext();
			switch (i) {
			case 0:
				canvas.drawText("A、"+opl, 30, 210+60*i, paint);
				break;
			case 1:
				canvas.drawText("B、"+opl, 30, 210+60*i, paint);
				break;
			case 2:
				canvas.drawText("C、"+opl, 30, 210+60*i, paint);
				break;
			case 3:
				canvas.drawText("D、"+opl, 30, 210+60*i, paint);
				break;
			}
			
		}
		
	}

	private void drawtrue(Canvas canvas) {
		
		// TODO 自动生成的方法存根
		canvas.drawBitmap(bitmap,left , 500, paint);
		int l=(left+bitmap.getWidth()/2)-usbitmap.getWidth()/2+17*i;
		int t=(500+bitmap.getHeight()/2)-usbitmap.getHeight()/2+17*i;
		canvas.drawBitmap(usbitmap,l,t , paint);
		isTrue=false;
//		invalidate();
	}
public  static int left=0;
	private void drawbitmap(Canvas canvas) {
		// TODO 自动生成的方法存根
		left=suiji();
		canvas.drawBitmap(bitmap,left , 500, paint);
	}

	
	private int suiji(){
		int i=(int) (Math.random() *(getWidth()-bitmap.getWidth())+0);
		return i;
	}
	
	public void isTrue(){
		TestUtil.zs+=1;
		isTrue=true;
		i=RousUtil.j;
		this.postInvalidate();
	}
	private Bitmap  bitmapUtil(Bitmap bitmap){
		int width=bitmap.getWidth();
		int height=bitmap.getHeight();
		float scaleWith=((float)550)/width;
		float scaleHeight=((float)450)/height;
		Matrix matrix=new Matrix();
		matrix.postScale(scaleWith, scaleHeight);
		bitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,matrix,true);
		return bitmap;
	}
	private Bitmap  bitmapUtils(Bitmap bitmap){
		int width=bitmap.getWidth();
		int height=bitmap.getHeight();
		float scaleWith=((float)40)/width;
		float scaleHeight=((float)40)/height;
		Matrix matrix=new Matrix();
		matrix.postScale(scaleWith, scaleHeight);
		bitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,matrix,true);
		return bitmap;
	}
}
