package com.customview.youkumenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.customview.R;
import com.customview.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.channel1)
    ImageView channel1;
    @BindView(R.id.channel2)
    ImageView channel2;
    @BindView(R.id.channel3)
    ImageView channel3;
    @BindView(R.id.channel4)
    ImageView channel4;
    @BindView(R.id.channel5)
    ImageView channel5;
    @BindView(R.id.channel6)
    ImageView channel6;
    @BindView(R.id.channel7)
    ImageView channel7;
    @BindView(R.id.level3)
    RelativeLayout level3;
    @BindView(R.id.level2)
    RelativeLayout level2;
    @BindView(R.id.icon_home)
    ImageView iconHome;
    @BindView(R.id.level1)
    RelativeLayout level1;
    @BindView(R.id.icon_menu)
    ImageView iconMenu;

    private boolean isShowLevel3 = false;  //是否显示第三级菜单
    private boolean isShowLevel2 = false;  //是否显示第二级菜单
    private boolean isShowLevel1 = false;  //是否显示第一级菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
       /* Animation animation = AnimationUtils.loadAnimation(this, R.anim.hide_view);
        level3.startAnimation(animation);
        level2.startAnimation(animation);
        level1.startAnimation(animation);*/

        Tools.hideViewBefore(level1, this);
        Tools.hideViewBefore(level2, this);
        Tools.hideViewBefore(level3, this);
        iconHome.setOnClickListener(this);
        iconMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon_home:
                //如果二级菜单和三级菜单都显示
                if (isShowLevel2){
                    isShowLevel2 = false;
                    Tools.hideView(level2);
                    if ( isShowLevel3){
                        isShowLevel3 = false;
                        Tools.hideView(level3,200);
                    }
            }else {
                //如果二级菜单和三级菜单都隐藏，显示二级菜单
                isShowLevel2 = true;
                Tools.showView(level2);
            }
                break;
            case R.id.icon_menu:
              if (isShowLevel3){
                  //如果是显示状态
                  isShowLevel3 = false;
                  Tools.hideView(level3);
              }else{
                  //如果是隐藏状态
                  isShowLevel3 = true;
                  Tools.showView(level3);
              }
                break;
        }
    }

    public void showMenu(View view) {
        if (isShowLevel1){
            isShowLevel1 = false;
            Tools.hideView(level1);
            if (isShowLevel2){
                isShowLevel2 = false;
                Tools.hideView(level2,200);
                if (isShowLevel3){
                    isShowLevel3 = false;
                    Tools.hideView(level3,200);
                }
            }
        }else {
            isShowLevel1 = true;
            isShowLevel2 = true;
            Tools.showView(level1);
            Tools.showView(level2, 200);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
