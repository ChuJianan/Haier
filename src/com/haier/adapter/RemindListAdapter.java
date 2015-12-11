package com.haier.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haier.R;

public class RemindListAdapter extends BaseAdapter{

	Context context;
	String[] description=new String[]{"新闻和通知","专家回帖","好友验证","问自己调研"};
	List<Integer> remindsize;
	
	private LayoutInflater listContainer; // 视图容器
	static class ViewHolder { //自定义控件集合  
//		public ImageView icon;
		public TextView title;
		public TextView size;
//		public TextView author;
	}
	
	public RemindListAdapter (Context context,List<Integer> list){
		this.listContainer = LayoutInflater.from(context);
		this.remindsize=list;
	}
	
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.list_remind_item, null);
			holder = new ViewHolder();
			holder.title=(TextView)convertView.findViewById(R.id.remind_text);
			holder.size=(TextView)convertView.findViewById(R.id.remindsize);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.title.setText(description[arg0]);
		if (remindsize.get(arg0)!=0) {
			holder.size.setText(remindsize.get(arg0).toString());
		}else {
			holder.size.setText("0");
		}
		
		return convertView;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return remindsize == null ? 0 : remindsize.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return remindsize == null ? null : remindsize.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
