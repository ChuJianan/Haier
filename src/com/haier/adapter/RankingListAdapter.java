package com.haier.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.haier.R;
import com.haier.bean.Rank;
import com.lidroid.xutils.BitmapUtils;
public class RankingListAdapter extends BaseAdapter{

	Context context;
	List<Rank> list;
	BitmapUtils bitmapUtils;
	private LayoutInflater listContainer; // 视图容器
	static class ViewHolder { //自定义控件集合  
		public ImageView icon;
		public TextView title;
		public TextView rank;
		public TextView totalScore;
	}
	public RankingListAdapter (Context context,List<Rank> list){
		this.listContainer = LayoutInflater.from(context);
		this.bitmapUtils=new BitmapUtils(context);
		this.list=list;
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.ranking_item, null);
			holder = new ViewHolder();
			holder.title=(TextView)convertView.findViewById(R.id.username1);
			holder.rank=(TextView)convertView.findViewById(R.id.number);
			holder.totalScore=(TextView)convertView.findViewById(R.id.totalScore1);
			holder.icon=(ImageView)convertView.findViewById(R.id.userimage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Rank iNews=list.get(arg0);
		holder.title.setText(iNews.getUserName());
		holder.rank.setText(String.valueOf(iNews.getRanking()) );
		holder.totalScore.setText(iNews.getTotalScore());
		//bitmapUtils.display(holder.icon, uri);
		return convertView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(arg0-1);
	}
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
