package com.haier.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.haier.R;
import com.haier.app.AppContext;
import com.haier.app.AppManager;

/**
 * 基础Activity类
 * @author SYZ
 * @version 1.0
 * @created 2013-11-30
 */
public class BaseActivity extends FragmentActivity {
    AppContext appContext;  // AppContext
    TextView mTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        AppManager.getAppManager().addActivity(this);
        appContext = (AppContext) getApplication();
    }
    
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        
        if (mTitle == null) {
            mTitle = (TextView) findViewById(R.id.title);
        }
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }
}
