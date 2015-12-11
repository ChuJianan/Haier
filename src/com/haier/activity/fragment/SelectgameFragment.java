package com.haier.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.haier.R;
import com.haier.adapter.SelectGamePagerAdapter;

public class SelectgameFragment extends Fragment {
	private ViewPager mGamePager;
	private SelectGamePagerAdapter mPagerAdapter;
	private RadioGroup mRadioGroup;
	private RadioButton kasaButton;
	private RadioButton tongshuaiButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.selectgame1, container,
				false);
		mGamePager = (ViewPager) settingLayout.findViewById(R.id.pager);
		mRadioGroup = (RadioGroup) settingLayout.findViewById(R.id.radio_group);
		kasaButton = (RadioButton) settingLayout.findViewById(R.id.radio_kasa);
		tongshuaiButton= (RadioButton) settingLayout.findViewById(R.id.radio_tongshuai);
		return settingLayout;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPagerAdapter = new SelectGamePagerAdapter(getChildFragmentManager(),
				getActivity());
		mGamePager.setAdapter(mPagerAdapter);
		mRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radio_kasa:
							mGamePager.setCurrentItem(0);
						//	kasaButton.setBackgroundResource(R.drawable.ic_game_hk);
						//tongshuaiButton.setBackgroundResource(R.drawable.ic_game_tongshuai);
							break;
						case R.id.radio_tongshuai:
							mGamePager.setCurrentItem(1);
							//kasaButton.setBackgroundResource(R.drawable.ic_game_hk2);
							//tongshuaiButton.setBackgroundResource(R.drawable.ic_game_tongshuain);
							break;
						default:
							break;
						}
					}
			});
		 View.OnClickListener cl = new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
	                	mRadioGroup.getChildAt(i).setSelected(false);
	                }
	            }
	        };
	        mGamePager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
						mRadioGroup.getChildAt(i).setSelected(false);
					}
					if(arg0 == 0){
						kasaButton.setBackgroundResource(R.drawable.ic_game_hk);
						tongshuaiButton.setBackgroundResource(R.drawable.ic_game_hk2);
				   }else if (arg0 == 1) {
					 kasaButton.setBackgroundResource(R.drawable.ic_game_hk2);
				     tongshuaiButton.setBackgroundResource(R.drawable.ic_game_hk);
				  }
					mRadioGroup.getChildAt(arg0).setSelected(true);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
}
