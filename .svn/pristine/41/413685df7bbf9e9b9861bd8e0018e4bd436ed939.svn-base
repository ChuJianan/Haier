package com.haier.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haier.activity.fragment.AboutFragment;
import com.haier.activity.fragment.RuleFragment;

public class CarefulPagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	List<Fragment> fragments;
	public CarefulPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		mContext = context;

		fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(mContext, RuleFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, AboutFragment.class.getName()));
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
