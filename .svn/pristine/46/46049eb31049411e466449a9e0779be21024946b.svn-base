package com.haier.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.haier.activity.fragment.FriendFragment;
import com.haier.activity.fragment.RankingFragment;

public class WeiFriendPagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	List<Fragment> fragments;

	public WeiFriendPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = context;

		fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(mContext,
				RankingFragment.class.getName()));
		fragments.add(Fragment.instantiate(mContext,
				FriendFragment.class.getName()));
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
