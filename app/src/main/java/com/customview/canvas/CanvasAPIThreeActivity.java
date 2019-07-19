package com.customview.canvas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.customview.R;
import com.customview.canvas.view.MyView;
import com.customview.canvas.view.MyView2;

/**
 * Created by 简言 on 2019/2/27  8:44.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.canvas
 * Description :
 */
public class CanvasAPIThreeActivity extends AppCompatActivity{

    private Button btn_reset;
    private Button btn_left;
    private Button btn_right;
    private Button btn_zoomin;
    private Button btn_zoomout;
    private MyView2 myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_three);
        bindViews();
    }

    private void bindViews() {
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
        btn_zoomin = (Button) findViewById(R.id.btn_zoomin);
        btn_zoomout = (Button) findViewById(R.id.btn_zoomout);
        myView = (MyView2) findViewById(R.id.myView);


//        btn_reset.setOnClickListener(this);
//        btn_left.setOnClickListener(this);
//        btn_right.setOnClickListener(this);
//        btn_zoomin.setOnClickListener(this);
//        btn_zoomout.setOnClickListener(this);

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_reset:
//                myView.setMethod(0);
//                break;
//            case R.id.btn_left:
//                myView.setMethod(1);
//                break;
//            case R.id.btn_right:
//                myView.setMethod(2);
//                break;
//            case R.id.btn_zoomin:
//                myView.setMethod(3);
//                break;
//            case R.id.btn_zoomout:
//                myView.setMethod(4);
//                break;
//        }
//    }

}
