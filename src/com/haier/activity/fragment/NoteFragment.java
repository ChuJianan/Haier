package com.haier.activity.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.haier.R;
import com.haier.activity.LoginActivity;
import com.haier.activity.MainActivity;
import com.haier.bean.Sscion;
import com.haier.bean.URLs;
import com.haier.utils.UIHelper;
import com.haier.widgets.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class NoteFragment extends Fragment implements OnClickListener {
	@ViewInject(R.id.send)
	private ImageView send;
	@ViewInject(R.id.sendedit)
	private EditText editText;
	private String noteString;
	private String urlname;
	private String sid;
	private String accessFlag;
	private CustomProgressDialog progressDialog = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.senddialog, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick({ R.id.send })
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.send:
			startProgressDialog();
			noteString = editText.getText().toString();
			if (noteString.equals("")) {
				UIHelper.ToastMessage(getActivity(), "不能为空");
			} else {
			try {
					urlname = URLEncoder.encode(noteString, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sendNote();
			}
			break;

		}
	}

	private void sendNote() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					msg.obj = doSend();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		}).start();
	}
	String result = null;
	String describe;
	protected Object doSend() throws JSONException {
		// TODO Auto-generated method stub
		sid = Sscion.getSsid();
		String title = "sessionId=" + sid + "&title=" + urlname;
		HttpGet httpRequest = new HttpGet(URLs.SEND_URL + title);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			JSONArray jsonArray = new JSONArray(strResult);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject sc = jsonArray.getJSONObject(i);
				JSONObject item = sc.getJSONObject("accessFlag");
				accessFlag = item.getString("flag");
				describe = item.getString("describe");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessFlag;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj != null) {
				if (msg.obj.equals("1")) {
					UIHelper.ToastMessage(getActivity(), "用户已在其他客户端登陆！");
					Intent intent=new Intent(getActivity(),LoginActivity.class);
					MainActivity.mainActivity.finish();
					startActivity(intent);
					getActivity().finish();
				}else {
					stopProgressDialog();
					editText.setText("");
					UIHelper.ToastMessage(getActivity(), describe);
				}
			} else {
				stopProgressDialog();
				UIHelper.ToastMessage(getActivity(), "发表失败");
			}
		}
	};
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(getActivity());
			progressDialog.setMessage("正在登录...");

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
