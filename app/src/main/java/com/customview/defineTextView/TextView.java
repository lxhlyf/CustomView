package com.customview.defineTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.customview.R;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/5/13.
 * Version 1.0
 * Description:
 */
public class TextView extends LinearLayout {

    private String mText;
    private int mTextSize = 15;  //默认传入的是sp类型
    private int mTextColor = Color.BLACK;

    private Paint mPaint;


    // 构造函数会在代码里面new的时候调用
    // TextView tv = new TextView(this);
    public TextView(Context context) {
        this(context, null);
    }

    // 在布局layout中使用(调用)
    /*<com.darren.view_day01.TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!" />*/
    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 在布局layout中使用(调用)，但是会有style
    /**
     <com.darren.view_day01.TextView
     style="@style/defualt"
     android:text="Hello World!"
     */
    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = array.getString(R.styleable.TextView_darrenText);
        mTextColor = array.getColor(R.styleable.TextView_darrenTextColor, mTextColor);
        // 15 15px 15sp
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_darrenTextSize,sp2px(mTextSize));

        // 回收
        array.recycle();

        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 设置字体的大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        //  默认给一个背景
        //setBackgroundColor(Color.TRANSPARENT);
        setWillNotDraw(false);
    }

    private int sp2px(int sp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                getResources().getDisplayMetrics());
    }


    /**
     * 自定义View的测量方法  确定 View 的宽和高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 布局的宽高都是由这个方法指定
        // 指定控件的宽高，需要测量
        // 获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 1.确定的值，这个时候不需要计算，给的多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);

        // 2.给的是wrap_content 需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            // 计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            Rect bounds = new Rect();
            // 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width() + getPaddingLeft() +getPaddingRight();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            // 计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            Rect bounds = new Rect();
            // 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }

        // 设置控件的宽高
        setMeasuredDimension(width,height);
    }

    /**
     *  确定View在布局中的位置，在这个方法中确定的位置是父View中位置。
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 绘制空间中的文本等内容， 在这里方法中确定的位置是在view内部的位置。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        /*// 画文本
        canvas.drawText();
        // 画弧
        canvas.drawArc();
        // 画圆
        canvas.drawCircle();*/
        // 画文字 text  x  y  paint
        // x 就是开始的位置   0
        // y 基线 baseLine   求？   getHeight()/2知道的   centerY


        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        //是从坐标看的，不是从view的角度看的
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
//        Log.i("fontMetrics.bottom", ""+fontMetrics.bottom);
//        Log.i("fontMetrics.top", ""+fontMetrics.top);
//        Log.i("(bottom - top)/2", ""+(fontMetrics.bottom - fontMetrics.top)/2);
//        Log.i("dy", ""+dy);
        int baseLine = getHeight()/2 + dy;
//        Log.i("baseLine", ""+baseLine);
        int x = getPaddingLeft();   //默认应该是0

        canvas.drawText(mText,x,baseLine,mPaint);
    }

    /**
     * 处理跟用户交互的，手指触摸等等
     * @param event 事件分发事件拦截
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                Log.e("TAG","手指按下");
                break;

            case MotionEvent.ACTION_MOVE:
                // 手指移动
                Log.e("TAG","手指移动");
                break;

            case MotionEvent.ACTION_UP:
                // 手指抬起
                Log.e("TAG","手指抬起");
                break;
        }

        invalidate();

        return super.onTouchEvent(event);
    }
}
