package com.customview.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by 枫暮晓 on 2018/10/20.
 * 作用是显示和隐藏指定的控件。
 */

public class Tools {

    public static void hideView(ViewGroup view) { //旋转180.旋转到对称面
        //1.默认顺时针旋转，默认相对于自己旋转
        hideView(view, 0);
    }

    public static void showView(ViewGroup view) {
        showView(view, 0);
    }

    public static void hideView(ViewGroup view, int startOffset) {
       /* RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth()/2, view.getHeight() );
        ra.setDuration(500);
        ra.setStartOffset(startOffset);
        ra.setFillAfter(true);
        view.startAnimation(ra);
        //设置不可以点击
        for (int i=0; i<view.getChildCount();i++){
            View child = view.getChildAt(i);
            child.setEnabled(false);
        }*/


        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void showView(ViewGroup view, int startOffset) {
      /*  RotateAnimation ra = new RotateAnimation(180, 360, view.getWidth()/2, view.getHeight() );
        ra.setDuration(500);
        ra.setStartOffset(startOffset);
        ra.setFillAfter(true);
        view.startAnimation(ra);
        //设置可以点击
        for (int i=0; i<view.getChildCount();i++){
            View child = view.getChildAt(i);
            child.setEnabled(true);
        }*/

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void hideViewBefore(ViewGroup view, Context context){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setDuration(1);
        animator.start();

        WindowManager win = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (win != null){
            win.getDefaultDisplay().getMetrics(dm);
        }
       int width = dm.widthPixels;
        int height = dm.heightPixels;

        view.setPivotX(width/2);
        view.setPivotY(height);
    }
}
