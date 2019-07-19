package com.customview.camera.providerCamera;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.customview.R;
import com.customview.utils.SystemUtils;
import com.customview.youkumenu.MainActivity;

import java.io.File;

/**
 * Created by 简言 on 2019/2/21  10:11.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.camera.providerCamera
 * Description :
 */
public class ProviderCameraActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_camera);

        imageView = findViewById(R.id.icon_iv);
    }

    public void paiZhao(View view) {

    }

    public void fromAlbun(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!SystemUtils.isGrantExternalRW(this)) return;
            //获得了SD卡权限
            openCamera();

        } else {

            openCamera();
        }

    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
//        File file = new FileStorage().createIconFile();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(ProviderCameraActivity.this, "com.bugull.cameratakedemo.fileprovider", file);//通过FileProvider创建一个content类型的Uri
//        } else {
//            imageUri = Uri.fromFile(file);
//        }
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//        }
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//        startActivityForResult(intent, REQUEST_CAPTURE);
    }
}
