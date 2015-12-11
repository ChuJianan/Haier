package com.haier.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.EquipmentAdapter;
import com.haier.app.AppClient;
import com.haier.app.AppException;
import com.haier.bean.Equip;
import com.haier.bean.Equipments;
import com.haier.bean.Music1;
import com.haier.bean.Sscion;
import com.haier.utils.UIHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EquipmentFragment extends Fragment {
	@ViewInject(R.id.gridview)
	private GridView gridView;
	@ViewInject(R.id.texttishi)
	private TextView tishiView;
	EquipmentAdapter equipmentAdapter;
	List<Equipments> list = new ArrayList<Equipments>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.equipment, container, false);
		ViewUtils.inject(this, view);
		initView();
		loadDatas();
		return view;
	}

	private void loadDatas() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				try {
					Equip equip = Equip.parse(AppClient.httpgeteq(Sscion
							.getSsid()));
					msg.what = equip.getFileList().size();
					msg.obj = equip;
					handler.sendMessage(msg);
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what > 0) {
				Equip equip = (Equip) msg.obj;
				List<Equipments> list = new ArrayList<Equipments>(
						equip.getFileList());
				EquipmentFragment.this.list.removeAll(list);
				EquipmentFragment.this.list.addAll(list);
				equipmentAdapter.notifyDataSetChanged();
				tishiView.setVisibility(View.GONE);
			} else {
				tishiView.setText("暂无装备");
				//UIHelper.ToastMessage(getActivity(), "暂无装备");
			}
		};
	};

	private void initView() {
		// TODO Auto-generated method stub
		equipmentAdapter = new EquipmentAdapter(getActivity(), list);
		gridView.setAdapter(equipmentAdapter);
	}

}