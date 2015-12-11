package com.haier.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.fragment.CarefulFragment;
import com.haier.activity.fragment.EquipmentFragment;
import com.haier.activity.fragment.NoteFragment;
import com.haier.activity.fragment.SellKnowledgeFragment;
import com.haier.activity.fragment.SetFragment;
import com.haier.bean.Music1;
import com.haier.bean.User;

public class knowledgeSellActivity extends FragmentActivity implements
		OnClickListener {
	/**
	 * 用于展示装备的Fragment
	 */
	private EquipmentFragment messageFragment;
	private TextView usernameTextView;
	private TextView corenumber;
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
	private SellKnowledgeFragment selectgameFragment;
	/**
	 * 用于发表学习笔记的界面
	 */
	private NoteFragment noteFragment;

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

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView back;

	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView contactsImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView newsImage;

	private ImageView selectImageView;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView settingImage;
	private FragmentManager fragmentManager;
	/**
	 * 判断人员身份标识
	 */
	private String roleflag;

	private ImageView message_image;
	private ImageView images;
	private LinearLayout linearmenu;
	private TextView text;
	private FrameLayout fragment;
	private LinearLayout linearLayout;
	private RelativeLayout backRelativeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weikasa);
		// 初始化布局元素
		initViews();
		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		messageLayout = findViewById(R.id.message_layout);
		// contactsLayout = findViewById(R.id.contacts_layout);
		newsLayout = findViewById(R.id.news_layout);
		settingLayout = findViewById(R.id.setting_layout);
		back = (ImageView) findViewById(R.id.back);
		roleflag = Music1.getRoleflag();
		usernameTextView = (TextView) findViewById(R.id.user);
		corenumber = (TextView) findViewById(R.id.corenumber);
		corenumber.setText(User.getUserscore());
			usernameTextView.setText(User.getName());
			usernameTextView.setText(User.getUserName());
		noteLayout = findViewById(R.id.notelayout);
		selectLayout = findViewById(R.id.selectlayout);
		selectLayout.setBackgroundResource(R.drawable.ic_select);
		
		selectImageView = (ImageView) findViewById(R.id.imageselect);
		images=(ImageView) findViewById(R.id.images);
		message_image=(ImageView) findViewById(R.id.message_image);
		newsImage = (ImageView) findViewById(R.id.news_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		back = (ImageView) findViewById(R.id.back);
		backRelativeLayout=(RelativeLayout)findViewById(R.id.backlinear);
		text = (TextView) findViewById(R.id.text);
		fragment = (FrameLayout) findViewById(R.id.content);
		linearLayout = (LinearLayout) findViewById(R.id.lineargame);
		linearmenu = (LinearLayout) findViewById(R.id.linearmenu);
		
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_munu);
		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
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
				R.drawable.ic_game_backn);
		bd = new BitmapDrawable(this.getResources(), bm);
		back.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_dibu);
		bd = new BitmapDrawable(this.getResources(), bm);
		text.setBackground(bd);
		
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_gamehome);
		bd = new BitmapDrawable(this.getResources(), bm);
		selectImageView.setBackground(bd);
		
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_yinyuedi);
		bd = new BitmapDrawable(this.getResources(), bm);
		linearLayout.setBackground(bd);
		bm = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_game_bottom_boder);
		bd = new BitmapDrawable(this.getResources(), bm);
		fragment.setBackground(bd);

		selectImageView.setOnClickListener(this);
		images.setOnClickListener(this);
		message_image.setOnClickListener(this);
		newsImage.setOnClickListener(this);
		settingImage.setOnClickListener(this);
		back.setOnClickListener(this);
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
			if (roleflag.equals("1")) {
				Intent intent = new Intent(knowledgeSellActivity.this,
						WeiGameActivity.class);
				startActivity(intent);
				finish();
			} else if (roleflag.equals("2") || roleflag.equals("3")) {
				Intent intent = new Intent(knowledgeSellActivity.this,
						WeiHaiKaSaActivity.class);
				startActivity(intent);
				finish();
			}
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
				selectgameFragment = new SellKnowledgeFragment();
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
		// contactsImage.setImageResource(R.drawable.contacts_unselected);
		// contactsText.setTextColor(Color.parseColor("#82858b"));
		// newsImage.setImageResource(R.drawable.news_unselected);
		// newsText.setTextColor(Color.parseColor("#82858b"));
		// settingImage.setImageResource(R.drawable.setting_unselected);
		// settingText.setTextColor(Color.parseColor("#82858b"));
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
		BitmapDrawable bd = (BitmapDrawable) linearmenu.getBackground();
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
		bd = (BitmapDrawable) back.getBackground();
		back.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) text.getBackground();
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
		System.gc();
	}
}
