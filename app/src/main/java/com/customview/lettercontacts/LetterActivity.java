package com.customview.lettercontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.customview.R;

/**
 * Created by 简言 on 2019/2/28  13:45.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.lettercontacts
 * Description :
 */
public class LetterActivity  extends AppCompatActivity implements LetterSideBar.LetterTouchListener {
    private TextView mLetterTv;
    private LetterSideBar mLetterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        mLetterTv = (TextView) findViewById(R.id.letter_tv);
        mLetterSideBar = (LetterSideBar) findViewById(R.id.letter_side_bar);
        mLetterSideBar.setOnLetterTouchListener(this);
    }

    @Override
    public void touch(CharSequence letter,boolean isTouch) {
        if (isTouch) {
            mLetterTv.setVisibility(View.VISIBLE);
            mLetterTv.setText(letter);
        } else {
            mLetterTv.setVisibility(View.GONE);
        }
    }
}
