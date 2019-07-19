package com.customview.camera.cainiao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.customview.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by 简言 on 2019/2/20  8:49.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.camera
 * Description :
 */
public class CaiNiaoCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Button caiCameraBtn;
    private ImageView imageView;
    private Button caiCameraSaveBtn;

    File currentImageFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cainiao_camera);

        caiCameraBtn = findViewById(R.id.btn_cainiao_camera1);
        imageView = findViewById(R.id.image_cainiao);
        caiCameraSaveBtn = findViewById(R.id.btn_cainiao_camera2);

        caiCameraBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_cainiao_camera1:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_cainiao_camera2:

                //定义一个保存图片的File变量
                File dir = new File(Environment.getExternalStorageDirectory(), "pictures");
                if (dir.exists()) {
                    dir.mkdirs();
                }
                currentImageFile = new File(dir, System.currentTimeMillis() + ".jpg");
                if (!currentImageFile.exists()) {
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(intent1, 1);
                break;
        }

    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
        } else if (requestCode == 1) {
            imageView.setImageURI(Uri.fromFile(currentImageFile));
        }
    }
}
