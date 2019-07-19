package com.customview.jsHtml.splashActivity.musicsplash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.customview.R;
import com.customview.jsHtml.splashActivity.musicsplash.MusicSplashActivity;
import com.customview.jsHtml.splashActivity.webviewsplash.WebViewActivity;

/**
 * Created by 简言 on 2019/2/19  13:13.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.jsHtml.splashActivity
 * Description :
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    Intent intent;
    public void musicSplashClick(View view) {

        intent = new Intent(this, MusicSplashActivity.class);
        startActivity(intent);
    }

    public void webClick(View view) {

        intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }
}
