package com.haier.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.MainActivity;
import com.haier.app.AppClient;
import com.haier.bean.Friend;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.BitmapUtils;

public class FriendListAdapter extends BaseAdapter{
	Context context;
	List<Friend> list;
	private BitmapUtils bmpUtils;
	private LayoutInflater listContainer; // 视图容器
	static class ViewHolder { //自定义控件集合  
		public ImageView icon;
		public TextView title;
		public TextView rank;
		public TextView totalScore;
		public Button addbtn;
	}
	public FriendListAdapter (Context context,List<Friend> list){
		this.listContainer = LayoutInflater.from(context);
		this.bmpUtils = new BitmapUtils(context);
		this.context=context;
		this.list=list;
	}
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.friend_list_item, null);
			holder = new ViewHolder();
			holder.title=(TextView)convertView.findViewById(R.id.username2);
			holder.icon=(ImageView)convertView.findViewById(R.id.userimage);
			//holder.rank=(TextView)convertView.findViewById(R.id.number);
			//holder.totalScore=(TextView)convertView.findViewById(R.id.totalScore1);
			holder.addbtn=(Button)convertView.findViewById(R.id.addbtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		final Friend iNews=list.get(arg0);
		String a=iNews.getUserName();
		holder.title.setText(a);
		//bmpUtils.display(holder.icon, URLs.HOST+iNews.getPath());
		holder.addbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String flag=AppClient.httpgetaddFriend(Sscion.getSsid(), iNews.getId());
				if (flag.equals("6")) {
					Dialog(JsonUtils.describe);
				}else {
					Dialog(JsonUtils.describe);
				}
			}
		});
		return convertView;
	}
	protected void Dialog(String event) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(context).setTitle(event)
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(arg0-1);
	}
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
