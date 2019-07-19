package com.customview.lettercontacts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.customview.utils.DensityUtil;


/**
 * Created by 简言 on 2018/10/23.
 * 努力吧 ！ 少年 ！
 *
 * 绘制快速索引的字母，
 *  1. 把26个字母，放入数组中
 *  2. 在OnMeasure中计算每条的高(itemHeight)和宽(itemWidth),
 *  3. 在onDraw 中计算wordWidth,wordHeight,wordX,wordY。
 *
 * 手指按下改变文字颜色
 * 1.重写onTouchEvent,返回true,在down/move的过程中计算
 * 2.int touchIndex = Y / itemHeight ,强制绘制
 * 3.在onDraw()方法对应的下标设置画笔变色
 * 4.在up时 touchIndex= -1; 强制绘制
 */

public class IndexView extends View{

    //每一个存放字母的item的宽和高
    private int itemWidth;
    private int itemHeight;
    //画笔
    private Paint paint;

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    Handler handler = new Handler();

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setTextSize(DensityUtil.dip2px(context, 15));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //getMeasuredWidth,该方法是测量过后得到的值。准确
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight()/words.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<words.length; i++){

            if (i == touchIndex){
                paint.setColor(Color.BLUE);
            }else{
                paint.setColor(Color.BLACK);
            }

            //第一步，拿到一个字母
            String word = words[i];
            //new一个矩形
            Rect rect = new Rect();
            //第三步，
            //从word字符串或字符数组中，获取（0，1）这个范围内的字符，给它的边界画一个矩形。
            //（0，1） 包括前不包括后
            // 会自动去计算这个字符的边界，并沿着边界画一个矩形。
            paint.getTextBounds(word, 0, 1, rect);
            //第四步，拿到矩形的宽和高，就拿到了字母的宽和高
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            //第五步，计算每个字母在视图上的坐标位置,就是矩形左下角的坐标
            float wordX = itemWidth/2 - wordWidth/2;
            float wordY = itemHeight/2 + wordHeight/2 + i*itemHeight;
            //第六步，将item绘制在布局上
            canvas.drawText(word, wordX, wordY, paint);
        }

    }

    //字母的下标位置
    private int touchIndex = -1;
    //touchIndex = -1的标识符
    public static final  int Text_Gone = -1;
    /**
     * 直接返回 super.onTouchEvent(event)，默认是只消耗Down事件的。
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float Y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int tempIndex = (int) (Y / itemHeight);
                if (tempIndex != touchIndex){
                    touchIndex = tempIndex;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchIndex != -1){
                    touchIndex = -1;
                    invalidate();
                }
                break;
        }
        if (onLetterChangeListener != null){
            if (touchIndex != -1) {
                onLetterChangeListener.letterChange(touchIndex, words[touchIndex]);
            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onLetterChangeListener.letterChange(Text_Gone, "A");
                    }
                }, 3000);
            }
        }
        return true;
    }

    //回调字母的接口
    private OnLetterChangeListener onLetterChangeListener;
    public interface OnLetterChangeListener{
        void letterChange(int position, String letter);
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener){
        this.onLetterChangeListener = onLetterChangeListener;
    }
}
