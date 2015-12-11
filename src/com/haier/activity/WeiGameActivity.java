package com.haier.activity;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.haier.R;
import com.haier.activity.fragment.CarefulFragment;
import com.haier.activity.fragment.FriendFragment;
import com.haier.activity.fragment.EquipmentFragment;
import com.haier.activity.fragment.NoteFragment;
import com.haier.activity.fragment.RankingFragment;
import com.haier.activity.fragment.RuleFragment;
import com.haier.activity.fragment.SelectgameFragment;
import com.haier.activity.fragment.SetFragment;
import com.haier.app.AppClient;
import com.haier.bean.AskallInfos;
import com.haier.bean.Askalls;
import com.haier.bean.Music1;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.bean.User;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author Administrator
 * 
 */
public class WeiGameActivity extends FragmentActivity implements
		OnClickListener {
	/**
	 * 静态全局变量
	 */
	public static WeiGameActivity weiGameActivity;
	
	private CustomProgressDialog progressDialog = null;
	/**
	 * 用于展示消息的Fragment
	 */
	private EquipmentFragment messageFragment;
	/**
	 * 用于展示联系人的Fragment
	 */
	private FriendFragment contactsFragment;
	/**
	 * 用于展示动态的Fragment
	 */
	private SetFragment newsFragment;
	/**
	 * 用于展示设置的Fragment
	 */

	private CarefulFragment settingFragment;
	/**
	 * 用于展示选择游戏的界面
	 */
	private SelectgameFragment selectgameFragment;
	/**
	 * 用于发表学习笔记的界面
	 */
	private NoteFragment noteFragment;
	/**
	 * 用于显示好友排名的界面
	 */
	private RankingFragment scoreFragment;
	/**
	 * 消息界面布局
	 */
	private View messageLayout;
	/**
	 * 联系人界面布局
	 */
	private View contactsLayout;
	/**
	 * 动态界面布局
	 */
	private View newsLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 选择游戏界面布局
	 */
	private View selectLayout;
	/**
	 * 学习笔记界面布局
	 */
	private View noteLayout;
	/**
	 * 装备界面布局
	 */
	private View scoreLayout;
	private Handler mNewsHandler;
	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView contactsImage;
	private ImageView back;
	
    private ImageView message_image;
    private ImageView images;
    private ImageView newsImage;
    private ImageView backk;
    private ImageView settingImage;
    private LinearLayout  linearmenu;
	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	
  
	private ImageView selectImageView;
   
	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	private TextView usernameTextView;
	private TextView corenumber;
	
	List<AskallInfos> list;
	String roleflag;
  private TextView text;
  private  FrameLayout fragment;
  private LinearLayout linearLayout;
	private RelativeLayout backRelativeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weigame);
		ViewUtils.inject(this);
		initViews();
		weiGameActivity=this;
		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	
	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
	//	roleflag=Music1.getRoleflag();
		back = (ImageView) findViewById(R.id.back);
		usernameTextView = (TextView) findViewById(R.id.user);
		corenumber = (TextView) findViewById(R.id.corenumber);
		corenumber.setText(User.getUserscore());
		usernameTextView.setText(User.getName());
		text = (TextView) findViewById(R.id.text);
		text.setText(Music1.getRESULTS());
		fragment=(FrameLayout)findViewById(R.id.content);
		linearLayout=(LinearLayout)findViewById(R.id.lineargame);
		selectLayout = findViewById(R.id.selectlayout);
		noteLayout = findViewById(R.id.notelayout);
		messageLayout = findViewById(R.id.message_layout);
		newsLayout = findViewById(R.id.news_layout);
		settingLayout = findViewById(R.id.setting_layout);
		
		
		selectImageView = (ImageView) findViewById(R.id.imageselect);
		images=(ImageView) findViewById(R.id.images);
		message_image=(ImageView) findViewById(R.id.message_image);
		newsImage = (ImageView) findViewById(R.id.news_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		selectLayout.setBackgroundResource(R.drawable.ic_select);
		
		backRelativeLayout=(RelativeLayout)findViewById(R.id.backlinear);
		linearmenu=(LinearLayout)findViewById(R.id.linearmenu);
		
		backk = (ImageView) findViewById(R.id.back);
		
		
		
		selectImageView.setOnClickListener(this);
		images.setOnClickListener(this);
		message_image.setOnClickListener(this);
		newsImage.setOnClickListener(this);
		settingImage.setOnClickListener(this);
		back.setOnClickListener(this);
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_dibu);
		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
		text.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_yinyuedi);
		bd = new BitmapDrawable(this.getResources(), bm);
		linearLayout.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_bottom_boder);
		bd = new BitmapDrawable(this.getResources(), bm);
		fragment.setBackground(bd);
		
		
		
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_munu);
		bd = new BitmapDrawable(this.getResources(), bm);
		linearmenu.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_info);
		bd = new BitmapDrawable(this.getResources(), bm);
		images.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_zhuangbei);
		bd = new BitmapDrawable(this.getResources(), bm);
		message_image.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_set);
		bd = new BitmapDrawable(this.getResources(), bm);
		newsImage.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_zhuyi);
		bd = new BitmapDrawable(this.getResources(), bm);
		settingImage.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_gamehome);
		bd = new BitmapDrawable(this.getResources(), bm);
		selectImageView.setBackground(bd);
		
		
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_backn);
		bd = new BitmapDrawable(this.getResources(), bm);
		backk.setBackground(bd);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			backRelativeLayout.setBackgroundResource(R.drawable.ic_select);
			selectLayout.setBackgroundResource(0);
			noteLayout.setBackgroundResource(0);
			messageLayout.setBackgroundResource(0);
			newsLayout.setBackgroundResource(0);
			settingLayout.setBackgroundResource(0);
			finish();
			break;
		case R.id.imageselect:
			setTabSelection(0);
			selectLayout.setBackgroundResource(R.drawable.ic_select);
			noteLayout.setBackgroundResource(0);
			messageLayout.setBackgroundResource(0);
			newsLayout.setBackgroundResource(0);
			settingLayout.setBackgroundResource(0);
			
			break;
		case R.id.images:
			setTabSelection(1);
			selectLayout.setBackgroundResource(0);
			noteLayout.setBackgroundResource(R.drawable.ic_select);
			messageLayout.setBackgroundResource(0);
			newsLayout.setBackgroundResource(0);
			settingLayout.setBackgroundResource(0);
			break;
		case R.id.message_image:
			setTabSelection(2);
			selectLayout.setBackgroundResource(0);
			noteLayout.setBackgroundResource(0);
			messageLayout.setBackgroundResource(R.drawable.ic_select);
			newsLayout.setBackgroundResource(0);
			settingLayout.setBackgroundResource(0);
			break;
		case R.id.news_image:
			setTabSelection(3);
			selectLayout.setBackgroundResource(0);
			noteLayout.setBackgroundResource(0);
			messageLayout.setBackgroundResource(0);
			newsLayout.setBackgroundResource(R.drawable.ic_select);
			settingLayout.setBackgroundResource(0);
			break;
		case R.id.setting_image:
			setTabSelection(4);
			selectLayout.setBackgroundResource(0);
			noteLayout.setBackgroundResource(0);
			messageLayout.setBackgroundResource(0);
			newsLayout.setBackgroundResource(0);
			settingLayout.setBackgroundResource(R.drawable.ic_select);
			break;

		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			if (selectgameFragment == null) {
				selectgameFragment = new SelectgameFragment();
				transaction.add(R.id.content, selectgameFragment);
			} else {
				transaction.show(selectgameFragment);
			}
			break;
		case 1:
			if (noteFragment == null) {
				noteFragment = new NoteFragment();
				transaction.add(R.id.content, noteFragment);
			} else {
				transaction.show(noteFragment);
			}
			break;
		case 2:
			if (messageFragment == null) {
				messageFragment = new EquipmentFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				transaction.show(messageFragment);
			}
			break;
		case 3:
			if (newsFragment == null) {
				newsFragment = new SetFragment();
				transaction.add(R.id.content, newsFragment);
			} else {
				transaction.show(newsFragment);
			}
			break;
		case 4:
		default:
			if (settingFragment == null) {
				settingFragment = new CarefulFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		// messageImage.setImageResource(R.drawable.message_unselected);
		// messageText.setTextColor(Color.parseColor("#82858b"));
	}
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (selectgameFragment != null) {
			transaction.hide(selectgameFragment);
		}
		if (noteFragment != null) {
			transaction.hide(noteFragment);
		}
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BitmapDrawable bd = (BitmapDrawable) text.getBackground();
		text.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) linearLayout.getBackground();
		linearLayout.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) fragment.getBackground();
		fragment.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) linearmenu.getBackground();
		linearmenu.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) images.getBackground();
		images.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) message_image.getBackground();
		message_image.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) newsImage.getBackground();
		newsImage.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) settingImage.getBackground();
		settingImage.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) backk.getBackground();
		backk.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		
		System.gc();
	}
}
