package com.haier.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.haier.R;
import com.haier.adapter.WeiFriendPagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WeiFeiends extends FragmentActivity {
	@ViewInject(R.id.back)
	private Button imageButton;
	@ViewInject(R.id.ask)
	private Button askButton;

	private FragmentManager mFragmentManager;
	private WeiFriendPagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
    public static FragmentActivity friendsActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weifriends);
		ViewUtils.inject(this);
		friendsActivity=this;
		mFragmentManager = getSupportFragmentManager();
		findView();
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void findView() {
		// TODO Auto-generated method stub
		final RelativeLayout ll = (RelativeLayout) findViewById(R.id.ll);
		mPagerAdapter = new WeiFriendPagerAdapter(mFragmentManager, this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int arg0) {
						for (int i = 0; i < ll.getChildCount(); i++) {
							ll.getChildAt(i).setSelected(false);
						}
						int i = arg0 < 2 ? arg0 : arg0 + 1;
						ll.getChildAt(i).setSelected(true);
					}
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}
					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
		View.OnClickListener cl = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < ll.getChildCount(); i++) {
					ll.getChildAt(i).setSelected(false);
				}
				v.setSelected(true);

				switch (v.getId()) {
				case R.id.jtbtn:
					mViewPager.setCurrentItem(0, false);
					break;
				case R.id.sybbtn:
					mViewPager.setCurrentItem(1, false);
					break;
				}
			}
		};
		findViewById(R.id.jtbtn).setSelected(true);
		findViewById(R.id.jtbtn).setOnClickListener(cl);
		findViewById(R.id.sybbtn).setOnClickListener(cl);
	}

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/
}
