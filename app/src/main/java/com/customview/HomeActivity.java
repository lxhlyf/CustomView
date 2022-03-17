package com.customview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.customview.FireWork.FireWorkActivity;
import com.customview.SlideMenu.SlideActivity;
import com.customview.androidgame.CanvasGameActivity;
import com.customview.bitmap.ThumbnailActivity;
import com.customview.bottomtab.simple3.TabActivity;
import com.customview.lettercontacts.LetterActivity;
import com.customview.loadingView.LoadingViewActivity;
import com.customview.camera.cainiao.CaiNiaoCameraActivity;
import com.customview.camera.neihan.TestImageActivity;
import com.customview.camera.selecthead.CameraActivity;
import com.customview.camera.cainiao.DefinedCameraActivity;
import com.customview.canvas.CanvasAPIThreeActivity;
import com.customview.circleplayimage.ViewPageActivity;
import com.customview.colortracktab.ViewPagerTabActivity;
import com.customview.lettercontacts.ContactsActivity;
import com.customview.contentprovider.ContentProviderActivity;
import com.customview.defineTextView.TextViewActivity;
import com.customview.dispatchtouchevent.DispatchActivity;
import com.customview.distributeViewbyself.DistributeActivity;
import com.customview.imitateviewpage.ImitateViewPageActivity;
import com.customview.jsHtml.activity.HtmlAndJsActivity;
import com.customview.jsHtml.splashActivity.musicsplash.SplashActivity;
import com.customview.musicplayer.localaudio.LocalAudioActivity;
import com.customview.mvvmdemo.MvvmActivity;
import com.customview.ndk.NdkActivity;
import com.customview.notification.NotificationActivity;
import com.customview.paint.ColorMatrixActivity;
import com.customview.paint.ColorMatrixThreeethodActivity;
import com.customview.paint.LightColorFilterActivity;
import com.customview.paint.PaintTestActivity;
import com.customview.pedometerLikeqq.PedometerActivity;
import com.customview.ripple.otherswave.WaveActivity;
import com.customview.servicetest.ServiceActivity;
import com.customview.slideItemlistview.SlideListActivity;
import com.customview.spanner.SpannerActivity;
import com.customview.staggrLayout.TagActivity;
import com.customview.switchview.SwitchActivity;
import com.customview.youkumenu.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.menu)
    Button menu;
    @BindView(R.id.circle_image)
    Button circleImage;
    @BindView(R.id.spanner_btn)
    Button spannerBtn;
    @BindView(R.id.switch_btn)
    Button switchBtn;
    @BindView(R.id.distribute_btn)
    Button distributeBtn;
    @BindView(R.id.imitate_view_page)
    Button imitateViewPage;
    @BindView(R.id.dispatch_btn)
    Button dispatchBtn;
    @BindView(R.id.contacts_btn)
    Button contactsBtn;
    @BindView(R.id.slide_btn)
    Button slideBtn;
    @BindView(R.id.wave_others)
    Button waveOthers;
    @BindView(R.id.menu_default)
    TextView menuDefault;
    @BindView(R.id.service_btn)
    Button serviceBtn;
    @BindView(R.id.content_provider_btn)
    Button contentProviderBtn;
    @BindView(R.id.notification_btn)
    Button notificationBtn;
    @BindView(R.id.music_player_btn)
    Button musicPlayer;
    @BindView(R.id.html_js_btn)
    Button htmlJsBtn;
    @BindView(R.id.splash_btn)
    Button splashBtn;
    @BindView(R.id.btn_camera)
    Button cameraBtn;
    @BindView(R.id.cainiao_camera)
    Button caiCameraBtn;
    @BindView(R.id.defined_camera)
    Button definiedCamera;
    @BindView(R.id.neihan_camera)
    Button neiHanBtn;
    @BindView(R.id.fireWork)
    Button fireWorkBtn;
    @BindView(R.id.defineTextList)
    Button defineTextList;
    @BindView(R.id.pedometerLikeQq)
    Button pedometerLikeQq;
    @BindView(R.id.paint_btn)
    Button paintBtn;
    @BindView(R.id.tab_btn)
    Button tabBtn;
    @BindView(R.id.color_matrix_btn)
    Button colorMatrixBtn;
    @BindView(R.id.matrix_three_btn)
    Button matrixThreeBtn;
    @BindView(R.id.light_color_btn)
    Button lightColorBtn;
    @BindView(R.id.canvas_three_btn)
    Button canvasThreeBtn;
    @BindView(R.id.loading_progress_btn)
    Button loadingProgressBtn;
    @BindView(R.id.letter_side_btn)
    Button letterSideBtn;
    @BindView(R.id.stagger_btn)
    Button staggerBtn;
    @BindView(R.id.slide_menu_btn)
    Button slideMenuBtn;
    @BindView(R.id.mvvm_btn)
    Button mvvmBtn;
    @BindView(R.id.session_btn)
    Button sessionBtn;
    @BindView(R.id.ndk_btn)
    Button ndkBtn;
    @BindView(R.id.bottom_tab_btn)
    Button bottomTabBtn;
    @BindView(R.id.canvas_game_btn)
    Button canvasGameBtn;
    @BindView(R.id.thumbnail_btn)
    Button mThumbnailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        menu.setOnClickListener(this);
        circleImage.setOnClickListener(this);
        spannerBtn.setOnClickListener(this);
        switchBtn.setOnClickListener(this);
        distributeBtn.setOnClickListener(this);
        imitateViewPage.setOnClickListener(this);
        dispatchBtn.setOnClickListener(this);
        contactsBtn.setOnClickListener(this);
        slideBtn.setOnClickListener(this);
        waveOthers.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        contentProviderBtn.setOnClickListener(this);
        notificationBtn.setOnClickListener(this);
        musicPlayer.setOnClickListener(this);
        htmlJsBtn.setOnClickListener(this);
        splashBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
        caiCameraBtn.setOnClickListener(this);
        definiedCamera.setOnClickListener(this);
        neiHanBtn.setOnClickListener(this);
        fireWorkBtn.setOnClickListener(this);
        defineTextList.setOnClickListener(this);
        pedometerLikeQq.setOnClickListener(this);
        paintBtn.setOnClickListener(this);
        tabBtn.setOnClickListener(this);
        colorMatrixBtn.setOnClickListener(this);
        matrixThreeBtn.setOnClickListener(this);
        lightColorBtn.setOnClickListener(this);
        canvasThreeBtn.setOnClickListener(this);
        loadingProgressBtn.setOnClickListener(this);
        letterSideBtn.setOnClickListener(this);
        staggerBtn.setOnClickListener(this);
        slideMenuBtn.setOnClickListener(this);
        mvvmBtn.setOnClickListener(this);
        sessionBtn.setOnClickListener(this);
        ndkBtn.setOnClickListener(this);
        bottomTabBtn.setOnClickListener(this);
        canvasGameBtn.setOnClickListener(this);
        mThumbnailBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menu: //优酷菜单
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.circle_image:  //轮播图
                intent = new Intent(this, ViewPageActivity.class);
                startActivity(intent);
                break;
            case R.id.spanner_btn:  //下拉菜单
                intent = new Intent(this, SpannerActivity.class);
                startActivity(intent);
                break;
            case R.id.switch_btn:  //自定义开关
                intent = new Intent(this, SwitchActivity.class);
                startActivity(intent);
                break;
            case R.id.distribute_btn:  //自定义属性
                intent = new Intent(this, DistributeActivity.class);
                startActivity(intent);
                break;
            case R.id.imitate_view_page: //自定义 仿ViewPage
                intent = new Intent(this, ImitateViewPageActivity.class);
                startActivity(intent);
                break;
            case R.id.dispatch_btn:  //listview的 事件分发小案例
                intent = new Intent(this, DispatchActivity.class);
                startActivity(intent);
                break;
            case R.id.contacts_btn: // 自定义联系人列表
                intent = new Intent(this, ContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.slide_btn: //侧滑删除ListView 的 item
                intent = new Intent(this, SlideListActivity.class);
                startActivity(intent);
                break;
            case R.id.wave_others:  //全版水波纹
                // TODO: 2020/9/5 看一下实现
                intent = new Intent(this, WaveActivity.class);
                startActivity(intent);
                break;
            case R.id.service_btn:  // service 的 学习案例
                intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.content_provider_btn:  // 对ContentProvider 的学习
                // TODO: 2020/9/5 知识有点落伍的
                intent = new Intent(this, ContentProviderActivity.class);
                startActivity(intent);
                break;
            case R.id.notification_btn:  //对 通知栏 notification
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.music_player_btn: //音乐播放器
                intent = new Intent(this, LocalAudioActivity.class);
                startActivity(intent);
                break;
            case R.id.html_js_btn: //html and js 的加载
                intent = new Intent(this, HtmlAndJsActivity.class);
                startActivity(intent);
                break;
            case R.id.splash_btn: //引导页
                intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_camera: //
                // TODO: 2020/9/5  10以上需要 使用 filrprovider
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.cainiao_camera: //引导页
                intent = new Intent(this, CaiNiaoCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.defined_camera: //引导页
                intent = new Intent(this, DefinedCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.neihan_camera: //引导页
                intent = new Intent(this, TestImageActivity.class);
                startActivity(intent);
                break;
            case R.id.fireWork: //烟花效果
                intent = new Intent(this, FireWorkActivity.class);
                startActivity(intent);
                break;
            case R.id.defineTextList: //自定义TextView+ListView
                intent = new Intent(this, TextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.pedometerLikeQq: //仿QQ运动步数进度效果
                intent = new Intent(this, PedometerActivity.class);
                startActivity(intent);
                break;
            case R.id.paint_btn: //引导页
                intent = new Intent(this, PaintTestActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_btn: //自定义tabLayout
                intent = new Intent(this, ViewPagerTabActivity.class);
                startActivity(intent);
                break;
            case R.id.color_matrix_btn: //引导页
                intent = new Intent(this, ColorMatrixActivity.class);
                startActivity(intent);
                break;
            case R.id.matrix_three_btn: //引导页
                intent = new Intent(this, ColorMatrixThreeethodActivity.class);
                startActivity(intent);
                break;
            case R.id.light_color_btn: //引导页
                intent = new Intent(this, LightColorFilterActivity.class);
                startActivity(intent);
                break;
            case R.id.canvas_three_btn: //引导页
                intent = new Intent(this, CanvasAPIThreeActivity.class);
                startActivity(intent);
                break;
            case R.id.loading_progress_btn: //LoadingProgress
                intent = new Intent(this, LoadingViewActivity.class);
                startActivity(intent);
                break;
            case R.id.letter_side_btn: //联系人列表
                intent = new Intent(this, LetterActivity.class);
                startActivity(intent);
                break;
            case R.id.stagger_btn: //流式布局
                intent = new Intent(this, TagActivity.class);
                startActivity(intent);
                break;
            case R.id.slide_menu_btn: //测滑菜单
                intent = new Intent(this, SlideActivity.class);
                startActivity(intent);
                break;
            case R.id.mvvm_btn: // mvvm
                intent = new Intent(this, MvvmActivity.class);
                startActivity(intent);
                break;
            case R.id.session_btn: //  MediaSession
                intent = new Intent(this, com.customview.musicplayer.mediasession.LocalAudioActivity.class);
                startActivity(intent);
                break;
            case R.id.ndk_btn:
                intent = new Intent(this, NdkActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_tab_btn: //底部导航栏
                intent = new Intent(this, TabActivity.class);
                startActivity(intent);
                break;
            case R.id.canvas_game_btn:  //画布矩阵
                intent = new Intent(this, CanvasGameActivity.class);
                startActivity(intent);
                break;
            case R.id.thumbnail_btn:  //获取缩略图
                intent = new Intent(this, ThumbnailActivity.class);
                startActivity(intent);
                break;
        }
    }

    //实现双击退出应用的几种方法;
    //第一种方式
    // private long pressTime = 0;

    /*@Override
    public void onBackPressed() {
        //第一次一定能够进入
        if (System.currentTimeMillis() - pressTime > 2000) {
            Toast.makeText(this, "再按一下退出应用", Toast.LENGTH_SHORT);
            pressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }*/

    //第二种方式
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                finish();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
