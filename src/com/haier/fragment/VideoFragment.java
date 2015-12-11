package com.haier.fragment;

import java.io.File;
import java.text.DecimalFormat;

import com.haier.R;
import com.haier.download.DownloadManager;
import com.haier.download.DownloadService;
import com.haier.utils.FileUtils;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoFragment extends DialogFragment {
	@ViewInject(R.id.video)
	private VideoView mVideoView;
	@ViewInject(R.id.play)
	private ImageButton mBtnPlay;
	@ViewInject(R.id.loading)
	private ProgressBar mLoading;
	private String mUrl;

	private DownloadManager downloadManager;
	private ProgressBar mProgress;
	private TextView mProgressText;
	private AlertDialog downloadDialog;

	/**
	 * 获得一个带参数的VideoFragment
	 * 
	 * @param knowledgeId
	 * @param keyword
	 * @return
	 */
	public static VideoFragment newInstance(String url) {
		VideoFragment fragment = new VideoFragment();
		Bundle args = new Bundle();
		args.putString("url", url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container, false);
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
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mLoading.setVisibility(View.GONE);
				mBtnPlay.setVisibility(View.VISIBLE);
			}
		});
		mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mLoading.setVisibility(View.GONE);
				mBtnPlay.setVisibility(View.GONE);
				UIHelper.ToastMessage(getActivity(), "无法播放视频，请下载后使用本地播放器播放");
				return true;
			}
		});
		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mBtnPlay.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Bundle args = getArguments();
		if (args == null) {
			UIHelper.ToastMessage(getActivity(), "播放失败");
			getActivity().finish();
			return;
		}
		mUrl = args.getString("url");//"http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp";//
		// String url =
		// "http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp";
		Uri uri = Uri.parse(mUrl);
		mVideoView.setVideoURI(uri);
		
		mVideoView.requestFocus();
		//mVideoView.start();
	}

	@OnClick({ R.id.close, R.id.download, R.id.play })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.close:
			dismiss();
			break;
		case R.id.download:
			downloadVideo(mUrl);
			break;
		case R.id.play:
			mVideoView.start();
			mBtnPlay.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	/**
	 * 下载视频
	 * 
	 * @param url
	 */
	private void downloadVideo(String url) {
		String fileName = FileUtils.getUrlFileName(url);
		String target = Environment.getExternalStorageDirectory().getAbsolutePath();
		target += "/Haier/download/" + fileName;
		final String filePath = target;
		boolean autoResume = true;
		boolean autoRename = false;
		try {
			downloadManager.addNewDownload(url, fileName, target, autoResume, autoRename, new RequestCallBack<File>() {
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					downloadDialog.dismiss();
					UIHelper.ToastMessage(getActivity(), "下载完成，文件保存在：\n" + filePath, Toast.LENGTH_LONG);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					downloadDialog.dismiss();
					if (new File(filePath).exists()) {
						UIHelper.ToastMessage(getActivity(), "下载完成，文件保存在：\n" + filePath, Toast.LENGTH_LONG);
					} else {
						UIHelper.ToastMessage(getActivity(), "文件下载出错，请重新下载");
					}
				}

				public void onLoading(long total, long current, boolean isUploading) {
					// 显示文件大小格式：2个小数点显示
					DecimalFormat df = new DecimalFormat("0.00");
					// 进度条下面显示的总文件大小
					String fileSize = df.format((float) total / 1024 / 1024) + "MB";
					// 进度条下面显示的当前下载文件大小
					String tmpFileSize = df.format((float) current / 1024 / 1024) + "MB";
					// 当前进度值
					int progress = (int) (((float) current / total) * 100);
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
