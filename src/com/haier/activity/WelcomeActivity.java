package com.haier.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.WebView;

import com.haier.R;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 5500; // 延迟6秒
    static final String mimeType = "text/html";
	static final String encoding = "utf-8";
	WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        webView=(WebView)findViewById(R.id.web);
		webView.loadUrl("file:///android_asset/bg/bg.html");
        new Handler().postDelayed(new Runnable() {
            @Override
			public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this,
                        LoginActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                System.gc();
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	webView.destroy();
    	webView=null;
    }
}
