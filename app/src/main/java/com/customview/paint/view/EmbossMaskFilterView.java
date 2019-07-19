package com.customview.paint.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.customview.R;

/**
 * Created by 简言 on 2019/2/26  20:07.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.paint.view
 * Description :
 */
public class EmbossMaskFilterView extends View {

    public EmbossMaskFilterView(Context context) {
        super(context);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 参数依次是：
//    direction：浮点型数组，用于控制x,y,z轴的光源方向
//    ambient：设置环境光亮度，0到1之间
//    specular：镜面反射系数
//    blurRadius：模糊半径


//    在使用MaskFilter的时候要注意，当我们的targetSdkVersion >= 14的时候，MaskFilter
// 就不会起效果了，这是因为Android在API 14以上版本都是默认开启硬件加速的，这样充分 利用GPU的特性，
// 使得绘画更加平滑，但是会多消耗一些内存！好吧，我们把硬件加速关了 就好，可以在不同级别下打开或者关闭硬件加速，一般是关闭~

//    Application：在配置文件的application节点添加： android:hardwareAccelerated="true"
//    Activity：在配置文件的activity节点添加 android:hardwareAccelerated="false"
//    View：可以获得View对象后调用，或者直接在View的onDraw()方法里设置： view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    @Override
    protected void onDraw(Canvas canvas) {

        float[] direction = new float[]{ 1, 1, 3 };   // 设置光源的方向
        float light = 0.4f;     //设置环境光亮度
        float specular = 8;     // 定义镜面反射系数
        float blur = 3.0f;      //模糊半径
        EmbossMaskFilter emboss=new EmbossMaskFilter(direction,light,specular,blur);

        Paint paint = new Paint();
        paint.setAntiAlias(true);          //抗锯齿
        paint.setColor(Color.BLUE);//画笔颜色
        paint.setStyle(Paint.Style.FILL);  //画笔风格
        paint.setTextSize(70);             //绘制文字大小，单位px
        paint.setStrokeWidth(8);           //画笔粗细
        paint.setMaskFilter(emboss);

        //paint.setMaskFilter(emboss);
        canvas.drawText("最喜欢看曹神日狗了~", 50, 100, paint);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lyfei34);
        canvas.drawBitmap(bitmap, 150, 200, paint);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
    }

}
