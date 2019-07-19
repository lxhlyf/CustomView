package com.customview.jsHtml.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public abstract class BaseFragment extends LazyLoadFragment {

    private View rootView;
    public Activity mActivity;
    public Context context;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
        this.context = getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            //ButterKnife IOC 等注入工具在这里进行注册
            unbinder = ButterKnife.bind(this, rootView);

            //创建数据库
            //初始化数据库

            initTitle(rootView);
            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    public void initTitle(View rootView) {

    }


    protected abstract int provideContentViewId();

    protected View getStateViewRoot(){
        return rootView;
    }

    /**
     * 可以在这里进行UI操作
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onFragmentVisibleChange(boolean isFragmentVisible) {
    }

    @Override
    public void onFragmentFirstVisible() {
        loadData();
    }

    protected void loadData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
