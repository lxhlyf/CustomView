package com.customview.jsHtml.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static long mPreTime = 0;

    private BaseActivity mCurrentActivity;
    public static List<BaseActivity> mActivities = new LinkedList<>();

    private PermissionListener mPermissionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //设置布局文件
        setContentView(provideContentViewId());

        //设置滑动菜单
        if (enableSlideOpen()) {

            initSlidingMenu();
        }

        //注册IOC 或者 ButterKnife
        ButterKnife.bind(this);

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            //应用一打开，就将BaseActivity添加到Activity的集合中
            mActivities.add(this);
        }


        initTitle();
        initView();
        initData();
        initListener();
    }


    private void initSlidingMenu() {

    }

    public boolean enableSlideOpen() {
        return false;
    }


    /**
     * 提供布局文件 的 Id
     *
     * @return
     */
    public abstract int provideContentViewId();


    public void initTitle() {
    }

    protected abstract void initView();

    public void initData() {

    }

    public void initListener() {
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    /**
     * 得到当前正在显示得Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 申请运行时权限
     *
     * @param permissions        需要申请的权限
     * @param permissionListener 权限申请结果的回调接口
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //如果请求的权限没有被同意，就添加到权限集合中
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            permissionListener.onGranted();
        }
    }

    //用于请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //定义一个存放被拒绝了的权限的集合
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

}
