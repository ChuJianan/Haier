package com.haier.activity.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.FriendListAdapter;
import com.haier.adapter.NewsListAdapter;
import com.haier.adapter.RankingListAdapter;
import com.haier.app.AppClient;
import com.haier.bean.Friend;
import com.haier.bean.Friends;
import com.haier.bean.INews;
import com.haier.bean.LNews;
import com.haier.bean.Rank;
import com.haier.bean.Ranks;
import com.haier.bean.Sscion;
import com.haier.utils.DateUtils;
import com.haier.utils.StringUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public  class FriendFragment extends Fragment implements OnClickListener{
	//@ViewInject(R.id.newslistview)    private PullToRefreshListView  news_list;
	@ViewInject(R.id.search) private TextView search;
	@ViewInject(R.id.list)    private ListView news_list;
	@ViewInject(R.id.editfrend) private EditText edit;
	private FriendListAdapter newsListAdapter;
	private List<Friend> news=new ArrayList<Friend>();
	private View mNewsFooter;
	private String username;
	private String userString;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.contacts_layout, container, false);
		ViewUtils.inject(this, view);
		inview();
		return view;
	}
	private void loadNewsDatas(){
		new Thread(){
			public void run() {
				Message msg = new Message();
				try {
					Friends list=Friends.parse(AppClient.httpgetFriend(Sscion.getSsid(),userString));
					msg.what=list.getFileList().size();
					msg.obj=list;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
				}
				handler.sendMessage(msg);
				}
		}.start();
	}
	
	Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
        	super.handleMessage(msg);
        	if (msg.what>0) {
				Friends list=(Friends) msg.obj;
				if (msg.arg1==UIHelper.LISTVIEW_ACTION_REFRESH) {
					news.clear();
					news=list.getFileList();
				}else {
					List<Friend> iList=new ArrayList<Friend>(list.getFileList());
					news.removeAll(iList);
					news.addAll(iList);
				}
				newsListAdapter.notifyDataSetChanged();
        	}
		}	
	};
	private void inview() {
		// TODO Auto-generated method stub
		newsListAdapter=new FriendListAdapter(getActivity(),news);
		news_list.setAdapter(newsListAdapter);
		
	}
	
    @OnClick({R.id.search})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
    	switch(arg0.getId()){
		case R.id.search:
			username=edit.getText().toString();
			if(username.equals("")){
				UIHelper.ToastMessage(getActivity(), "不能为空");
			}else{
				try {
					news.clear();
					userString = URLEncoder.encode(username, "utf-8");
         	    } catch (UnsupportedEncodingException e) {
         	        e.printStackTrace();
         	    }
				loadNewsDatas();
			}
			break;
	}
    }
    }