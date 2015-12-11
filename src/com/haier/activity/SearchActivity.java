package com.haier.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.haier.R;
import com.haier.fragment.SearchFragment;
import com.haier.fragment.SearchResultFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.widget.Button;

public class SearchActivity extends BaseActivity {
	/**
	 * 检索模块
	 */
	private SearchFragment mSearchFragment;
	
	private FragmentManager mFragmentManager;
	
	@ViewInject(R.id.submit)
	private Button mSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		ViewUtils.inject(this);
		
		mFragmentManager = getSupportFragmentManager();

		mSearchFragment = new SearchFragment();
		mFragmentManager.beginTransaction()
		.replace(R.id.container, mSearchFragment)
		.commit();
		mFragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				if (mFragmentManager.getBackStackEntryCount() > 0) {
					mSubmit.setVisibility(View.INVISIBLE);
				} else {
					mSubmit.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@OnClick({ R.id.back, R.id.submit })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			if (mFragmentManager.getBackStackEntryCount() > 0) {
				mFragmentManager.popBackStack();
			} else {
				finish();
			}
			break;
		case R.id.submit:
			searchResult();
			break;
		default:
			break;
		}
	}
	
	private void searchResult() {
		long knowledgeId = mSearchFragment.getKnowledgeId();
		String keyword = mSearchFragment.getKeyword();
		Fragment fragment = SearchResultFragment.newInstance(knowledgeId, keyword);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.container, fragment)
		.addToBackStack(null)
		.commit();
	}
}
