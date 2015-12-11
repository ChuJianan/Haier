package com.haier.fragment;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.haier.R;
import com.haier.adapter.ListViewSearchAdapter;
import com.haier.app.AppException;
import com.haier.bean.KnowledgeInfo;
import com.haier.bean.Result;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.download.DownloadManager;
import com.haier.download.DownloadService;
import com.haier.utils.FileUtils;
import com.haier.utils.JsonUtils;
import com.haier.utils.MediaFile;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SearchResultFragment extends BaseFragment {
	@ViewInject(R.id.listview)
	private ListView mResultsView;
	@ViewInject(R.id.message)
	private TextView mMessageText;
	
	private ListViewSearchAdapter mSearchAdapter;
	
	private long mKnowlegdeId;
	private String mKeyword;
	
	private DownloadManager downloadManager;
	private ProgressBar mProgress;
	private TextView mProgressText;
	private AlertDialog downloadDialog;
	
	/**
	 * 获得一个带参数的检索结果Fragment
	 * @param knowledgeId
	 * @param keyword
	 * @return
	 */
	public static SearchResultFragment newInstance(long knowledgeId, String keyword) {
		SearchResultFragment fragment = new SearchResultFragment();
		Bundle args = new Bundle();
		args.putLong("knowlegdeId", knowledgeId);
		args.putString("keyword", keyword);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		downloadManager = DownloadService.getDownloadManager(getActivity());
		
		initView(); // 视图初始化
		initData(); // 数据初始化
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getActivity().setTitle("检索结果列表");
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		mMessageText.setText("正在检索...");
		mResultsView.setEmptyView(mMessageText);
		mResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				KnowledgeInfo kl = (KnowledgeInfo) mSearchAdapter.getItem(position);
				String url = URLs.HOST + kl.getPath();
				if (MediaFile.isVideoFileType(url)) {
					VideoFragment.newInstance(url).show(getFragmentManager(), "videoDialog");
				} else {
					downloadFile(url);
//					getFragmentManager().beginTransaction()
//					.replace(R.id.container, BrowserFragment.newInstance(url))
//					.addToBackStack(null)
//					.commit();
				}
			}
		});
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		Bundle args = getArguments();
		if (args == null) {
			mMessageText.setText("参数不正确，请重新检索");
			return;
		}
		mKnowlegdeId = args.getLong("knowlegdeId");
		mKeyword = args.getString("keyword");
		loadKnowledges(mKnowlegdeId, mKeyword);
	}
	
	/**
	 * 检索资料列表
	 * @param klId
	 * @param keyword
	 */
	private void loadKnowledges(long klId, String keyword) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sessionId", Sscion.getSsid());
		params.addQueryStringParameter("pageno", "1");
		params.addQueryStringParameter("knowledgeId", String.valueOf(klId));
		params.addQueryStringParameter("searchStr", keyword);
		http.send(HttpMethod.GET, URLs.URL_SEARCH_LIST, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					Result result = JsonUtils.fromArrayJson(responseInfo.result, Result.class);
					if (!result.OK()) {
						throw AppException.custom(result.message());
					} else if (result.knowledgeDBInfos.size() > 0) {	
						List<KnowledgeInfo> list = result.knowledgeDBInfos;
						mSearchAdapter = new ListViewSearchAdapter(getActivity(), list);
						mResultsView.setAdapter(mSearchAdapter);
					} else {
						mMessageText.setText("未检索到数据");
					}
				} catch (JsonSyntaxException e) {
					AppException.json(e).makeToast(getActivity());
				} catch (AppException e) {
					e.makeToast(getActivity());
				}
			}
			@Override
			public void onStart() {
				mMessageText.setText("正在检索...");
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mMessageText.setText("检索出错，请重新检索");
			}
		});
	}
	
	/**
	 * 下载文件
	 * @param url
	 */
	private void downloadFile(String url) {
        String fileName = FileUtils.getUrlFileName(url);
		String target = Environment.getExternalStorageDirectory().getAbsolutePath();
		target += "/Haier/download/" + fileName;
		final String filePath = target;
		boolean autoResume = true;
		boolean autoRename = false;
		try {
			downloadManager.addNewDownload(url, fileName, target, autoResume, autoRename,
				new RequestCallBack<File>() {
					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						downloadDialog.dismiss();
						UIHelper.openFile(getActivity(), arg0.result);
					}
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						downloadDialog.dismiss();
						if (new File(filePath).exists()) {
							UIHelper.openFile(getActivity(), new File(filePath));
						} else {
							UIHelper.ToastMessage(getActivity(), "文件下载出错，请重新下载");
						}
					}
					public void onLoading(long total, long current, boolean isUploading) {
						//显示文件大小格式：2个小数点显示
				    	DecimalFormat df = new DecimalFormat("0.00");
				    	//进度条下面显示的总文件大小
				    	String fileSize = df.format((float) total / 1024 / 1024) + "MB";
				    	//进度条下面显示的当前下载文件大小
				    	String tmpFileSize = df.format((float) current / 1024 / 1024) + "MB";
			    		//当前进度值
			    	    int progress = (int)(((float)current / total) * 100);
			    	    mProgress.setProgress(progress);
						mProgressText.setText(tmpFileSize + "/" + fileSize);
					};
					public void onStart() {
						showDownloadDialog();
					};
				});
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("正在下载文件");

		final LayoutInflater inflater = LayoutInflater.from(getActivity());
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

		builder.setView(v);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					downloadManager.stopAllDownload();
				} catch (DbException e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				try {
					downloadManager.stopAllDownload();
				} catch (DbException e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();
	}
}
