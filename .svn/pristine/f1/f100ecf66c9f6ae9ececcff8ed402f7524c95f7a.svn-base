package com.haier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haier.R;
import com.haier.db.KnowledgeDB;
import com.haier.utils.JsonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Game1Activity extends Fragment{
	@ViewInject(R.id.imageView1) ImageView  imageView1;
	@ViewInject(R.id.imageView2) ImageView   imageView2;
	@ViewInject(R.id.imageView3) ImageView   imageView3;
	@ViewInject(R.id.text1) TextView text1;
	@ViewInject(R.id.text2) TextView text2;
	@ViewInject(R.id.text3) TextView text3;
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_game1, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		findView();   //初始化视图
		loadHistoryData();
	}
 
    private void loadHistoryData() {
		// TODO Auto-generated method stub
    	 String [] a;
   	     KnowledgeDB knowledgeDB=new KnowledgeDB(getActivity());
   	     a=knowledgeDB.select1();
         text1.setText(a[0]);
         text2.setText(a[1]);
         text3.setText(a[2]);
        imageView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intent=new Intent(getActivity(),KnowledgeActivity.class);
				String na="1";
				JsonUtils.na=na;
				startActivity(intent);
			}
		});
//        imageView2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				/* Animation shake=AnimationUtils.loadAnimation(mContext, R.anim.shake_x);
//				 imageView.startAnimation(shake);*/
////				Intent intent=new Intent();
////				String na="2";
////				intent.setClass(Game1Activity.this, KnowledgeActivity.class);
////				intent.putExtra("na", na);
////				startActivity(intent);
//			}
//		});
//        imageView3.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				/* Animation shake=AnimationUtils.loadAnimation(mContext, R.anim.shake_x);
//				 imageView.startAnimation(shake);*/
////				Intent intent=new Intent();
////				String na="3";
////				intent.setClass(Game1Activity.this, KnowledgeActivity.class);
////				intent.putExtra("na", na);
////				startActivity(intent);
//			}
//		});
	}
}
