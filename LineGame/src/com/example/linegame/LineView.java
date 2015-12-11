package com.example.linegame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yunrui.util.Getquestion;
import com.yunrui.util.TestUtil;

public class LineView extends View {
	private OnCompleteListener mCompleteListener;
	/**
	 * �Ƿ�������ӵ�
	 */
	private boolean isFinished = false;
	/**
	 * �Ƿ�����������
	 */
	private boolean hasData = false;
	/**
	 * ��׼���
	 */
	private float mWidth=480;
	/**
	 * ��׼�߶�
	 */
	private float mHeight;
	/**
	 * ����
	 */
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	/**
	 * Բ��뾶(dp)
	 */
	private float mRadius = 10;
	/**
	 * �����ӵ�
	 */
	private List<List<Point>> sPoints = new ArrayList<List<Point>>();
	private List<Point> tPoints = new ArrayList<Point>();
	/**
	 * ���ߵ�͸����
	 */
	private int lineAlpha = 50;

	public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public LineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(50);
	}

	/**
	 * ����������
	 * 
	 * @return
	 */
	public boolean undo() {
		if (sPoints.size() > 0) {
			sPoints.remove(sPoints.size() - 1);
			this.postInvalidate();
			return true;
		}
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		super.onDraw(canvas);
		keyPoints=TestUtil.keyPoints;
//		DrawText(canvas);
		drawPoints(canvas);
		drawConnPoints(canvas);
		drawConnLine(canvas);
		drawConnPoints(canvas);
	}

	/**
	 * ����Բ��
	 * @param canvas
	 * @param pointMap
	 * @param width		��׼���
	 */
	private void drawPoints(Canvas canvas) {
		float cx, cy;
		for (int i = 0; i < keyPoints.size(); i++) {
			Point p = keyPoints.get(i);
			cx = p.x;
			cy = p.y;
			mPaint.setColor(Color.BLACK);
			canvas.drawCircle(cx, cy, mRadius, mPaint);
//			mPaint.setColor(Color.WHITE);
//			mPaint.setTextSize(mRadius);
//			mPaint.setTextAlign(Align.CENTER);
//			canvas.drawText(p.index.toString(), cx, cy + mRadius / 3, mPaint);
		}
	}
	/**
	 * ����������
	 * 
	 * @param canvas
	 */

	private void drawConnPoints(Canvas canvas) {
		// TODO Auto-generated method stub
		// ������
		int tmpAlpha = mPaint.getAlpha();
		mPaint.setAlpha(lineAlpha);
		// ������
		// ��������ɵ�������
		if (sPoints.size() > 0) {
			for (int i = 0; i < sPoints.size(); i++) {
				List<Point> points = sPoints.get(i);
				if (points.size() > 0) {
					Point tp = points.get(0);
					for (int j = 1; j < points.size(); j++) {
						Point p = points.get(j);
						drawLine(canvas, tp, p);
						tp = p;
					}
				}
			}
		}
		// ��ǰ���ڻ���������
		if (tPoints.size() > 0) {
			Point tp = tPoints.get(0);
			for (int i = 1; i < tPoints.size(); i++) {
				Point p = tPoints.get(i);
				drawLine(canvas, tp, p);
				tp = p;
			}
			if (this.movingNoPoint) {
				drawLine(canvas, tp, new Point((int) moveingX, (int) moveingY));
			}
		}
		mPaint.setAlpha(tmpAlpha);
		lineAlpha = mPaint.getAlpha();
	}

	/**
	 * �����������
	 * 
	 * @param canvas
	 * @param a
	 * @param b
	 */
	private void drawLine(Canvas canvas, Point a, Point b) {
		mPaint.setStrokeWidth((float) mRadius * 0.8f); // �����߿�
		mPaint.setColor(Color.RED);
		if (a.x!=b.x) {
			if (this.movingNoPoint) {
				canvas.drawLine(a.x, a.y, b.x, b.y, mPaint);// ����
			} else {
				canvas.drawLine(a.x, a.y, b.x, b.y, mPaint);// ����
				Getquestion.questuserList.add(b.index);
				Getquestion.mfList.add(25);
				Getquestion.questtimeList.add(5);
				Getquestion.questTFList.add("1");
				Getquestion.questidList.add(a.index);
			}
		}
		
	}

	/**
	 * ����������Բ��
	 * 
	 * @param canvas
	 * @param pointMap
	 * @param width
	 *            ��׼���
	 */
	private void drawConnLine(Canvas canvas) {
		// TODO Auto-generated method stub
		for (List<Point> points : sPoints) {
			for (Point p : points) {
				mPaint.setColor(Color.RED);
				canvas.drawCircle(p.x, p.y, mRadius, mPaint);
			}
		}
		for (Point p : tPoints) {
			mPaint.setColor(Color.RED);
			canvas.drawCircle(p.x, p.y, mRadius, mPaint);
		}
	}

	float x = 20;
	float y = 120;
	/**
	 * �ؼ�����
	 */
	private List<Point> keyPoints = new ArrayList<Point>();

	/*private void DrawText(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(20);
		TextPaint textPaint = new TextPaint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(20.0F);
		for (int i = 0; i < JsonUtil.qList.size(); i++) {
			double yy = y;
			if (i == 1) {
				yy = y * 2 +100;
			}
			if (i > 1) {
				yy = y * (i + 2) +100;
			}
			if(i==3){
				yy=y*(i+2)+210;
			}
			StaticLayout layout = new StaticLayout(JsonUtil.qList.get(i)
					.getTitle(), textPaint, 200, Alignment.ALIGN_NORMAL, 1.0F,
					0.0F, true);
			// canvas.drawText(JsonUtil.qList.get(i).getTitle(), (float) (x),
			// (float) (yy-20), mPaint);
			// int s=JsonUtil.qList.get(i).getTitle().toCharArray().length;
			canvas.save();
			canvas.translate((float) (x), (float) (yy));
			layout.draw(canvas);
			canvas.restore();}
//			canvas.save();
			for (int i = 0; i < JsonUtil.qList.size(); i++) {
				double yy = 140;
				if (i == 1) {
					yy = y * 2 +140;
				}
				if (i > 1) {
					yy = y * (i + 2) +140;
				}
				if(i==3){
					yy=y*(i+2)+240;
				}
			canvas.drawCircle(x +210, (float) (yy), mRadius, mPaint);
			Point point = new Point();
			point.index = JsonUtil.qList.get(i).getId();
			point.x = x +210;
			point.y = (float) (yy);
			keyPoints.add(point);
//			canvas.restore();
		}
			for (int i = 0; i < JsonUtil.qList.size(); i++) {
				double yy = y;
				if (i == 1) {
					yy = y * 2 +100;
				}
				if (i > 1) {
					yy = y * (i + 2) +100;
				}
				if(i==3){
					yy=y*(i+2)+210;
				}
				StaticLayout layout = new StaticLayout(JsonUtil.qList.get(i)
						.getOplists().get(0).getQcontext(), textPaint, 120, Alignment.ALIGN_NORMAL, 1.0F,
						0.0F, true);
				// canvas.drawText(JsonUtil.qList.get(i).getTitle(), (float) (x),
				// (float) (yy-20), mPaint);
				// int s=JsonUtil.qList.get(i).getTitle().toCharArray().length;
				canvas.save();
				canvas.translate(getWidth()-x*7, (float) (yy));
				layout.draw(canvas);
				canvas.restore();
			}
			for (int i = 0; i < JsonUtil.qList.size(); i++) {
				double yy = 140;
				if (i == 1) {
					yy = y * 2 +140;
				}
				if (i > 1) {
					yy = y * (i + 2) +140;
				}
				if(i==3){
					yy=y*(i+2)+220;
				}
			canvas.drawCircle(getWidth()-x*7-10, (float) (yy), mRadius, mPaint);
			Point point = new Point();
			point.index = JsonUtil.qList.get(i).getOplists().get(0).getOrderIndex();
			point.x = getWidth()-x*7-10;
			point.y = (float) (yy);
			keyPoints.add(point);
		}
	}*/

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		hasData = true;
		float ex = e.getX();
		float ey = e.getY();
		Point p = null;
		movingNoPoint = false;
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			tPoints = new ArrayList<Point>();
			p = checkSelectPoint(ex, ey);
			break;
		case MotionEvent.ACTION_MOVE:
			p = checkSelectPoint(ex, ey);
			if (p == null) {
				movingNoPoint = true;
				moveingX = ex;
				moveingY = ey;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isFinished && mCompleteListener != null) {
				mCompleteListener.onComplete(toPointString());
				return false;
			}
			this.sPoints.add(tPoints);
			tPoints = new ArrayList<Point>();// ���õ�ǰ���ӵ�
			break;
		}
		if (p != null) {
			// for(List<Point> points:sPoints){
			// for(int i=0;i<points.size();i++)
			// {
			// if(p==points.get(i)){
			// pData=false;
			// }
			// }
			// }
			if (pData) {
				addPoint(p);
			}
		}
		this.postInvalidate();
		return hasData;
	}

	boolean pData = true;
	float moveingX, moveingY;
	boolean movingNoPoint = false;

	/**
	 * ���һ����
	 * 
	 * @param point
	 */
	private void addPoint(Point point) {
		this.tPoints.add(point);
	}

	/**
	 * ת��ΪString
	 * 
	 * @param points
	 * @return
	 */
	private String toPointString() {
		if (sPoints.size() == keyPoints.size()) {
			StringBuffer sf = new StringBuffer();
			for (List<Point> points : sPoints) {
				for (Point p : points) {
					sf.append(",");
					sf.append(p.index);
				}
			}
			return sf.deleteCharAt(0).toString();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * ���
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Point checkSelectPoint(float x, float y) {
		for (int i = 0; i < keyPoints.size(); i++) {
			Point p = keyPoints.get(i);
			if (RoundUtil.checkInRound(p.x, p.y, 80, (int) x, (int) y)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * �켣������¼�
	 * 
	 * @author way
	 */
	public interface OnCompleteListener {
		/**
		 * ������
		 * 
		 * @param str
		 */
		public void onComplete(String password);
	}
}
