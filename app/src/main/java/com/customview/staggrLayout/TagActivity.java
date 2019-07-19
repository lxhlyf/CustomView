package com.customview.staggrLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 简言 on 2019/3/2  7:36.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.staggrLayout
 * Description :
 */
public class TagActivity extends AppCompatActivity {

    private String[] strs = new String[100];

    @BindView(R.id.tag_layout)
    TagLayout tagLayout;

    private BaseTagAdapter myAdapter = new BaseTagAdapter() {
        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public View getView(int position, ViewGroup parent) {
            View inflate = LayoutInflater.from(TagActivity.this).inflate(R.layout.tag_adapter_item, parent, false);
            android.widget.TextView text = inflate.findViewById(R.id.item_text);
            text.setText(strs[position]);
            return inflate;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);

        for (int i=0; i<50; i++){
            strs[i] = "刘晓慧" + i;
        }

        tagLayout.setAdapter(myAdapter);
    }


}
