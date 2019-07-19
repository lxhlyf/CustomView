package com.customview.staggrLayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 简言 on 2019/3/2  8:23.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.staggrLayout
 *
 * Description :  流式布局的Adapter
 */
public abstract class BaseTagAdapter  {

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);

    //用观察者模式
    public void notifyDateSetChange(){

    }
}
