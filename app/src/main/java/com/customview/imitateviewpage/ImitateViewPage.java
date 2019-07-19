package com.customview.imitateviewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Printer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by 简言 on 2018/10/21.
 * 努力吧 ！ 少年 ！
 */

/**
 * 1.getChildCount()
 * 2.getChildAt(i)
 * 3.getScrollY()  起始值， 创建的时候默认的一个值
 * 4.getWidth  得到ImitateViewPage的宽度
 * 5.getScrollX() 滚动的初始值， 右view提供
 */

/**
 * 父类芳芳是空方法
 * computeScroll 和 onDraw
 */

/**
 * 自定义viewPage 的主要实现功能
 * 1.将子view绘制在自定义viewPage 通过layout确定子view的位置
 * 2.实现滑动功能
 *    2.1 方法一： 是同手势探测器 GestureDetector
 *        2.1.1 new一个GestureDetector的实例 , 并重写方法
 *        2.2.2 在onToucheEvent中传递事件 detector.onTouchEvent(event); ， 并返回true
 *    2.2 方法二： 通过触摸监听事件 onTouchEvent
 *        2.2.1 在一进入监听器，就记录触摸事件的事件位置，并在监听器末尾的位置将事件位置存入全局
 *        2.2.2 在ACTION_MOVE， 计算偏移量， 用scrollBy(); 实现滚动
 *        2.2.3 在ACTION_UP中判断页面滑动规则，利用Scroller，computeScroll,computeScrollOffset,invalidate实现平滑滚动
 *        2.2.4 利用velocityTracker 平均速度实现超过 30 开启滑动。
 */
public class ImitateViewPage extends ViewGroup  {

    private Scroller scroller;
    private VelocityTracker tracker;
    private int lastX;
    private int lastY;
    private int currentIdex = 0;

    public ImitateViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        tracker =  VelocityTracker.obtain();
    }

    private int lastIntercepterX;
    private int lastIntercepterY;
    /**
     * 如果当前方法返回true，拦截事件，将会触发当前控件的onTouchEvent()方法
     * 如果当前方法，返回false,不拦截事件，事件继续传递给孩子
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepter = false ; //默认不拦截
        int x = (int) ev.getX();
        int y = (int) ev.getY();
     switch (ev.getAction()){
         case MotionEvent.ACTION_DOWN:
             break;
         case MotionEvent.ACTION_MOVE:
             int deltaX = x - lastIntercepterX;
             int deltaY = y - lastIntercepterY;
             if (Math.abs(deltaX) - Math.abs(deltaY) > 0 && Math.abs(deltaX)>5){
                 isIntercepter = true; //在事件首次传到ViewPage时拦截
             }
             break;
         case MotionEvent.ACTION_UP:
             break;
     }
        lastIntercepterX = x;
        lastIntercepterY = y;
        lastX = x;
        lastY = y;
        return isIntercepter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);  //这是为了向父类传递事件
        int x = (int) event.getX();
        int y= (int) event.getY();
       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:
               //只有执行了ACTION_DOWN，才会跳出，到末尾赋初值，
               //所以要在拦截监听中，同步初始值
               break;
           case MotionEvent.ACTION_MOVE:
               int deltaX = x - lastX;
               /**
                * scrollBy 传入 负数---向右移动， 传入正数--->向左移动
                * 1. deltaX > 0 手指在向右滑动，期望页面向右滑动 所以应传入负值 -deltaX
                * 2. deltaX < 0 手指在向左滑动，期望页面向左滑动，所以应传入正值 -deltaX
                */
               scrollBy(-deltaX, 0);
               break;
           case MotionEvent.ACTION_UP:
               /**
                *
                */
               int offsetX = getScrollX() - currentIdex*getWidth();
               if (Math.abs(offsetX) > getWidth()/4){
                   if (offsetX > 0){ //左滑
                       currentIdex++;
                   }else{ //右滑
                       currentIdex--;
                   }
               }else{
                   tracker.computeCurrentVelocity(1000);
                   //估计是通过触摸计算得到的滑动距离 ， 和scroller没有关系
                   float vX = tracker.getXVelocity();
                   if (Math.abs(vX) > 30){
                       //切换到上一个页面
                       if (vX > 0){
                           currentIdex--;
                       }else {
                           //切换到下一个页面
                           currentIdex++;
                       }
                   }
               }
                setScrollToPage(currentIdex);
               break;
       }
        lastX = x;
        lastY = y;
        return  true;
    }

    public void setScrollToPage(int tempIndex){

        currentIdex = tempIndex<0 ? getChildCount()-1 : tempIndex>(getChildCount()-1) ? 0 : tempIndex;
       if (mOnPageChangeListener != null){
           mOnPageChangeListener.onPageChanged(currentIdex);
       }
        //调用系统的
        smoothScrollTo( currentIdex*getWidth(), 0);
        tracker.clear();
    }

    /**
     * computeScroll 会在 onDraw 方法中被调用
     * postInvalidate 会导致 onDraw 方法被调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    private void smoothScrollTo(int distanceX, int distanceY){
        scroller.startScroll(getScrollX(), getScrollY(), distanceX - getScrollX(), distanceY - getScrollY(), Math.abs(distanceX - getScrollX()));
        invalidate();
    }


    //设置监听器
    private OnPageChangeListener mOnPageChangeListener;
    public interface OnPageChangeListener{
        void onPageChanged(int position);
    }

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener){
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    /**
     * 1. ViewGroup 没有测量自己的方法，只有测量孩子的方法， measureChild
     * 2. 在measureChild中系统通过调用measure来测量子view,但这个方法不允许被用户调用。
     * 3. 用户通过重写onMeasure来对自定义的子View进行测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *
     * 1.测量的时候，会测量多次
     * 2.widthMeasureSpec 父层视图给当前视图的宽和模式
     * 3.根据自身的宽度和自身的padding值相减，求得子view可以拥有得宽度newWidth
     * 4.根据newWidth和模式求得一个新的MeasureSpec值；
     * 5.根据newWidth 和模式求得一个新的MeasureSpec值；
     *   MeasureSpec.makeMeasureSpec(newSize, newMode);
     * 6.用新得MeasureSpec来计算子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i=0; i<getChildCount(); i++){
            View chileView = getChildAt(i);
            //如果是一个view,那么就调用measure测量自己，如果是ViewGroup,就调用measureChild放法，自动去测量自己孩子的。
            chileView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //确定子View的位置
        for(int i=0; i<getChildCount(); i++){
            View child = getChildAt(i);
            /**
             * 确定每个子view左上角的坐标公式 (i*getWidth(),0)
             * 确定每个子view右下角的坐标公式 ((i+1)*getWight,getHeight)
             */
            child.layout(i*getWidth(), 0, (i+1)*getWidth(), getHeight());
        }
    }

}
