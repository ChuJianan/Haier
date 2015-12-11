package com.haier.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.R;
import com.haier.activity.forum.AskAllActivity;
import com.haier.activity.forum.AskExpertActivity;
import com.haier.adapter.InteractPagerAdapter;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class InteractActivity extends FragmentActivity {

	@ViewInject(R.id.back)
	private Button imageButton;
	@ViewInject(R.id.ask)
	private Button askButton;
	@ViewInject(R.id.top_title)
	private TextView top_title;
	private FragmentManager mFragmentManager;
	private InteractPagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
	final static int REQUEST = 10;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_interact);
		ViewUtils.inject(this);
		top_title.setText("微互动");
		mFragmentManager = getSupportFragmentManager();
		findView();
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		askButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (mViewPager.getCurrentItem()) {
				case 0:
					startActivity(new Intent(InteractActivity.this, AskAllActivity.class));
					break;
				case 1:
					startActivity(new Intent(InteractActivity.this, AskExpertActivity.class));
					break;
				case 2:
					UIHelper.ToastMessage(getApplicationContext(), "问题已经显示在页面中！");
					break;
				default:
					break;
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		List<Fragment> fragments = mFragmentManager.getFragments();
		if (fragments != null) {
			for (Fragment fragment : fragments) {
				fragment.setUserVisibleHint(true);
			}
		}
	}

	private void findView() {
		// TODO Auto-generated method stub
		final LinearLayout ll = (LinearLayout) findViewById(R.id.bottom_bar);

		mPagerAdapter = new InteractPagerAdapter(this, mFragmentManager);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < ll.getChildCount(); i++) {
					ll.getChildAt(i).setSelected(false);
				}
				ll.getChildAt(arg0).setSelected(true);
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
				case R.id.btn_home:
					mViewPager.setCurrentItem(0, false);
					break;
				case R.id.btn_cloud:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.btn_notice:
					mViewPager.setCurrentItem(2, false);
					break;

				}
			}
		};
		findViewById(R.id.btn_home).setSelected(true);
		findViewById(R.id.btn_home).setOnClickListener(cl);
		findViewById(R.id.btn_cloud).setOnClickListener(cl);
		findViewById(R.id.btn_notice).setOnClickListener(cl);
	}
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode==0) {
	            if (resultCode==-1) {
	            	switch (mViewPager.getCurrentItem()) {
					case 0:
						
						break;
					case 1:
						
						break;
						}
	            }else if (resultCode==0) {
	                Toast.makeText(this, "result cancel", Toast.LENGTH_SHORT).show();
	            }else {
	                Toast.makeText(this, resultCode, Toast.LENGTH_SHORT).show();
	            }
	        }else {
	        	switch (mViewPager.getCurrentItem()) {
				case 0:
					
					break;
				case 1:
					
					break;
					}
	        }
	    };
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/
}
