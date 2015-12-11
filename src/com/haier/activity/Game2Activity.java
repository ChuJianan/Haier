package com.haier.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haier.R;
import com.lidroid.xutils.ViewUtils;

public class Game2Activity extends Fragment{
 
 @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	 View view=inflater.inflate(R.layout.activity_game2, container, false);
	 ViewUtils.inject(this, view);
	return view;
}
}
