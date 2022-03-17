package com.customview.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.customview.app.MyApp;

import java.io.File;

/**
 * Created by 简言 on 2020/7/22
 * Description:
 */
public class ThumbnailUtil {

    public static Bitmap getThumbnail(String path) {
        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        File file= new File(path);
        if(file.exists()){
            try {  //content:    文件是存在的
                mmr.setDataSource(MyApp.getContext(),  Uri.fromFile(file));//设置数据源为该文件对象指定的绝对路径
                Bitmap bitmap = mmr.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (bitmap != null) {
                    return bitmap;
                } else {
                    Toast.makeText(MyApp.getContext(), "获取视频缩略图失败", Toast.LENGTH_SHORT).show();//获取视频缩略图失败，弹出消息提示框
                }
            } catch (IllegalArgumentException e) {
                Log.i("MediaMeta", "" + e);
            } finally {
                mmr.release();
            }
        }else{
            Toast.makeText(MyApp.getContext(), "文件不存在", Toast.LENGTH_SHORT).show();//文件不存在时，弹出消息提示框
        }
        return null;
    }
}
