package com.customview.ndk;

public class jni {

    static {

        System.loadLibrary("Hello");
    }

    public native String sayHello();

}
