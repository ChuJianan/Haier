package com.haier.adapter;

import java.util.Collections;
import java.util.List;

import com.haier.R;
import com.haier.bean.ListItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewSearchAdapter extends BaseAdapter {
	private LayoutInflater mContainer;
	private List<? extends ListItem> data = Collections.emptyList();
	
	static class ViewHolder {
		public TextView title;
		public ImageView image;
	}
	
	public ListViewSearchAdapter(Context context, List<? extends ListItem> data) {
		mContainer = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mContainer.inflate(R.layout.listitem_search, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ListItem item = data.get(position);
		holder.title.setText(item.getTitle());
		holder.image.setImageResource(item.getImageResourse());
		return convertView;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
