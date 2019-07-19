package com.customview.switchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.customview.R;
import com.customview.utils.DensityUtil;

/**
 * Created by 枫暮晓 on 2018/10/21.
 */

/**
 *  //一个视图从创建到显示的过程中的主要方法。
 * 1.构造方法实例化类
 * 2.测量 measure(int, int) ---> onMeasure();
 *   如果当前是一个ViewGroup,还有义务测量孩子
 *   孩子有建议权
 * 3.指定位置layout() --> onLayout();
 *   指定控件的位置，继承自ViewGroup的时候需要重写，是抽象方法。
 * 4.绘制视图， draw() --> onDraw(canvas);
 *    根据上俩个方法，进行绘制。
 */

/**
 * 1. 画布要画在Bitmap上
 * 2.点击和滑动冲突
 */
public class MyToggleButton extends View implements View.OnClickListener {

    private Bitmap backgroundBitmaps;
    private Bitmap slidingBitmap;
    private int slidLeftMax;
    private int slideLeft = 0;
    private  float startX;
    private float lastX;
    private boolean isOpen = false;
    /**
     * true : 点击事件生效
     * false : 滑动事件生效
     */
    private boolean isClickEnable = true;
    private Paint paint;


    public MyToggleButton(Context context) {
        super(context);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        backgroundBitmaps = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slidLeftMax = backgroundBitmaps.getWidth() - slidingBitmap.getWidth();

        //点击事件
        this.setOnClickListener(this);

    }

    /**
     * 视图大小，在这里最终能够确定。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmaps.getWidth(), backgroundBitmaps.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmaps, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, paint);
    }


    @Override
    public void onClick(View v) {
        if (isClickEnable){
            isOpen = !isOpen;
            flushView();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
               lastX = startX = event.getX();
                //每次的事件再按下时，归位
                isClickEnable = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //2.计算我们的结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;
                //4.动态移动
                slideLeft = (int) (slideLeft + distanceX);
                //5.规避越界问题
                if (slideLeft <= 0){
                    slideLeft = 0;
                }
                if (slideLeft >= slidLeftMax){
                    slideLeft = slidLeftMax;
                }
                if (Math.abs(endX - lastX) >  2){
                    isClickEnable = false; //为滑动事件。
                }
                //6.刷新
                invalidate();
                //7.数据初始化
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //如果点击实际按不生效，
                if (!isClickEnable){
                    if (slideLeft > slidLeftMax/2){ //右滑slideLeft = 0  , 左滑slideLeft = slidLeftMax  所以当slideLeft>slidLeftMax/2时
                        isOpen = true;
                    }else { //左滑slideLeft<slidLeftMax/2时 和 右滑slideLeft < slidLeftMax/2
                        isOpen = false;
                    }
                    flushView();
                }
                break;
        }
        return true;
    }

    private void flushView(){
        if(isOpen){
            slideLeft = slidLeftMax;
        }else{
            slideLeft = 0;
        }
        invalidate();
    }


}
