package yunrui.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View{

public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	/**
	 * �Ƿ�����������
	 */
	private boolean hasData = false;
	/**
	 * �Ƿ��ʼ�����
	 */
	private boolean isInited = false;
	/**
	 * ��׼���
	 */
	private float mWidth;
	/**
	 * ��׼�߶�
	 */
	private float mHeight;
	/**
	 * ����
	 */
	private Bitmap mBackground;
	/**
	 * ����
	 */
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


}
