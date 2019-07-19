package com.customview.lettercontacts;

import com.customview.utils.PinYinUtils;

/**
 * Created by 简言 on 2018/10/23.
 * 努力吧 ！ 少年 ！
 */

public class Person {

    private String name;
    private String pinyin;

    public Person(String name) {
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
