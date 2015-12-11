package com.haier.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.GloryPagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class GloryActivity extends FragmentActivity{

	 @ViewInject(R.id.back)     private Button  imageButton;
	 @ViewInject(R.id.top_title)private TextView top_title;
	 @ViewInject(R.id.ask)      private Button askButton;
	 @ViewInject(R.id.btn_home) private Button btnhomedaqu;
	 @ViewInject(R.id.btn_cloud)private Button btnhomechengshi;
	 @ViewInject(R.id.btn_notice) private Button btnhomebumen;
	 @ViewInject(R.id.btn_more) private Button btnhomezoubu;
	 
		private FragmentManager mFragmentManager;
		private GloryPagerAdapter mPagerAdapter;
		private ViewPager mViewPager;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_glory);
			ViewUtils.inject(this);
			top_title.setText("微荣誉");
			mFragmentManager = getSupportFragmentManager();
	        findView();
	        imageButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View arg0) {
	                // TODO Auto-generated method stub
	                finish();
	            }
	        });
			askButton.setVisibility(View.INVISIBLE);
		}
		 private void findView() {
		        // TODO Auto-generated method stub
			 final LinearLayout ll = (LinearLayout) findViewById(R.id.bottom_bar);
				
				mPagerAdapter = new GloryPagerAdapter(this, mFragmentManager);
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
					public void onPageScrolled(int arg0, float arg1, int arg2) { }
					@Override
					public void onPageScrollStateChanged(int arg0) { }
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
							btnhomedaqu.setTextColor(R.color.lightblue);
							btnhomechengshi.setTextColor(Color.GRAY);
							btnhomebumen.setTextColor(Color.GRAY);
							btnhomezoubu.setTextColor(Color.GRAY);
							mViewPager.setCurrentItem(0, false);
							break;
						case R.id.btn_cloud:
							btnhomedaqu.setTextColor(Color.GRAY);
							btnhomechengshi.setTextColor(R.color.lightblue);
							btnhomebumen.setTextColor(Color.GRAY);
							btnhomezoubu.setTextColor(Color.GRAY);
							mViewPager.setCurrentItem(1, false);
							break;
						case R.id.btn_notice:
							btnhomedaqu.setTextColor(Color.GRAY);
							btnhomechengshi.setTextColor(Color.GRAY);
							btnhomebumen.setTextColor(R.color.lightblue);
							btnhomezoubu.setTextColor(Color.GRAY);
							mViewPager.setCurrentItem(2, false);
							break;
						case R.id.btn_more:
							btnhomedaqu.setTextColor(Color.GRAY);
							btnhomechengshi.setTextColor(Color.GRAY);
							btnhomebumen.setTextColor(Color.GRAY);
							btnhomezoubu.setTextColor(R.color.lightblue);
							mViewPager.setCurrentItem(3, false);
							break;
						}
					}
				};
				findViewById(R.id.btn_home).setSelected(true);
				findViewById(R.id.btn_home).setOnClickListener(cl);
				findViewById(R.id.btn_more).setOnClickListener(cl);
				findViewById(R.id.btn_cloud).setOnClickListener(cl);
				findViewById(R.id.btn_notice).setOnClickListener(cl);
		    }
		  /*  @Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {

		        switch (keyCode) {
		            case KeyEvent.KEYCODE_BACK:
		            return true;
		        }
		        return super.onKeyDown(keyCode, event);
		    }*/
}
