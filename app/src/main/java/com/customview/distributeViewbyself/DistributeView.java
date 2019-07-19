package com.customview.distributeViewbyself;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.customview.R;

/**
 * Created by 简言 on 2018/10/21.
 * 努力吧 ！ 少年 ！
 */

public class DistributeView extends View{

    private int age;
    private String name;
    private Bitmap bg;

    public DistributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //去属性有三种方式：
        //1.用命名空间去取。  xmlns:atguigu="http://schemas.android.com/tools"
       /* String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");*/
        //System.out.println("age="+age+",name="+name+",bg="+bg);
        //2.遍历我们的属性集合
        for (int i=0; i<attrs.getAttributeCount(); i++){
            System.out.println(attrs.getAttributeName(i)+"==="+attrs.getAttributeValue(i));
        }
        //3.使用系统工具，获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DistributeView);
        for (int i=0; i<typedArray.getIndexCount(); i++){
           int index =  typedArray.getIndex(i);
           switch(index){
               case R.styleable.DistributeView_my_age:
                   age = typedArray.getInt(index, 0);
                   break;
               case R.styleable.DistributeView_my_name:
                   name = typedArray.getString(index);
                   break;
               case R.styleable.DistributeView_my_bg:
                   //只有发生过向上转型，才能进行向下转型
                   Drawable drawable = typedArray.getDrawable(index);
                   BitmapDrawable drawable1 = (BitmapDrawable) drawable;
                   bg = drawable1.getBitmap();
                   break;
           }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawText(name+"----------"+age,50,50, paint);
        //距离左边50，上边50。
        canvas.drawBitmap(bg, 50, 50, paint);
    }
}
