package com.customview.imitateviewpage;

import android.os.SystemClock;
import android.widget.Scroller;

/**
 * Created by 简言 on 2018/10/21.
 * 努力吧 ！ 少年 ！
 */

public class MyScroller {

    /***起始坐标*/
    private float startX;
    private float startY;
    /**
     * X,Y 轴方向上移动的距离
     */
    private int deltaX;
    private int deltaY;
    /**
     * 开始的时间
     */
    private long startTime;
    private boolean isFinish = false;
    /**滚动的总时间*/
    private long totalTime = 500;
    /**通过动态计算出来的每一秒钟需要滚动的距离为 currX - startX*/
    //下一秒滚动目标的X坐标
    private float currX;
    /**
     *  startScroll 通过这个方法，目标需要进行滚动的参数，添加到系统当中
     * @param startX  滚动的开始位置, X坐标
     * @param startY   滚动的开始位置， Y坐标
     * @param deltaX  X轴的偏移量
     * @param deltaY  Y轴的偏移量
     */
    public void startScroll(float startX, float startY, int deltaX, int deltaY, long totalTime) {
        this.startX = startX;
        this.startY = startY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.startTime = SystemClock.uptimeMillis(); //系统开机时间
        this.isFinish = false;
        this.totalTime = totalTime;
    }


    /**
     * 得到下一秒滚动目标的X坐标
      * @return
     */
    public float getCurrX() {
        return currX;
    }


    /**
     * 通过startScroll 传入的值，动态的计算每一秒钟的偏移量，将是否完成的结果返回。
     * true : 正在移动
     * false : 移动结束
     * @return
     */
    public boolean cuputeScrollOffset() {

        if (isFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();

        //计算从开滚动，到目前所用了的时间间隔
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            //如果时间间隔小于要求的时间间隔，滚动还没有结束
            //计算平均速度
            float voleCity = deltaX / totalTime;
            //移动一小段对应的距离
            float distanceSmallX = passTime * voleCity;
            //求移动一小段对应的坐标
            /**
             * 1s  currX = startX + voleCity
             * 2s  currX = startX + 2*voleCity
             * 3s  currX = startX + 3*voleCity
             * 对于currX就是每一秒增加 voleCity， 这样就实现了匀速滚动
             */
             currX = startX + distanceSmallX;
        } else {
            //移动结束了
            isFinish = true;
            //滚动到终点坐标位置
            currX = startX + deltaX;
        }
        return true;
    }
}
