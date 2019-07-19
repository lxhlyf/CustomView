package com.customview.FireWork;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.customview.FireWork.fireview.MyView;
import com.customview.R;

/**
 * Created by 简言 on 2019/2/22  11:01.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.FireWork
 * Description :
 */
public class FireWorkActivity extends AppCompatActivity {

    /** Called when the activity is first created. */

    // EventListener mListener = new EventListener();

    static final String LOG_TAG = FireWorkActivity.class.getSimpleName();
    static int SCREEN_W = 480;// 当前窗口的大小
    static int SCREEN_H = 800;

    MyView fireworkView;

    // get the current looper (from your Activity UI thread for instance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}

        fireworkView = new MyView(this, this);
        setContentView(fireworkView);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (fireworkView.isRunning()) {
            fireworkView.setRunning(false);
        }
    }
}
