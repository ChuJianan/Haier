package com.haier.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haier.R;
import com.haier.bean.INews;
import com.haier.bean.RemindInfos;

public class RNewsListAdapter extends BaseAdapter{
	Context context;
	List<RemindInfos> list;
	private LayoutInflater listContainer; // 视图容器
	static class ViewHolder { //自定义控件集合  
//		public ImageView icon;
		public TextView title;
//		public TextView size;
//		public TextView author;
	}
	public RNewsListAdapter (Context context,List<RemindInfos> list){
		this.listContainer = LayoutInflater.from(context);
		this.list=list;
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.activity_news_list_item, null);
			holder = new ViewHolder();
			holder.title=(TextView)convertView.findViewById(R.id.titles);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		RemindInfos iNews=list.get(arg0);
		holder.title.setText(iNews.getDescription());
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
