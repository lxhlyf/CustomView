package com.customview.imitateviewpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.customview.R;
import com.customview.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImitateViewPageActivity extends AppCompatActivity {

    @BindView(R.id.myViewPage)
    ImitateViewPage myViewPage;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private int[] images = {R.drawable.lyfe11, R.drawable.lyfei12, R.drawable.lyfei13, R.drawable.lyfei14, R.drawable.lyfei15, R.drawable.lyfei16, R.drawable.lyfei17, R.drawable.lyfei18,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imitate_view_page);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            myViewPage.addView(imageView);
        }

        View view = View.inflate(this, R.layout.test, null);
        myViewPage.addView(view, 2);
    }

    private void initView() {
        radioGroup.removeAllViews();
        for(int i=0; i<myViewPage.getChildCount(); i++){
            RadioButton button = new RadioButton(this);
            button.setId(i); // 0~i
            //默认选中第一个。
            button.setChecked(false);
            if ( i == 0){
                button.setChecked(true);
            }
            radioGroup.addView(button);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param group
             * @param checkedId 0~5
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPage.setScrollToPage(checkedId);
            }
        });

            myViewPage.setOnPageChangeListener(new ImitateViewPage.OnPageChangeListener() {
                @Override
                public void onPageChanged(int position) {
                    radioGroup.check(position);
                }
            });
    }


}
