package com.customview.camera.selecthead;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.customview.R;
import com.customview.camera.selecthead.config.Configs;
import com.customview.camera.selecthead.tool.CropUtil;
import com.customview.camera.selecthead.tool.SelectHeadTools;
import com.customview.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.customview.camera.selecthead.config.Configs.SystemPicture.PHOTO_REQUEST_CUT;

/**
 * Created by 简言 on 2019/2/20  7:53.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.camera
 * Description :
 */
public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPopUp;
    private ImageView iv_show;
    private Uri photoUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnPopUp = (Button) this.findViewById(R.id.btn_popup);
        btnPopUp.setOnClickListener(this);
        iv_show = (ImageView) this.findViewById(R.id.iv_show);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_popup:
                if (!FileUtils.isSDCardAvailable()) {
                    Toast.makeText(this, "没有找到SD卡，请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    photoUri = FileUtils.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
                } catch (IOException e) {
                    Toast.makeText(this, "创建文件失败。", Toast.LENGTH_SHORT).show();
                    return;
                }
                SelectHeadTools.openDialog(this, photoUri);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                //SelectHeadTools.startPhotoZoom(this, photoUri, 600);


                    CropUtil.cropImageUri(this, photoUri, photoUri, 600,
                            600, PHOTO_REQUEST_CUT);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
//                if (data==null)
//                    return;
//                SelectHeadTools.startPhotoZoom(this, data.getData(), 600);

                //从相册选取成功后，需要从Uri中拿出图片的绝对路径，再调用剪切
                Uri newUri = Uri.parse("file:///" + CropUtil.getPath(this, data.getData()));
                if (newUri != null) {
                    CropUtil.cropImageUri(this, newUri, photoUri, 600,
                            600, PHOTO_REQUEST_CUT);
                }
                break;
            case PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data == null)
                    return;

                Bitmap bit = CropUtil.decodeUriAsBitmap(this, data.getData());
                iv_show.setImageBitmap(bit);

                File file = FileUtils.getFileByUri(this, photoUri);
                Log.d("File", file.toString());
                break;
        }
    }

}
