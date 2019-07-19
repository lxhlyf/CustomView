package com.customview.SlideMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.customview.R;
import com.customview.utils.DensityUtil;
import com.customview.utils.SystemUtils;
import com.customview.utils.UIUtils;

/**
 * Created by 简言 on 2019/3/2  13:46.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.SlideMenu
 * Description :
 */
public class SlidingMenu extends HorizontalScrollView {

    private int sMenuRightMargin;

    private  int sMenuWidth;

    private View mContentView,mMenuView;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,  R.styleable.SlidingMenu);

        sMenuRightMargin = typedArray.getDimensionPixelSize(R.styleable.SlidingMenu_menuRightMargin, UIUtils.dip2px(context,50));

        typedArray.recycle();

        sMenuWidth = getScreenWidth(getContext()) - sMenuRightMargin;
    }

    /**
     * 当布局填充完毕的时候，调用这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ViewGroup container = (ViewGroup) getChildAt(0);

        if (container.getChildCount() != 2){
            throw new RuntimeException("该布局必须有俩个子View");
        }

        //设置菜单的宽度
        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams mMenuParams = mMenuView.getLayoutParams();
        mMenuParams.width = sMenuWidth;
        mMenuView.setLayoutParams(mMenuParams);

        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams mContentParams = mContentView.getLayoutParams();
        mContentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(mContentParams);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 算一个梯度值
        float scale = 1f * l / sMenuWidth;// scale 变化是 1 - 0
        // 右边的缩放: 最小是 0.7f, 最大是 1f
        float rightScale = 0.7f + 0.3f * scale;
        // 设置右边的缩放,默认是以中心点缩放
        // 设置缩放的中心点位置
        ViewCompat.setPivotX(mContentView,0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView,rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);

        // 菜单的缩放和透明度
        // 透明度是 半透明到完全透明  0.5f - 1.0f
        float leftAlpha = 0.5f + (1-scale)*0.5f;
        ViewCompat.setAlpha(mMenuView,leftAlpha);
        // 缩放 0.7f - 1.0f
        float leftScale = 0.7f + (1-scale)*0.3f;
        ViewCompat.setScaleX(mMenuView,leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);

        // 最后一个效果 退出这个按钮刚开始是在右边，安装我们目前的方式永远都是在左边
        // 设置平移，先看一个抽屉效果 //只搞下面这行
        // ViewCompat.setTranslationX(mMenuView,l);
        // 平移 l*0.7f
        ViewCompat.setTranslationX(mMenuView, 0.25f*l);
    }

    //设置刚进来的时候菜单是关闭的
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        scrollTo(sMenuWidth, 0);
    }


    /**
     *   手指抬起只能二选一 ，要么打开菜单，要么关闭菜单
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 只需要管手指抬起 ，根据我们当前滚动的距离来判断
            int currentScrollX = getScrollX();

            if (Math.abs(currentScrollX) > sMenuWidth / 2) {
                // 关闭
                closeMenu();
            } else {
                // 打开
                openMenu();
            }
            // 确保 super.onTouchEvent() 不会执行
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu(){

        scrollTo(-sMenuWidth, 0);
    }

    private void closeMenu(){

        scrollTo(sMenuWidth, 0);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
