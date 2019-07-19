//
// Created by 枫暮晓 on 2019/3/25.
//
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include "com_customview_ndk_jni.h"


//com.customview.ndk.NdkActivity.sayHello

jstring Java_com_customview_ndk_NdkActivity_sayHello(
        JNIEnv* env,
        jobject jobj) {
    char* text = "Hello from C++";
    return (*env)->NewStringUTF(env, text);
}
