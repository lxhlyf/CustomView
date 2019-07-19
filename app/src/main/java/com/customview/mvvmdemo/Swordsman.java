package com.customview.mvvmdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.customview.BR;

/**
 * Created by 简言 on 2019/3/3  16:55.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.mvvmdemo
 * Description :
 */
public class Swordsman extends BaseObservable {

    private String name;
    private String level;

    public Swordsman(String name, String level){
        this.name = name;
        this.level = level;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
        notifyPropertyChanged(BR.level);
    }
}


//public class Swordsman{
//
//
//    public ObservableField<String> name = new ObservableField<>();
//    public ObservableField<String> level = new ObservableField<>();
//
//    public Swordsman(String name, String level){
//        this.name.set(name);
//        this.level.set(level);
//    }
//
//    public ObservableField<String> getName() {
//        return name;
//    }
//
//    public void setName(ObservableField<String> name) {
//        this.name = name;
//    }
//
//    public ObservableField<String> getLevel() {
//        return level;
//    }
//
//    public void setLevel(ObservableField<String> level) {
//        this.level = level;
//    }
//}
