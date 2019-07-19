package com.customview.jsHtml.splashActivity.webviewsplash;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.customview.HomeActivity;

/**
 * 注意点：
 * 1.  $('#entry_button').on('click',function() {
 *
 *            window.android.startActivity();
 *         });
 *
 * 2.@JavascriptInterface  //一定要加上
 * 	 public void startActivity() {
 *
 * 3.myWebView.addJavascriptInterface(new JavaScriptinterface(this), "android"); //一定要对应
 */
public class JavaScriptinterface {

	Activity mActivity;

	public JavaScriptinterface(Activity mActivity) {
		this.mActivity = mActivity;
	}

	/** 与js交互时用到的方法，在js里直接调用的 */
	@JavascriptInterface
	public void startActivity() {
		Intent intent = new Intent();
		intent.setClass(mActivity, HomeActivity.class);
		mActivity.startActivity(intent);
		mActivity.finish();
	}
}
