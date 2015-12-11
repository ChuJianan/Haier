package com.haier.fragment;

import java.util.Collections;
import java.util.List;

import com.haier.R;
import com.haier.bean.Knowledges;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchFragment extends BaseFragment {
	/**
	 * 品牌选择框
	 */
	@ViewInject(R.id.rg_brand)
	private RadioGroup mRadioGroup;
	/**
	 * 知识点选择
	 */
	@ViewInject(R.id.spinner1)
	private Spinner mSpinner1;
	/**
	 * 知识模块选择
	 */
	@ViewInject(R.id.spinner2)
	private Spinner mSpinner2;
	
	/**
	 * 关键词
	 */
	@ViewInject(R.id.keyword)
	private EditText mKeyword;
	
	/**
	 * 知识点选择适配器
	 */
	private SpinnerAdapter mSpinner1Adapter;
	/**
	 * 知识模块选择适配器
	 */
	private SpinnerAdapter mSpinner2Adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView(); // 初始化视图
		initData(); // 初始化数据
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getActivity().setTitle("微检索");
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		mSpinner1Adapter = new SpinnerAdapter(getActivity());
		mSpinner1.setAdapter(mSpinner1Adapter);
		mSpinner2Adapter = new SpinnerAdapter(getActivity());
		mSpinner2.setAdapter(mSpinner2Adapter);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Knowledges kl = (Knowledges) mRadioGroup.findViewById(checkedId).getTag();
				DbUtils db = DbUtils.create(getActivity(), "meetreader.db");
				try {
					List<Knowledges> kls = db.findAll(Selector.from(Knowledges.class).where("parentId", "=", kl.getId()));
					mSpinner1Adapter.setData(kls);
					mSpinner1Adapter.notifyDataSetChanged();
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		});
		mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Knowledges kl = (Knowledges) mSpinner1Adapter.getItem(position);
				DbUtils db = DbUtils.create(getActivity(), "meetreader.db");
				try {
					List<Knowledges> kls = db.findAll(Selector.from(Knowledges.class).where("parentId", "=", kl.getId()));
					mSpinner2Adapter.setData(kls);
					mSpinner2Adapter.notifyDataSetChanged();
				} catch (DbException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		DbUtils db = DbUtils.create(getActivity(), "meetreader.db");
		try {
			List<Knowledges> list = db.findAll(Selector.from(Knowledges.class).where("parentId", "=", 0));
			for (Knowledges kl : list) {
				RadioButton button = new RadioButton(getActivity());
				LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				params.weight = 1;
				button.setLayoutParams(params);
				button.setGravity(Gravity.CENTER);
				button.setText(kl.getName());
				button.setTag(kl);
				mRadioGroup.addView(button);
			}
			if (mRadioGroup.getChildCount() > 0) {
				mRadioGroup.check(mRadioGroup.getChildAt(0).getId());
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前搜索关键词
	 * @return
	 */
	public String getKeyword() {
		return mKeyword.getText().toString();
	}
	/**
	 * 获取当前选择知识模块ID
	 * @return
	 */
	public long getKnowledgeId() {
		return mSpinner2.getSelectedItemId();
	}
	
	public class SpinnerAdapter extends BaseAdapter {
		private Context mContext;
		/**
		 * 数据
		 */
		private List<Knowledges> data = Collections.emptyList();
		
		public SpinnerAdapter(Context context) {
			this.mContext = context;
		}
		public SpinnerAdapter(Context context, List<Knowledges> data) {
			this.mContext = context;
			this.data = data;
		}
		
		/**
		 * 设置数据
		 * @param data
		 */
		public void setData(List<Knowledges> data) {
			this.data = data;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txt;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
				txt = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(txt);
			} else {
				txt = (TextView) convertView.getTag();
			}
			Knowledges kl = data.get(position);
			txt.setText(kl.getName());
			return convertView;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.valueOf(data.get(position).getId());
		}
	}
}
