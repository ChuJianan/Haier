package com.haier.activity.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.forum.AskExpertReplyActivity;
import com.haier.adapter.AskAllListAdapter;
import com.haier.app.AppClient;
import com.haier.bean.AskallInfos;
import com.haier.bean.Askalls;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.fragment.BaseFragment;
import com.haier.utils.DateUtils;
import com.haier.utils.JsonUtils;
import com.haier.utils.StringUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AskExoertsFragment extends BaseFragment {

	@ViewInject(R.id.newslistview)
	private PullToRefreshListView news_list;
	@ViewInject(R.id.back)
	private Button back;
	private AskAllListAdapter newsListAdapter;
	private List<AskallInfos> news = new ArrayList<AskallInfos>();
	private View mNewsFooter;
	private TextView mNewsMore;
	private ProgressBar mNewsProgress;
	private Handler mNewsHandler;
	private int pageNo = 1;// 当前页数
	private int pageSize = 10;// 每页个数
	private long mLastTime; // 上次加载时间
	private boolean isViewInited = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_glorybm, null);
		ViewUtils.inject(this, view);
		news.clear();
		pageNo = 1;
		inuiView();
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		news.clear();
		pageNo = 1;
		loaDatas();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint() && isViewInited) {
			//如果直接点击跳转到本Fragment，setUserVisibleHint方法会先于
			//onCreateView调用，所以加载数据前需要先判断视图是否已初始化
			news.clear();
			pageNo = 1;
			loaDatas();
		}
	}
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//		long now = System.currentTimeMillis();
//		// 10分钟内不重复加载信息
//		if (mLastTime > 0 && now - mLastTime < 1000 * 60 * 10) {
//			return;
//		} else {
//			if (getUserVisibleHint()) {
//				// 如果直接点击跳转到本Fragment，setUserVisibleHint方法会先于
//				// onCreateView调用，所以加载数据前需要先判断视图是否已初始化
//				loaDatas();
//			}
//		}
//	}
//
//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		// TODO Auto-generated method stub
//		super.setUserVisibleHint(isVisibleToUser);
//		long now = System.currentTimeMillis();
//		// 10分钟内不重复加载信息
//		if (mLastTime > 0 && now - mLastTime < 1000 * 60 * 10) {
//			return;
//		} else {
//			if (getUserVisibleHint() && isViewInited) {
//				// 如果直接点击跳转到本Fragment，setUserVisibleHint方法会先于
//				// onCreateView调用，所以加载数据前需要先判断视图是否已初始化
//				loaDatas();
//
//			}
//		}
//	}

	private void inuiView() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		newsListAdapter = new AskAllListAdapter(getActivity(), news);
		mNewsFooter = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
		mNewsMore = (TextView) mNewsFooter.findViewById(R.id.listview_foot_more);
		mNewsProgress = (ProgressBar) mNewsFooter.findViewById(R.id.listview_foot_progress);
		news_list.addFooterView(mNewsFooter);
		news_list.setAdapter(newsListAdapter);
		news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mNewsFooter)
					return;

				AskallInfos file = (AskallInfos) newsListAdapter.getItem(position);
        		Intent intent = new Intent(getActivity(), AskExpertReplyActivity.class);
        		intent.putExtra("id", file.getId());
        		startActivity(intent);
				//UIHelper.showHtml(getActivity(), file.getContents(), file.getTitle());
			}
		});
		// 滚动加载
		news_list.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				news_list.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (news.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mNewsFooter) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(news_list.getTag());
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					news_list.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					mNewsMore.setText(R.string.load_ing);
					mNewsProgress.setVisibility(View.VISIBLE);
					// 当前pageNo+1

				
						pageNo++;
						loadNewsDatas(mNewsHandler, pageNo, UIHelper.LISTVIEW_ACTION_SCROLL);
					

				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				news_list.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		news_list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			// 下拉列表刷新
			@Override
			public void onRefresh() {
				loadNewsDatas(mNewsHandler, 1, UIHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
		isViewInited = true;
	}

	private void loaDatas() {
		// TODO Auto-generated method stub
		mNewsHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what > 0) {
					Askalls list = (Askalls) msg.obj;
					if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
						news.clear();
						news.addAll(list.getFileList());
					} else {
						List<AskallInfos> iList = new ArrayList<AskallInfos>(list.getFileList());
						iList.removeAll(news);
						news.addAll(iList);
					}
					if (msg.what < pageSize) {
						news_list.setTag(UIHelper.LISTVIEW_DATA_FULL);
						newsListAdapter.notifyDataSetChanged();
						mNewsMore.setText(R.string.load_full);
					} else {
						news_list.setTag(UIHelper.LISTVIEW_DATA_MORE);
						newsListAdapter.notifyDataSetChanged();
						mNewsMore.setText(R.string.load_more);
					}

				} else if (msg.what == -1 && msg.obj != null) {
					news_list.setTag(UIHelper.LISTVIEW_DATA_MORE);
					mNewsMore.setText(R.string.load_error);

				}
				if (newsListAdapter.getCount() == 0) {
					news_list.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					mNewsMore.setText(R.string.load_empty);
				}
				mNewsProgress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					news_list.onRefreshComplete(DateUtils.format(new Date(),
							getString(R.string.pull_to_refresh_update_pattern)));
					news_list.setSelection(0);
				}
			}
		};
		this.loadNewsDatas(mNewsHandler, 1, UIHelper.LISTVIEW_ACTION_INIT);
	}

	private void loadNewsDatas(final Handler handler, final int pageNo, final int action) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					Askalls list = Askalls.parse(AppClient.httpgetAsk(URLs.ASKEXPERTS_URL + "sessionId="
							+ Sscion.getSsid() + "&" + "pageno=" + pageNo));
					msg.what = list.getFileList().size();
					msg.obj = list;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				mLastTime = System.currentTimeMillis();
				msg.arg1 = action;// 告知handler当前action
				handler.sendMessage(msg);
			}
		}.start();
	}
}