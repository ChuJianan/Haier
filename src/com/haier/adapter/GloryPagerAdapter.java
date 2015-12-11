package com.haier.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haier.activity.fragment.GloryBmFragment;
import com.haier.activity.fragment.GloryCtFragment;
import com.haier.activity.fragment.GloryDqFragment;
import com.haier.activity.fragment.GloryZbFragment;

public class GloryPagerAdapter extends FragmentPagerAdapter{
	Context mContext;
	List<Fragment> fragments;
	public GloryPagerAdapter(Context context,FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = context;

		fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(mContext, GloryDqFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, GloryCtFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, GloryBmFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, GloryZbFragment.class.getName()));
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
