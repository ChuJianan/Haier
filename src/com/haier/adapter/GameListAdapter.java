package com.haier.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haier.R;
import com.haier.bean.GameList;

public class GameListAdapter extends BaseAdapter{
	 private List<GameList> listItems = Collections.emptyList();    //��ݼ���
	 private LayoutInflater mContainer;  //��ͼ����
	    static class ViewHolder { //�Զ�����ͼ
	        public TextView id;
	        public TextView path;
	    }
	    public GameListAdapter(Context context, List<GameList> data) {
			this.mContainer = LayoutInflater.from(context);
			this.listItems = data;
		}
	public void add(List<GameList> gamelists){
		listItems.addAll(gamelists);
	}
	public void setComments(List<GameList> gamelist) {
		this.listItems = gamelist;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null){
			convertView = mContainer.inflate(R.layout.gamelist_item, null);
			holder = new ViewHolder();
			holder.id = (TextView) convertView.findViewById(R.id.gameid);
			holder.path = (TextView) convertView.findViewById(R.id.gamepath);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameList gamelist = listItems.get(position);
		holder.id.setText(gamelist.getId());
		holder.path.setText(gamelist.getPath());
		return convertView;
	}
	@Override
	public int getCount(){
		// TODO Auto-generated method stub
		return listItems == null ? 0 : listItems.size();
	}
	@Override
	public Object getItem(int position){
		// TODO Auto-generated method stub
		return listItems == null ? null : listItems.get(position);
	}
	@Override
	public long getItemId(int position){
		// TODO Auto-generated method stub
		return 0;
	}
}
