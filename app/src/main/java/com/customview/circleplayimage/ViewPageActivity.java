package com.customview.circleplayimage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.customview.R;
import com.customview.utils.DensityUtil;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.customview.R.drawable.abc_cab_background_internal_bg;
import static com.customview.R.drawable.pointer_gray_orange;

public class ViewPageActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pointer_ly)
    LinearLayout pointerLy;
    private List<Integer> images = new ArrayList<>();
    MyPagerAdapter adapter;
    private final int FLAG = 101;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FLAG:
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }


    private void initData() {
        images.add(R.drawable.lyfe11);
        images.add(R.drawable.lyfei12);
        images.add(R.drawable.lyfei13);
        images.add(R.drawable.lyfei14);
        images.add(R.drawable.lyfei15);
        images.add(R.drawable.lyfei16);
        images.add(R.drawable.lyfei17);
        images.add(R.drawable.lyfei18);
    }

    private void initView() {

        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        //使 item总是 images.size的倍数，是为了默认选中第一个
        int item = Integer.MAX_VALUE/2  -  Integer.MAX_VALUE/2 % images.size();
        viewPager.setCurrentItem(item);
        creatIconPointer();

        mHandler.sendEmptyMessageDelayed(FLAG, 2000);
    }

    private void creatIconPointer() {
        //第一步，先清空布局
        pointerLy.removeAllViews();
        for (int i=0; i<images.size(); i++){
            //第二步，实例化一个ImageView对象
            ImageView imageView = new ImageView(this);
            //第三步，设置布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this,5));
            //第三步， 除第一个，全都marginleft = 8px
            if (i != 0){
                params.leftMargin = DensityUtil.dip2px(this, 7);
            }
            //第四步，设置布局参数
            imageView.setLayoutParams(params);
            //第五步，设置图片
            imageView.setImageResource(pointer_gray_orange);
            //全部置为不选中
            imageView.setEnabled(false);
            //默认选中第一个
            if(i == 0){
                imageView.setEnabled(true);
            }
            //将ImgeView加到布局当中
            pointerLy.addView(imageView);
        }
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面滚动了的时候回调这个方法
             * @param position 当前页面的位置
             * @param positionOffset 滑动页面的百分比
             * @param positionOffsetPixels 在屏幕上滑动的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当某个页面被选中的时候回调这个方法
             * @param position
             */
            @Override
            public void onPageSelected(int position) {

                senPointerChangeListener(position % images.size());

                Log.i("onPageSelected","onPageSelected");

                if (mHandler.hasMessages(FLAG)){
                    mHandler.removeMessages(FLAG);
                }
                mHandler.sendEmptyMessageDelayed(FLAG, 3000);
            }

            //当状态改变时调用一次
            @Override
            public void onPageScrollStateChanged(int state) {
                //当手指按住时，停止自动轮播
                if (state == ViewPager.SCROLL_STATE_DRAGGING && mHandler.hasMessages(FLAG)){
                    mHandler.removeMessages(FLAG);
                }
            }
        });
    }

    private void senPointerChangeListener(int position) {
        for (int i=0; i<pointerLy.getChildCount(); i++){
            pointerLy.getChildAt(i).setEnabled(false);
            if (position == i){
                pointerLy.getChildAt(i).setEnabled(true);
            }
        }
    }


    class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**相当于getView方法，创建某一个页面*/
        @Override
        public Object instantiateItem(ViewGroup container, int args) {
            int position = args % images.size();
            ImageView imageView = new ImageView(ViewPageActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(images.get(position));
            container.addView(imageView);
            return imageView;
        }


        /**
         *  比较我们的View和Object是否是同一个实例
         * @param view 页面
         * @param object  instantiateItem 返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
          return view == object;
        }

        /**
         * 释放资源
         * @param container  viewPager
         * @param position  要释放的位置
         * @param object 要释放的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // super.destroyItem(container, position, object);
            //从viewPager中将我们创建的页面销毁掉
            container.removeView((View) object);
        }
    }

}

