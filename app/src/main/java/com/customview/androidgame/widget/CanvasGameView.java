package com.customview.androidgame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.customview.R;

/**
 * Created by 简言 on 2019/4/18  16:13.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.androidgame.widget
 * Description :
 */
public class CanvasGameView extends View {

    private Bitmap myBitmap;
    private Paint paint;

    public CanvasGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initBitmap();
    }

    private void initBitmap() {
        paint = new Paint();
        myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lyfei33);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第一部分
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(15);
        //第二三俩个参数表示，图片坐上叫在Android坐标中的位置。
        canvas.drawBitmap(myBitmap, 10, 10, paint);
        canvas.save();

        //第二部分
        Matrix m1 = new Matrix();
        m1.setTranslate(500,10); //（500， 10）
        Matrix m2 = new Matrix();
        m2.setRotate(15);  //顺时针旋转的15

        Matrix m3 = new Matrix();
        m3.setConcat(m1, m2);

        m1.setScale(0.8f,0.8f); //m1 缩小0.8倍
        m2.setConcat(m3, m1);  //1. 坐标（500， 10） 2.顺时针旋转的15  3. m1 缩小0.8倍

        canvas.drawBitmap(myBitmap, m2, paint); //将图片绘制在m2上
        canvas.restore(); //将画布重置为最近一次保存的状态
        canvas.save(); //再次将画布保存

        //第三部分
        paint.setAlpha(180);
        m1.setTranslate(200, 100); //这些值，从0的基础上开始
        m2.setScale(1.3f, 1.3f);
        m3.setConcat(m1, m2); //1.(200, 100) //2.放大1.3倍
        canvas.drawBitmap(myBitmap, m3, paint);
        paint.reset();
        canvas.restore();
        canvas.save();

        //第四部分代码, 画文本，不需要保存画布
        paint.setTextSize(40);
        paint.setColor(0xffFFFFFF);
        canvas.drawText("图片的宽度:"+myBitmap.getWidth(), 20, 220, paint);
        canvas.drawText("图片的高度:"+myBitmap.getHeight(), 150, 220, paint);
        paint.reset();
    }
}
