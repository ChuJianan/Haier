package com.yunrui.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
/**
 * @author Himi
 */
public class Player {
	//���ǵ�Ѫ����Ѫ��λͼ
	//Ĭ��25Ѫ
	private int playerHp = 10;
	private Bitmap bmpPlayerHp;
	//���ǵ������Լ�λͼ
	public int x, y;
	private Bitmap bmpPlayer;
	//�����ƶ��ٶ�
	private int speed = 10;
	//�����ƶ���ʶ�������½��ѽ��⣬�㶮�ã�
	private boolean isUp, isDown, isLeft, isRight;
	//��ײ�����޵�ʱ��
	//��ʱ��
	private int noCollisionCount = 0;
	//��Ϊ�޵�ʱ��
	private int noCollisionTime = 60;
	//�Ƿ���ײ�ı�ʶλ
	private boolean isCollision;

	//���ǵĹ��캯��
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight()-30;
	}

	//���ǵĻ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//��������
		//�������޵�ʱ��ʱ����������˸
		if (isCollision) {
			//ÿ2����Ϸѭ��������һ������
			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, x, y, paint);
			}
		} else {
			canvas.drawBitmap(bmpPlayer, x, y, paint);
		}
		//��������Ѫ��
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}
	//ʵ�尴��
	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
		}
	}

	//ʵ�尴��̧��
	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
	}
	public void onUp(int keyCode) {
		if (keyCode == 1) {
			isDown = false;
			isUp = true;
			isLeft = false;
			isRight = false;
		}
		if (keyCode == 2) {
			isUp = false;
			isLeft = false;
			isRight = false;
			isDown = true;
		}
		if (keyCode == 3) {
			isUp = false;
			isLeft = true;
			isRight = false;
			isDown = false;
		}
		if (keyCode == 4) {
			isUp = false;
			isLeft = false;
			isRight = true;
			isDown = false;
		}
	}
	//���ǵ��߼�
	public void logic(){
		//���������ƶ�
		if (isLeft) {
			x -= speed;
			/*for(int i=1;i<1000;i++){
				int t=speed*i;
				if(t>Test.getJuli()){
					isLeft=false;
					i=1000;
				}
			}*/
		}
		if (isRight) {
			x += speed;
			/*for(int i=1;i<1000;i++){
				int t=speed*i;
				if(t>Test.getJuli()){
					isRight=false;
					i=1000;
				}
			}*/
		}
		if (isUp) {
			y -= speed;
			/*for(int i=1;i<1000;i++){
				int t=speed*i;
				if(t>Test.getJuli()){
					isUp=false;
					i=1000;
				}
			}*/
		}
		if (isDown) {
			y += speed;
			/*for(int i=1;i<1000;i++){
				int t=speed*i;
				if(t>Test.getJuli()){
					isDown=false;
					i=1000;
				}
			}*/
		}
		//�ж���ĻX�߽�
		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		//�ж���ĻY�߽�
		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
		//�����޵�״̬
		if (isCollision) {
			//��ʱ����ʼ��ʱ
			noCollisionCount++;
			if (noCollisionCount >= noCollisionTime) {
				//�޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}

	//��������Ѫ��
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	//��ȡ����Ѫ��
	public int getPlayerHp() {
		return playerHp;
	}

	//�ж���ײ(������л�)
	public boolean isCollsionWith(Enemy en) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
	//�ж���ײ(������л��ӵ�)
	public boolean isCollsionWith(Bullet bullet) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = bullet.bulletX;
			int y2 = bullet.bulletY;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
}
