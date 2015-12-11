package com.haier.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import com.haier.R;
import com.haier.activity.GameListActivity;
import com.haier.activity.KnowledgeXiaoActivity;
import com.haier.adapter.GameListAdapter;
import com.haier.bean.ClickKnowledgeId;
import com.haier.bean.GameList;
import com.haier.bean.Music1;
import com.haier.bean.Sscion;
import com.haier.bean.User;
import com.haier.db.DBHelper;
import com.haier.db.DBServer;
import com.haier.db.GameListDB;
import com.haier.db.UserDB;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GameListFragment extends Fragment implements OnClickListener {

	private List<GameList> mgameListData = new ArrayList<GameList>();
	private GameListAdapter mgameListAdapter;
	// @ViewInject(R.id.listView) private ListView gamelist;
	// @ViewInject(R.id.back)private ImageButton backButton;
	@ViewInject(R.id.sheji)
	private ImageView sheji;
	@ViewInject(R.id.feiche)
	private ImageView feiche;
	@ViewInject(R.id.xiaochu)
	private ImageView xiaochu;
	
	@ViewInject(R.id.tuozhuai)
	private ImageView tuozhuai;
	@ViewInject(R.id.feiji)
	private ImageView feiji;
	@ViewInject(R.id.lianxian)
	private ImageView lianxian;
	
	private LayoutInflater listContainer;
	Activity mActivity;
	GameListDB gameListDB;
	private DBServer db;
	private DBHelper helper;
	UserDB userDB;
	Bitmap bitmap = null;
	BitmapDrawable bd = null;
	int a = 0;
	String[] user;
	String userid;
	String username;
	String realname="";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.gamelist, container, false);
		ViewUtils.inject(this, newsLayout);
		iniw();
		return newsLayout;

	}

	private void iniw() {
		// TODO Auto-generated method stub
		bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
				R.drawable.icon3);
		bd = new BitmapDrawable(getActivity().getResources(), bitmap);
		xiaochu.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
				R.drawable.ic);
		bd = new BitmapDrawable(getActivity().getResources(), bitmap);
		sheji.setBackground(bd);
		bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
				R.drawable.icon3);
		bd = new BitmapDrawable(getActivity().getResources(), bitmap);
		feiche.setBackground(bd);
		db = new DBServer(getActivity());
		mActivity = getActivity();
		// gameListDB=new GameListDB(this);
		userDB = new UserDB(getActivity());
		// JsonUtils.gameListDB=gameListDB;
		JsonUtils.userDB = userDB;
		user = userDB.select1();
		userid = user[0];
		username = user[1];
		realname=User.getName();
	}

	protected void Component() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.example.game",
				"yunrui.game.IndexActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("realname", realname);
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}
	protected void Componenttuozhuai() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.tps.draggame",
				"com.tps.draggame.MainActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putString("realname", realname);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", "8");
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}
	protected void Componentfeiji() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.yunrui.pg",
				"com.yunrui.pg.MainActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putString("realname", realname);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}
	protected void Component4() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.example.linegame",
		"com.example.linegame.IndexActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
		}
	protected void Component2() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.yunrui.ggame",
				"yunrui.game.IndexActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putString("realname", realname);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}
	protected void Component3() {
		// TODO Auto-generated method stub
		ComponentName componentName = new ComponentName("com.yunrui.pg",
				"com.yunrui.pg.MainActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putString("realname", realname);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}
	private void Component1() {
		ComponentName componentName = new ComponentName("com.yunrui.newgame",
				"com.yunrui.newgame.IndexActivity");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("sscion", Sscion.getSsid());
		bundle.putString("userid", userid);
		bundle.putString("username", username);
		bundle.putString("realname", realname);
		bundle.putBoolean("music1", Music1.isMusic1());
		bundle.putBoolean("music2", Music1.isMusic2());
		bundle.putString("id", ClickKnowledgeId.getClickknowledgeId());
		intent.putExtras(bundle);
		intent.setComponent(componentName);
		startActivity(intent);
	}

	private boolean checkPackageManager(String pack) {
		PackageInfo packageInfo;
		try {
			packageInfo = getActivity().getPackageManager().getPackageInfo(
					pack, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			UIHelper.ToastMessage(mActivity, "应用未安装！");
			return false;
		} else {
			return true;
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(getActivity(),
					KnowledgeXiaoActivity.class);
			intent.putExtra("na", JsonUtils.na);
			startActivity(intent);
			// finish();
		}
		return true;
	}

	@OnClick({ R.id.sheji, R.id.xiaochu, R.id.feiche ,R.id.tuozhuai,R.id.feiji,R.id.lianxian})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sheji:
			if (checkPackageManager("com.yunrui.newgame")) {
				Component1();
			}
			break;
		case R.id.xiaochu:
			if (checkPackageManager("com.example.game")) {
				Component();
			}
			break;
		case R.id.lianxian:
			if (checkPackageManager("com.example.linegame")) {
				Component4();
			}
//			UIHelper.ToastMessage(getActivity(), "功能升级中...");
			break;
		case R.id.tuozhuai:
			if (checkPackageManager("com.tps.draggame")) {
				Componenttuozhuai();
			}
			break;
		case R.id.feiji:
			if (checkPackageManager("com.yunrui.pg")) {
				Componentfeiji();
			}
			break;
		case R.id.feiche:
			if (checkPackageManager("com.yunrui.ggame")) {
				Component2();
			}
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		bitmap.recycle();
		bd = (BitmapDrawable) xiaochu.getBackground();
		xiaochu.setBackgroundResource(0);
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) feiche.getBackground();
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd = (BitmapDrawable) sheji.getBackground();
		bd.setCallback(null);
		bd.getBitmap().recycle();
		System.gc();
	}
}
