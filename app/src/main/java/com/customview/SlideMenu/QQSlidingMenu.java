package com.customview.SlideMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.customview.R;
import com.customview.utils.UIUtils;

/**
 * Created by 简言 on 2019/3/2  19:53.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.SlideMenu
 * Description :
 */
public class QQSlidingMenu extends HorizontalScrollView {

    private int mMenuRightMargin;

    private int mMenuWidth;

    private View mMenuView, mContentView,mShadowView;

    private GestureDetector mGestureDetector;

    private boolean mMenuIsOpen;
    private boolean intercepter;

    public QQSlidingMenu(Context context) {
        this(context, null);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);

        mMenuRightMargin = typedArray.getDimensionPixelSize(R.styleable.SlidingMenu_menuRightMargin, dip2px(getContext(), 50));

        typedArray.recycle();

        mMenuWidth = getScreenWidth(getContext()) - mMenuRightMargin;

        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(mMenuIsOpen) {
                // 打开的时候往右边快速滑动切换（关闭）
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                // 关闭的时候往左边快速滑动切换（打开）
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    /**
     * 当视图填充好的时候，设置菜单和内容区域的宽度
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //1.获取LinearLayout
        ViewGroup container = (ViewGroup) getChildAt(0);

        if (container.getChildCount() != 2){

            throw new RuntimeException("必须且只能含有俩个布局");
        }

        //2.获取菜单
        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams mMenuParams = mMenuView.getLayoutParams();
        mMenuParams.width = mMenuWidth;
        mMenuView.setLayoutParams(mMenuParams);

        //3.获取内容
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        container.removeView(mContentView);
        // 然后在外面套一层阴影，
        RelativeLayout contentContainer = new RelativeLayout(getContext());
        contentContainer.addView(mContentView);
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#55000000"));
        contentContainer.addView(mShadowView);
        // 最后在把容器放回原来的位置
        contentParams.width = getScreenWidth(getContext());
        contentContainer.setLayoutParams(contentParams);
        container.addView(contentContainer);
        mShadowView.setAlpha(0.0f);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //一进入默认菜单是关闭的
        scrollTo(mMenuWidth, 0);
    }

    /**
     *   根据滚动状态来改变透明度，或者缩放比例
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 算一个梯度值
        float scale = 1f * l / mMenuWidth;// scale 变化是 1 - 0

        // 控制阴影 0 - 1
        float alphaScale = 1 - scale;
        mShadowView.setAlpha(alphaScale);

        ViewCompat.setTranslationX(mMenuView, 0.6f * l);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        intercepter = false;

        if (mMenuIsOpen){

            Toast.makeText(UIUtils.getContext(), "进入了拦截事件", Toast.LENGTH_SHORT).show();
            int currentX = (int) ev.getX();
            if (currentX > mMenuWidth){

                closeMenu();
                intercepter = true;
                return intercepter;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        // 如果有拦截不要执行自己的 onTouch
        if (intercepter) {
            return true;
        }

        if (mGestureDetector.onTouchEvent(ev)) {
            // 快速滑动触发了下面的就不要执行了
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP){

            int scrollX = getScrollX();  //内容的做边缘到View的左边的距离

            if (Math.abs(scrollX) > mMenuWidth/2){
                //关闭菜单
                closeMenu();
            }else {
                //打开菜单
                openMenu();
            }
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu(){

        mMenuIsOpen = true;
        smoothScrollTo(0, 0);  //getScrollX = mScrollX = View内容的左边缘到View的左边缘的距离

    }

    private void closeMenu(){

        mMenuIsOpen = false;
        smoothScrollTo(mMenuWidth, 0);
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

    /**
     * Dip into pixels
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
