
package com.haier.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haier.R;
import com.haier.adapter.NoticesPagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class INewsActivity extends FragmentActivity {

    @ViewInject(R.id.back)
    private Button imageButton;
    @ViewInject(R.id.ask)
    private Button askButton;
    @ViewInject(R.id.top_title)
    private TextView titleTextView;
    private FragmentManager mFragmentManager;
    private NoticesPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inews);
        ViewUtils.inject(this);
        mFragmentManager = getSupportFragmentManager();
        findView();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        askButton.setVisibility(View.GONE);
    }

    private void findView() {
        // TODO Auto-generated method stub
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        mPagerAdapter = new NoticesPagerAdapter(mFragmentManager, this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int arg0) {
                        for (int i = 0; i < ll.getChildCount(); i++) {
                            ll.getChildAt(i).setSelected(false);
                        }
                        int i = arg0 < 2 ? arg0 : arg0 + 1;
                        ll.getChildAt(i).setSelected(true);
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                    }
                });
        View.OnClickListener cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ll.getChildCount(); i++) {
                    ll.getChildAt(i).setSelected(false);
                }
                v.setSelected(true);
                switch (v.getId()) {
                    case R.id.jtbtn:
                    	titleTextView.setText("新闻");
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.sybbtn:
                    	titleTextView.setText("通知");
                        mViewPager.setCurrentItem(1, false);
                        break;
                }
            }
        };
        findViewById(R.id.jtbtn).setSelected(true);
        findViewById(R.id.jtbtn).setOnClickListener(cl);
        findViewById(R.id.sybbtn).setOnClickListener(cl);
    }
}
