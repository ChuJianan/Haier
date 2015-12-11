package com.haier.utils;

import java.util.ArrayList;
import java.util.List;

import com.haier.activity.fragment.Game1Activity;
import com.haier.activity.fragment.Game2Activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SelectGamePagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	List<Fragment> fragments;
	public SelectGamePagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		mContext = context;

		fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(mContext, Game1Activity.class.getName()));
		fragments.add(Fragment.instantiate(mContext, Game2Activity.class.getName()));
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}
	
}
