package com.yunrui.pg;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import yunrui.game.been.Getquestion;
import yunrui.game.util.EquipmentContentProviderUtil;
import yunrui.game.util.HttpUtil;
import yunrui.game.util.JsonUtil;
import yunrui.game.util.QuestionContentProviderUtil;
import yunrui.game.util.ScheduleInfosContentProviderUtil;
import yunrui.game.util.UserInfoContentProviderUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

/**
 * 
 * @author niu
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable , OnGestureListener, OnTouchListener{
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag1;
	private Canvas canvas;
	public static int screenW, screenH;
	//定义游戏状态常量
	public static final int GAME_MENU = 0;//游戏菜单
	public static final int GAMEING = 1;//游戏中
	public static final int GAME_WIN = 2;//游戏胜利
	public static final int GAME_LOST = 3;//游戏失败
	public static final int GAME_PAUSE = -1;//游戏菜单
	//当前游戏状态(默认初始在游戏菜单界面)
	public static int gameState = GAME_MENU;
	//声明一个Resources实例便于加载图片
	private Resources res = this.getResources();
	//声明游戏需要用到的图片资源(图片声明)
	private Bitmap bmpBackGround;//游戏背景
	private Bitmap bmpBoom;//爆炸效果
	private Bitmap bmpBoosBoom;//Boos爆炸效果
	private Bitmap bmpButton;//游戏开始按钮
	private Bitmap bmpButtonPress;//游戏开始按钮被点击
	private Bitmap bmpEnemyDuck;//怪物鸭子
	private Bitmap bmpEnemyFly;//怪物苍蝇
	private Bitmap bmpEnemyBoos;//怪物猪头Boos
	private Bitmap bmpGameWin;//游戏胜利背景
	private Bitmap bmpGameLost;//游戏失败背景
	private Bitmap bmpPlayer;//游戏主角飞机
	private Bitmap bmpPlayerHp;//主角飞机血量
	private Bitmap bmpMenu;//菜单背景
	public static Bitmap bmpBullet;//子弹
	public static Bitmap bmpEnemyBullet;//敌机子弹
	public static Bitmap bmpBossBullet;//Boss子弹
	private GestureDetector gd;//手势
	//声明一个菜单对象
	private GameMenu gameMenu;
	//声明一个滚动游戏背景对象
	private GameBg backGround;
	//声明主角对象
	private Player player;
	//声明一个敌机容器
	private Vector<Enemy> vcEnemy;
	//每次生成敌机的时间(毫秒)
	private int createEnemyTime = 100;
	private int count;//计数器
	//敌人数组：1和2表示敌机的种类，-1表示Boss
	//二维数组的每一维都是一组怪物
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
			{ 1, 3 }, { 2, 1 }, { -1 } };
	//当前取出一维数组的下标
	private int enemyArrayIndex;
	//是否出现Boss标识位
	private boolean isBoss;
	//随机库，为创建的敌机赋予随即坐标
	private Random random;
	//敌机子弹容器
	private Vector<Bullet> vcBullet;
	//添加子弹的计数器
	private int countEnemyBullet;
	//主角子弹容器/
	private Vector<Bullet> vcBulletPlayer;
	//添加子弹的计数器
	private int countPlayerBullet;
	//爆炸效果容器
	private Vector<Boom> vcBoom;
	//声明Boss
	private Boss boss;
	//Boss的子弹容器
	public static Vector<Bullet> vcBulletBoss;
	boolean run = true;
	myhandler myhandler;
	Thread thread;
	SingleSelectionDialog siDialog;//题目对话框
	CustomDialog.Builder builder;//弹出对话框
	Context context;
	int mf = 0;
	int h = 0;
	int itemIndex;
	int j = 0;
	int t = 1;
	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	
	/**
	 * SurfaceView初始化函数
	 */
	public MySurfaceView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置背景常亮
		this.setKeepScreenOn(true);
	}
	/**
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		flag1 = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
	}
	/*
	 * 自定义的游戏初始化函数
	 */
	private void initGame() {
		//放置游戏切入后台重新进入游戏时，游戏被重置!
		//当游戏状态处于菜单时，才会重置游戏
		if (gameState == GAME_MENU){
			//加载游戏资源
			bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.hhbg);
			bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
			bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
			bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
			bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
			bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
			bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
			bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
			bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewinnn);
			bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelose);
			bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.plane1);
			bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.yao);
			bmpMenu = BitmapFactory.decodeResource(res, R.drawable.begin);
			soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
			soundPoolMap = new HashMap<Integer, Integer>();
			soundPoolMap.put(1, soundPool.load(context, R.raw.explosion4, 1));
			soundPoolMap.put(2, soundPool.load(context, R.raw.destroyer_lazer3, 1));
			this.setLongClickable(true);
			// setLongClickable( true )是必须的，因为 只有这样
			// 我们当前的SurfaceView(view)才能够处理不同于触屏形式;
			// 例如：ACTION_MOVE，或者多个ACTION_DOWN
			// surfaceView.setOnTouchListener(this);//将本类绑定触屏监听器
			gd = new GestureDetector(this);
			gd.setIsLongpressEnabled(true);
			bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
			myhandler = new myhandler();
			builder=new CustomDialog.Builder(context);
			bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.dilei);
			bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
			//爆炸效果容器实例
			vcBoom = new Vector<Boom>();
			//敌机子弹容器实例
			vcBullet = new Vector<Bullet>();
			//主角子弹容器实例
			vcBulletPlayer = new Vector<Bullet>();//////////////
			//菜单类实例
			gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);
			//实例游戏背景
			backGround = new GameBg(bmpBackGround);
			//实例主角
			player = new Player(bmpPlayer, bmpPlayerHp);
			//实例敌机容器
			vcEnemy = new Vector<Enemy>();
			//实例随机库
			random = new Random();
			//---Boss相关
			//实例boss对象
			boss = new Boss(bmpEnemyBoos);
			//实例Boss子弹容器
			vcBulletBoss = new Vector<Bullet>();
		}
	}
	/**
	 * 游戏绘图
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				//绘图函数根据游戏状态不同进行不同绘制
				switch (gameState) {
				case GAME_MENU:
					//菜单的绘图函数
					gameMenu.draw(canvas, paint);
					break;
				case GAMEING:
					//while(run){
					//游戏背景
					backGround.draw(canvas, paint);
					//主角绘图函数
					player.draw(canvas, paint);
					if (isBoss == false) {
						//敌机绘制
						for (int i = 0; i < vcEnemy.size(); i++) {
							vcEnemy.elementAt(i).draw(canvas, paint);
						}
						//敌机子弹绘制
						for (int i = 0; i < vcBullet.size(); i++) {
							vcBullet.elementAt(i).draw(canvas, paint);
						}
					} else {
						//Boos的绘制
						boss.draw(canvas, paint);
						//Boss子弹逻辑
						for (int i = 0; i < vcBulletBoss.size(); i++) {
							vcBulletBoss.elementAt(i).draw(canvas, paint);
						}
					}
					//处理主角子弹绘制
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
						vcBulletPlayer.elementAt(i).draw(canvas, paint);
					}
					//爆炸效果绘制
					for (int i = 0; i < vcBoom.size(); i++) {
						vcBoom.elementAt(i).draw(canvas, paint);
					}
					break;
				case GAME_PAUSE:
					break;
				case GAME_WIN:
					canvas.drawBitmap(bmpGameWin, 0, 0, paint);
					break;
				case GAME_LOST:
					canvas.drawBitmap(bmpGameLost, 0, 0, paint);
					break;
			   }
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//触屏监听事件函数根据游戏状态不同进行不同监听
		switch (gameState) {
		case GAME_MENU:
			//菜单的触屏事件处理
			gameMenu.onTouchEvent(event);
			break;
		case GAMEING:
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		case MotionEvent.EDGE_LEFT:
		//	player.onUp(3);
			break;
		case MotionEvent.EDGE_RIGHT:
			Toast.makeText(context, "nnn", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}
	/**
	 * 按键按下事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
				//Boss状态设置为没出现
				isBoss = false;
				//重置游戏
				initGame();
				//重置怪物出场
				enemyArrayIndex = 0;
			} else if (gameState == GAME_MENU) {
				//当前游戏状态在菜单界面，默认返回按键退出游戏
				MainActivity.instance.finish();
				System.exit(0);
			}
			//表示此按键已处理，不再交给系统处理，
			//从而避免游戏被切入后台
			return true;
		}
		//按键监听事件函数根据游戏状态不同进行不同监听
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			//主角的按键按下事件
			player.onKeyDown(keyCode, event);
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 按键抬起事件监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
			}
			//表示此按键已处理，不再交给系统处理，
			//从而避免游戏被切入后台
			return true;
		}
		//按键监听事件函数根据游戏状态不同进行不同监听
		switch (gameState) {
		case GAME_MENU:
			
			break;
		case GAMEING:
			//按键抬起事件
			player.onKeyUp(keyCode, event);
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 游戏逻辑
	 */
	private void logic() {
		//逻辑处理根据游戏状态不同进行不同处理
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			//背景逻辑
			backGround.logic();
			//主角逻辑
			player.logic();
			//敌机逻辑
			if (isBoss == false) {
				//敌机逻辑
				for (int i = 0; i < vcEnemy.size(); i++) {
					Enemy en = vcEnemy.elementAt(i);
					//因为容器不断添加敌机 ，那么对敌机isDead判定，
					//如果已死亡那么就从容器中删除,对容器起到了优化作用；
					if (en.isDead) {
						vcEnemy.removeElementAt(i);
					} else {
						en.logic();
					}
				}
				//生成敌机
				count++;
				if (count % createEnemyTime == 0) {
					for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
						//苍蝇
						/*if (enemyArray[enemyArrayIndex][i] == 1) {
							int x = random.nextInt(screenW - 100) + 50;
							vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
						//鸭子左
						} else */if (enemyArray[enemyArrayIndex][i] == 2) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
						//鸭子右
						} else if (enemyArray[enemyArrayIndex][i] == 3) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));
						}
					}
					//这里判断下一组是否为最后一组(Boss)
					if (enemyArrayIndex == enemyArray.length - 1) {
						isBoss = true;
					} else {
						enemyArrayIndex++;
					}
				}
				//处理敌机与主角的碰撞
				for (int i = 0; i < vcEnemy.size(); i++) {
					if (player.isCollsionWith(vcEnemy.elementAt(i))) {
						//发生碰撞，主角血量-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						//当主角血量小于0，判定游戏失败
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//每2秒添加一个敌机子弹
				countEnemyBullet++;
				if (countEnemyBullet % 20 == 0) {
					for (int i = 0; i < vcEnemy.size(); i++) {
						Enemy en = vcEnemy.elementAt(i);
						//不同类型敌机不同的子弹运行轨迹
						int bulletType = 0;
						switch (en.type) {
						/*//苍蝇
						case Enemy.TYPE_FLY:
							bulletType = Bullet.BULLET_FLY;
							break;*/
						 //鸭子
						case Enemy.TYPE_DUCKL:
						case Enemy.TYPE_DUCKR:
							bulletType = Bullet.BULLET_DUCK;
							break;
						}
						vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
					}
				}
				//处理敌机子弹逻辑
				for (int i = 0; i < vcBullet.size(); i++) {
					Bullet b = vcBullet.elementAt(i);
					if (b.isDead) {
						vcBullet.removeElement(b);
					} else {
						b.logic();
					}
				}
				//处理敌机子弹与主角碰撞
				for (int i = 0; i < vcBullet.size(); i++) {
					if (player.isCollsionWith(vcBullet.elementAt(i))) {
						//发生碰撞，主角血量-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						//当主角血量小于0，判定游戏失败
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//处理主角子弹与敌机碰撞
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					//取出主角子弹容器的每个元素
					Bullet blPlayer = vcBulletPlayer.elementAt(i);
					for (int f = 0; f < vcEnemy.size(); f++) {
						//添加爆炸效果
						//取出敌机容器的每个元与主角子弹遍历判断
						if (vcEnemy.elementAt(f).isCollsionWith(blPlayer)) {
							vcBoom.add(new Boom(bmpBoom, vcEnemy.elementAt(f).x, vcEnemy.elementAt(f).y, 7));
							if (shifoudialog==0) {
								Message msg=new Message();
								/*if (j<JsonUtil.qList.size()) {
									j=j+1;
								}*/
								flag1=false;
								msg.arg1 = 6;
								myhandler.sendMessage(msg);
							}else {
								//flag1=false;
							}
						}
					}
				}
			} else {//Boss相关逻辑///////////////
				//每0.5秒添加一个主角子弹
				boss.logic();/////////////////////////////////////////////////////////////////////////////////////////////
				if (countPlayerBullet % 20 == 0) {
					//Boss的没发疯之前的普通子弹
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35, boss.y + 40, Bullet.BULLET_FLY));
				}
				//Boss子弹逻辑
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					Bullet b = vcBulletBoss.elementAt(i);
					if (b.isDead) {
						vcBulletBoss.removeElement(b);
					} else {
						b.logic();
					}
				}
				//Boss子弹与主角的碰撞
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
						//发生碰撞，主角血量-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						//当主角血量小于0，判定游戏失败
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//Boss被主角子弹击中，产生爆炸效果
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					Bullet b = vcBulletPlayer.elementAt(i);
					if (boss.isCollsionWith(b)) {
						if (boss.hp <= 0) {
							//游戏胜利
							gameState = GAME_WIN;
						} else {
							//及时删除本次碰撞的子弹，防止重复判定此子弹与Boss碰撞、
							b.isDead = true;
							//Boss血量减1
							boss.setHp(boss.hp - 1);
							//在Boss上添加三个Boss爆炸效果
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25, boss.y + 30, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 45, boss.y + 50, 5));
						}
					}
				}
			}
			//每1秒添加一个主角子弹///////////////////////////////
			countPlayerBullet++;
			if (countPlayerBullet % 20 == 0) {
				if(Test.music2){
					playSound(2, 0);
				}
				vcBulletPlayer.add(new Bullet(bmpBullet, player.x + 15, player.y - 20, Bullet.BULLET_PLAYER));
			}
			//处理主角子弹逻辑
			for (int i = 0; i < vcBulletPlayer.size(); i++) {
				Bullet b = vcBulletPlayer.elementAt(i);
				if (b.isDead) {
					vcBulletPlayer.removeElement(b);
				} else {
					b.logic();
				}
			}
			//爆炸效果逻辑
			for (int i = 0; i < vcBoom.size(); i++) {
				if(Test.music2){
					playSound(1, 0);
				}
				Boom boom = vcBoom.elementAt(i);
				if (boom.playEnd) {
					//播放完毕的从容器中删除
					vcBoom.removeElementAt(i);
				} else {
					vcBoom.elementAt(i).logic();
				}
			}
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
	}
	int shifoudialog=0;
	@Override
	public void run() {
		 Message	 msg = new  Message();
		 flag1=true;
		 if (zs==JsonUtil.qList.size()) {
			        flag1=false;
	         	    msg.what=zs;
					myhandler.sendMessage(msg);
					shifoudialog=1;
		  }
		while (flag1)  {
			
			long start = System.currentTimeMillis();
				myDraw();
				logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag1 = false;
	}
	
	/**
	 * 把触摸事件交给手势识别对象
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) { // 注意这里不能用ONTOUCHEVENT方法，不然无效的
		gd.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
	
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		        // 向zuo
				if (e1.getX() - e2.getX() > 60) {
					player.onUp(3);
				}
				// 向you
				if (e2.getX() - e1.getX() > 60) {
					player.onUp(4);
				}
				if (e1.getY() - e2.getY()>60) {
					player.onUp(1);
				}
				if(e1.getY() - e2.getY()<-60){
					player.onUp(2);
				}
		return false;
	}
	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		return false;
	}
	int zs = 0;
	private void inDialog() {
		itemIndex = JsonUtil.qList.size();
		Test.answer = JsonUtil.qList.get(zs).getAnswer();
		Getquestion.questidList.add(JsonUtil.qList.get(zs).getId());
		Test.item = new String[JsonUtil.qList.get(zs).getOplists().size()];
		for (int i = 0; i < JsonUtil.qList.get(zs).getOplists().size(); i++) {
			Test.item[i] = i + 1 + "、"+ JsonUtil.qList.get(zs).getOplists().get(i).getQcontext();
		}
		siDialog = new SingleSelectionDialog.Builder(context)
				.setTitle(JsonUtil.qList.get(zs).getTitle())
				.setSingleChoiceItems(Test.item, itemIndex,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								itemIndex = which;
								dialog.dismiss();
							}
						}).create();
	}
	int mf1 = 0;
	class myhandler extends Handler {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what != 0) {
				myhandler.postAtFrontOfQueue(httpsThread);
			}
			if (msg.obj != null) {
				if (msg.obj != null) {
					switch (JsonUtil.updateFlag) {
					case 1:
						builder.setUpdate("分数过低！");
						break;
					case 2:
						builder.setUpdate("无装备变化！");
						break;
					case 3:
						builder.setUpdate("你获得了新装备！");
						break;
					}
//					builder.setMessage("玩家：" + Test.realname + "\n" + " 得分：" + mf);
					builder.setRos(R.drawable.ic_go1);
					builder.setTitle("恭喜您得了"+mf+"分");
					if (JsonUtil.pass == 2) {
						builder.setPositiveButton("下一关",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int which) {
										dialog.dismiss();
										// 设置你的操作事项
										HttpUtil.getQuset();
									}
								});
					} else {
						builder.setPositiveButton("退出游戏",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										// 设置你的操作事项
										System.exit(0);
									//	flag1=true;
									//	gameState = GAME_MENU;
									}
								});
					   }
					builder.setNegativeButton(
							"继续游戏",
							new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
										gameState = GAME_MENU;
										//Boss状态设置为没出现
										isBoss = false;
										//重置游戏
										initGame();
										//重置怪物出场
										enemyArrayIndex = 0;}
								/*	if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
										gameState = GAME_MENU;
										//Boss状态设置为没出现
										isBoss = false;
										//重置游戏
										initGame();
										//重置怪物出场
										enemyArrayIndex = 0;
										}*/
								}
							});
					builder.create().show();
				}
			}
			if (msg.arg1 == 6) {
				inDialog();
				time=null;
				tt=null;
				ontStart();
				siDialog.show();
				DataUtil.setJ(j);
				DataUtil.setMf(mf);
				diss = true;
				zs++;
			}
			if (DataUtil.isClick()) {
				ontStop();
				i = 0;
				//t = 2;
				flag1=DataUtil.isRun();///////////////////////////////////////
				j = DataUtil.getJ();
				mf = DataUtil.getMf();
				DataUtil.setClick(false);
				surfaceCreated(sfh);
				//mark.setText("" + (mf));

			} else {
				if (msg.arg2 >= 30 && diss) {
					diss = false;
					ontStop();
					msg.arg1 = 0;
					siDialog.dismiss();
					// run=true;
					i = 0;
					j = j - 0;
					mf += (0);
					surfaceCreated(sfh);
				//	mark.setText("" + (mf));
				} else {
					DataUtil.setI(msg.arg2);
				}
			}
		}
	}
	boolean diss = false;
	int i = 0;
	Timer time = null;
	TimerTask tt = null;
	private void ontStart() {
		if (time == null) {
			time = new Timer();
		}
		if (tt == null) {
			tt = new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					i++;
					Message msg = new Message();
					msg.arg2 = i;
					myhandler.sendMessage(msg);
				}
			};
		}
		if (time != null && tt != null) {
			time.schedule(tt, 1000, 1000);
		}
	}
	private void ontStop() {
		if (time != null) {
			time.cancel();
		//	time = null;
		}
		if (tt != null) {
			tt.cancel();
		//	tt = null;
		}
	}
	String flag = null;
	Thread httpsThread = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg=new Message();
			flag= HttpUtil.httpget();
			msg.obj=flag;
			if (flag.equals("6")) {
				try {
					QuestionContentProviderUtil.testFind(context);
					ScheduleInfosContentProviderUtil.insert(context);
					UserInfoContentProviderUtil.update(Test.userid, context);
					EquipmentContentProviderUtil.insert(context);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(context, JsonUtil.describe, Toast.LENGTH_LONG).show();
				//finish();
			}
			myhandler.sendMessage(msg);
			// }
		}
	});
	/**
	 * 播放音效的方法
	 */
	public void playSound(int sound, int loop)
	{
		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}

}
