package com.haier.activity.fragment;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haier.R;
import com.haier.activity.LoginActivity;
import com.haier.activity.Vidio2Activity;
import com.haier.bean.ClickKnowledgeId;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.db.KnowledgeDB;
import com.haier.db.OptionListDB;
import com.haier.db.QuestionsDB;
import com.haier.db.RulesDB;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SellKnowledgeFragment extends Fragment implements OnCancelListener{
	@ViewInject(R.id.knowledge6)
	private TextView knowledge6;
	@ViewInject(R.id.knowledge5)
	private TextView knowledge5;
	@ViewInject(R.id.knowledge4)
	private TextView knowledge4;
	@ViewInject(R.id.knowledge3)
	private TextView knowledge3;
	@ViewInject(R.id.knowledge2)
	private TextView knowledge2;
	@ViewInject(R.id.knowledge1)
	private TextView knowledge1;
	QuestionsDB questionsDB;
	String name;
	OptionListDB optionListDB;
	KnowledgeDB knowledgeDB;
	RulesDB rulesDB;
	String id;
	Context context;
	KnowledgeDB kDB;
	String[] name1;
	String table = "questions";
	String db = "meetreader.db";
	String flag = null;
	private CustomProgressDialog progressDialog = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.sellknowledge, container,
				false);
		ViewUtils.inject(this, newsLayout);
		iniw();
		return newsLayout;
	}

	private void iniw() {
		// TODO Auto-generated method stub
		context = getActivity();
		questionsDB = new QuestionsDB(getActivity());
		optionListDB = new OptionListDB(getActivity());
		knowledgeDB = new KnowledgeDB(getActivity());
		rulesDB = new RulesDB(getActivity());
		JsonUtils.questionsDB = questionsDB;
		JsonUtils.optionListDB = optionListDB;
		JsonUtils.rulesDB = rulesDB;
			name1 = knowledgeDB.select3();
			knowledge1.setText(name1[0]);
			knowledge2.setText(name1[1]);
			knowledge3.setText(name1[2]);
			knowledge4.setText(name1[3]);
			knowledge5.setText(name1[4]);
			knowledge6.setText(name1[5]);
		   deleteTableByDBName(db, table);
	}
	@OnClick({ R.id.knowledge6, R.id.knowledge5,
		R.id.knowledge4, R.id.knowledge3, R.id.knowledge2, R.id.knowledge1 })
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	
	case R.id.knowledge6:
		startProgressDialog();
		name = knowledge6.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		//tianzhuan();
		break;
	case R.id.knowledge5:
		startProgressDialog();
		name = knowledge5.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		//tianzhuan();
		break;
	case R.id.knowledge4:
		startProgressDialog();
		name = knowledge4.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		//tianzhuan();
		break;
	case R.id.knowledge3:
		startProgressDialog();
		name = knowledge3.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		break;
	case R.id.knowledge2:
		startProgressDialog();
		name = knowledge2.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		break;
	case R.id.knowledge1:
		startProgressDialog();
		name = knowledge1.getText().toString();
		id = knowledgeDB.select4(name);
		ClickKnowledgeId.setClickknowledgeId(id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				flag = getQuset();
				handler.sendMessage(msg);
			}
		}).start();
		break;
	}
}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			stopProgressDialog();
			tianzhuan();
		}
	};
	private String getQuset() {
		// TODO Auto-generated method stub
		String result = null;
		String sessionId = Sscion.getSsid();
		HttpGet httpRequest = new HttpGet(URLs.TIMETABLE + "knowledgeId=" + 6
				+ "&sessionId=" + sessionId);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			result = JsonUtils.Fjson(strResult);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private void tianzhuan(){
		if (flag == null) {
			UIHelper.ToastMessage(context, "�������");
			Intent intent = new Intent();
			intent.setClass(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		} else {
			if (flag.equals("6")) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), Vidio2Activity.class);
				startActivity(intent);
				getActivity().finish();
			} else {
				UIHelper.ToastMessage(context, JsonUtils.describe);
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		}
		
	}
	public SQLiteDatabase openDBByName(String DBName) {
		SQLiteDatabase db;
		db = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
		return db;
	}

	public boolean deleteTableByDBName(String DBName, String TableName) {
		SQLiteDatabase dbDatabase = openDBByName(DBName);
		dbDatabase.delete(TableName, null, null);
		close(dbDatabase);
		return false;
	}

	public void close(SQLiteDatabase db_) {
		db_.close();
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(getActivity());
			progressDialog.setMessage("正在加载中...");
		}
		progressDialog.show();
	}

	private void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}


}
