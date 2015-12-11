package com.haier.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haier.R;
import com.haier.bean.Music1;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SetFragment extends Fragment implements OnClickListener{
  //  @ViewInject(R.id.sound1)   private ImageButton music1;
  //  @ViewInject(R.id.sound2)   private ImageButton music2;
    @ViewInject(R.id.img1)     private  ImageView img1;
    @ViewInject(R.id.img2)     private  ImageView img2;
    @ViewInject(R.id.img3)     private  ImageView img3;
    @ViewInject(R.id.img4)     private  ImageView img4;
    @ViewInject(R.id.img5)     private  ImageView img5;
    @ViewInject(R.id.img6)     private  ImageView img6;
    @ViewInject(R.id.yu)       private  LinearLayout linearLayout1;
    @ViewInject(R.id.yuyu)     private  LinearLayout linearLayout2;
    private boolean mus=false;
    private int sou=0;
    private int sou1=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.soundset, container,
				false);
		ViewUtils.inject(this, newsLayout);
		Test();
		return newsLayout;
	}
	private void Test() {
		// TODO Auto-generated method stub
		if(Music1.isMusic1()==true){
			img1.setBackgroundResource(R.drawable.ic_game_tiao1);
			img2.setBackground(null);
			img3.setBackgroundResource(R.drawable.ic_game_kaiguan);
		}else {
			img1.setBackgroundResource(R.drawable.ic_game_tiao2);
			img3.setBackground(null);
			img2.setBackgroundResource(R.drawable.ic_game_kaiguan);
		}
		if(Music1.isMusic2()==true){
			img4.setBackgroundResource(R.drawable.ic_game_tiao1);
			img5.setBackground(null);
			img6.setBackgroundResource(R.drawable.ic_game_kaiguan);
		}else {
			img4.setBackgroundResource(R.drawable.ic_game_tiao2);
			img6.setBackground(null);
			img5.setBackgroundResource(R.drawable.ic_game_kaiguan);
		}
	}
	@OnClick({R.id.yu,R.id.yuyu})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){  
		case R.id.yu:
			if(sou==0){
				//music1.setBackgroundResource(R.drawable.ic_shegnyin);
				Music1.setMusic1(true);
				img1.setBackgroundResource(R.drawable.ic_game_tiao1);
				img2.setBackground(null);
				img3.setBackgroundResource(R.drawable.ic_game_kaiguan);
				sou=1;
			}else {
				//music1.setBackgroundResource(R.drawable.sound1);
				Music1.setMusic1(false);
				img1.setBackgroundResource(R.drawable.ic_game_tiao2);
				img3.setBackground(null);
				img2.setBackgroundResource(R.drawable.ic_game_kaiguan);
				sou=0;
			}
			break;
		case R.id.yuyu:
			if(sou1==0){
				//music1.setBackgroundResource(R.drawable.ic_shegnyin);
				Music1.setMusic1(true);
				img4.setBackgroundResource(R.drawable.ic_game_tiao1);
				img5.setBackground(null);
				img6.setBackgroundResource(R.drawable.ic_game_kaiguan);
				sou1=1;
			}else {
				//music1.setBackgroundResource(R.drawable.sound1);
				Music1.setMusic1(false);
				img4.setBackgroundResource(R.drawable.ic_game_tiao2);
				img6.setBackground(null);
				img5.setBackgroundResource(R.drawable.ic_game_kaiguan);
				sou1=0;
			}
			break;
		
		}
	}
}
