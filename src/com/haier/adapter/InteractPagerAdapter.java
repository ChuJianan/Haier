package com.haier.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haier.activity.fragment.AskAllFragment;
import com.haier.activity.fragment.AskExoertsFragment;
import com.haier.activity.fragment.AskMeFragment;
import com.haier.activity.fragment.GloryBmFragment;
import com.haier.activity.fragment.GloryCtFragment;
import com.haier.activity.fragment.GloryDqFragment;
import com.haier.activity.fragment.GloryZbFragment;

public class InteractPagerAdapter extends FragmentPagerAdapter{
	Context mContext;
	List<Fragment> fragments;
	public InteractPagerAdapter(Context context,FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = context;

		fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(mContext, AskAllFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, AskExoertsFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext, AskMeFragment.class.getName()));
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
