package com.customview.jsHtml.splashActivity.musicsplash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.customview.HomeActivity;
import com.customview.R;

import java.io.IOException;

public class MusicSplashActivity extends AppCompatActivity {

    private MyWebView myWebView;
    private MediaPlayer mediaPlayer;
    private long pressTime = 0;
    private boolean isFist = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //第一步， //将屏幕设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //第二步，//去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_music);


        myWebView = findViewById(R.id.myWebView);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        AssetFileDescriptor fileDescriptor = null;
        try {

            fileDescriptor = getAssets().openFd("musicsplash/xinji.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String url = "file:///android_asset/musicsplash/allbelonglxh.html";
        //第四步，
        loadLocalHtml(url);

    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void loadLocalHtml(String url) {

        //开启JS支持
        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);

        myWebView.addJavascriptInterface(new AndroidAndJsInterface(), "android");
        myWebView.loadUrl(url);
    }

    class AndroidAndJsInterface {

        @JavascriptInterface
        public void startActivity() {
            if (System.currentTimeMillis() - pressTime >= 2000){
                Toast.makeText(MusicSplashActivity.this, "再按一次进入主界面", Toast.LENGTH_SHORT).show();
                pressTime = System.currentTimeMillis();
            }else {
                Intent intent = new Intent(MusicSplashActivity.this, HomeActivity.class);
                MusicSplashActivity.this.startActivity(intent);
                finish();
            }

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AssetFileDescriptor fileDescriptor = null;
        try {

            fileDescriptor = getAssets().openFd("musicsplash/xinji.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
    }


    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

}
