package com.customview.mvvmdemo;

import android.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 简言 on 2019/3/3  19:48.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.mvvmdemo
 * Description :
 */
public class ConverterUtil {

    @BindingConversion
    public static String converterDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
