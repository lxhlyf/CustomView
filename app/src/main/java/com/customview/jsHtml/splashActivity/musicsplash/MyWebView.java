package com.customview.jsHtml.splashActivity.musicsplash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by 简言 on 2018/12/17.
 * 努力吧 ！ 少年 ！
 */

public class MyWebView extends WebView implements View.OnClickListener {

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onClick(View v) {
        if (onClickListent != null){
            onClickListent.onClick(v);
        }
    }

    interface MyOnClickListent{
        void onClick(View v);
    }

    MyOnClickListent onClickListent = null;
    public void setMyOnClickListent(MyOnClickListent onClickListent){
        this.onClickListent = onClickListent;
    }
}
