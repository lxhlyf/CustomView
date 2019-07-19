package com.customview.jsHtml.splashActivity.webviewsplash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.customview.HomeActivity;
import com.customview.R;

public class WebViewActivity extends Activity {

	WebView myWebView;
	private int position = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//第一步， //将屏幕设置为全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//第二步，//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);

		myWebView = findViewById(R.id.myWebView);

		final String url = "file:///android_asset/websplash/index.html";
		//第四步，
		loadLocalHtml(url);
	}



	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	public void loadLocalHtml(String url){
		//开启JS支持
		WebSettings ws = myWebView.getSettings();
		ws.setJavaScriptEnabled(true);
		//获得最后一页的按钮，并实现跳转
		if (position == 1){
			//Js 调 android

			myWebView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					//重写此方法，用于捕捉页面上的跳转链接
					if ("http://start/".equals(url)){
						//在html代码中的按钮跳转地址需要同此地址一致
						Toast.makeText(getApplicationContext(), "开始体验", Toast.LENGTH_SHORT).show();
						finish();
						startActivity(new Intent(WebViewActivity.this, HomeActivity.class));

					}
					return true;
				}
			});
		}else {
			//android 调android

			myWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
		}
		myWebView.loadUrl(url);
	}
}