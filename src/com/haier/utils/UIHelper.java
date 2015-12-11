package com.haier.utils;


import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.haier.R;
import com.haier.activity.BrowserActivity;
import com.haier.activity.MainActivity;
import com.haier.app.AppManager;
import com.haier.bean.INews;


/**
 * Ӧ�ó���UI���߰��װUI��ص�һЩ����
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UIHelper {

	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;
	
	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	
	public final static int LISTVIEW_DATATYPE_NEWS = 0x01;
	public final static int LISTVIEW_DATATYPE_BLOG = 0x02;
	public final static int LISTVIEW_DATATYPE_POST = 0x03;
	public final static int LISTVIEW_DATATYPE_TWEET = 0x04;
	public final static int LISTVIEW_DATATYPE_ACTIVE = 0x05;
	public final static int LISTVIEW_DATATYPE_MESSAGE = 0x06;
	public final static int LISTVIEW_DATATYPE_COMMENT = 0x07;
	
	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;
	
	public final static Bitmap BITMAP=null;
	/** ȫ��web��ʽ */
	public final static String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} " +
			"img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} " +
			"pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} " +
			"a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";
	
	/**
	 * 打开文件
	 * @param file
	 */
	public static void openFile(Activity activity, File file){
	    //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    //设置intent的Action属性
	    intent.setAction(Intent.ACTION_VIEW);
	    //获取文件file的MIME类型
	    String type = FileUtils.getMIMEType(file);
	    //设置intent的data和Type属性。
	    intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
	    try {
	    	//跳转
		    activity.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			ToastMessage(activity, "您的手机上未安装能打开此类文件的软件，请安装后重试！");
		}
	}
	
	/**
	 * �ж��Ƿ��Ѱ�װpackageNameӦ�ó���
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isAvilible(Context context, String packageName) {
		PackageInfo packageInfo;
		try {
		    packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
		    packageInfo = null;
		    e.printStackTrace();
		}
		return packageInfo != null;
	}	
	/**
	 * ���ļ�����
	 * @param activity
	 * @param file
	 */
	public static void shareFile(Activity activity, File file) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		share.setType("*/*");
		activity.startActivity(Intent.createChooser(share, "����"));
	}	
	/**
	 * �������
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url){
		try {
//			Uri uri = Uri.parse(url);  
//			Intent it = new Intent(Intent.ACTION_VIEW, uri);  			
//			context.startActivity(it);
			
			
			Intent intent = new Intent();
			intent.setData(Uri.parse(url));
			intent.setAction(Intent.ACTION_VIEW);
			context.startActivity(intent); //�������			
			
		} catch (Exception e) {
			e.printStackTrace();
			ToastMessage(context, "�޷���4���ҳ", 500);
		} 
	}	
	
	public static void ToastMessage(Context cont,String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	public static void ToastMessage(Context cont,int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	public static void ToastMessage(Context cont,String msg,int time) {
		Toast.makeText(cont, msg, time).show();
	}	
	
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		};
	}		
	
	public static void sendAppCrashReport(final Context cont, final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.submit_report, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822") ; 
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"qddagu@126.com"});
				i.putExtra(Intent.EXTRA_SUBJECT,"�����ÿͻ��� - ���󱨸�");
				i.putExtra(Intent.EXTRA_TEXT,crashReport);
				cont.startActivity(Intent.createChooser(i, "���ʹ��󱨸�"));
				AppManager.getAppManager().AppExit(cont, true);
			}
		});
		builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				AppManager.getAppManager().AppExit(cont, true);
			}
		});
		builder.show();
	}
	private static Boolean isExit = false; 
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
	}
	/**
	 * ��ʾ����activity
	 * @param context
	 * @param activity
	 */
	public static void showActivity(Activity context, Class<? extends Activity> activity) {
		Intent intent = new Intent(context, activity);
		context.startActivity(intent);
	}
	
	public static void showReader(Activity context,Class<? extends Activity>activity){
		
	}
	/**
	 * 显示Html内容
	 * @param activity
	 * @param title
	 * @param data
	 */
	public static void showHtml(Context activity, String content,String top_title,String title) {
		Intent intent = new Intent(activity, BrowserActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.putExtra("top_title",top_title);
		activity.startActivity(intent);
	}
	/**
	 * �ڲ��������ʾHTML����
	 * @param activity
	 * @param title
	 * @param data
	 * @param baseUrl
	 *//*
	public static void showHtml(Activity activity, String title, String data, String baseUrl) {
		String fileName = "temp.html";
		FileUtils.write(activity, fileName, data);
		showHtmlFile(activity, title, fileName, baseUrl);
	}*/
	/**
	 * �ڲ��������ʱ���HTML
	 * @param context
	 * @param title
	 * @param fileName	files�ļ����µ��ļ���
	 * @param baseUrl
	 *//*
	public static void showHtmlFile(Context context, String title, String fileName, String baseUrl) {
		Intent intent = new Intent(context, BrowserActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("file", fileName);
		intent.putExtra("baseUrl", baseUrl);
		context.startActivity(intent);
	}*/
	/**
	 * �ڲ���������URL
	 * @param context
	 * @param url
	 *//*
	public static void showUrl(Context context, String url) {
		Intent intent = new Intent(context, BrowserActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	*//**
	 * Eol�ڲ���������URL
	 * @param context
	 * @param url
	 *//*
	public static void showEolUrl(Context context, String url) {
		Intent intent = new Intent(context, EolBrowserActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}*/
}
