package com.haier.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haier.R;
import com.haier.bean.Glory;
import com.haier.db.UserDB;

public class GloryListAdapter extends BaseAdapter{

	Context context;
	List<Glory> list;
	UserDB userDB;
	String[] aStrings=new String[5];
	private LayoutInflater listContainer; // 视图容器
	static class ViewHolder { //自定义控件集合  
//		public ImageView icon;
		public TextView title;
		public TextView rank;
		public TextView totalScore;
		public RelativeLayout relativeLayout;
	}
	public GloryListAdapter (Context context,List<Glory> list){
		this.listContainer = LayoutInflater.from(context);
		this.list=list;
		userDB=new UserDB(context);
		aStrings=userDB.select2();
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.list_glory_item, null);
			holder = new ViewHolder();
			holder.title=(TextView)convertView.findViewById(R.id.username);
			holder.rank=(TextView)convertView.findViewById(R.id.rank);
			holder.totalScore=(TextView)convertView.findViewById(R.id.totalScore);
			holder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.rel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Glory iNews=list.get(arg0);
		if (iNews.getUserName().equals(aStrings[1])) {
			holder.relativeLayout.setBackgroundResource(R.color.red);
		}
		holder.title.setText(iNews.getUserName());
		holder.rank.setText(String.valueOf(iNews.getRanking()) );
		holder.totalScore.setText(iNews.getTotalScore());
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
