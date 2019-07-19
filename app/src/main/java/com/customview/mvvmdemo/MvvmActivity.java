package com.customview.mvvmdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.customview.R;
import com.customview.databinding.ActivityMvvmBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 简言 on 2019/3/3  16:57.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.mvvmdemo
 * Description :
 */
public class MvvmActivity extends AppCompatActivity {

    private ActivityMvvmBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将实体类和视图进行绑定
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        //final Swordsman swordsman = new Swordsman("刘晓慧", "top_level");
        //binding.setSwordsman(swordsman);
        initRecyclerView();

//        binding.lxhText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(MvvmActivity.this, "I am a girl.", Toast.LENGTH_SHORT).show();
//                swordsman.setName("刘一峰");
//            }
//        });
//
//       binding.setOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(MvvmActivity.this, "A boy long to fell in love with me.", Toast.LENGTH_SHORT).show();
//                swordsman.setLevel("low_level");
//            }
//        });

       //binding.setTime(new Date());

//       binding.lxh2Text.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//               Toast.makeText(MvvmActivity.this, "tiem: "+ binding.lxh2Text.getText(), Toast.LENGTH_SHORT).show();
//           }
//       });

    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(manager);
        SwordsmanAdapter adapter = new SwordsmanAdapter(getList());
        binding.recycler.setAdapter(adapter);
    }

    private List<Swordsman> getList() {

        List<Swordsman> list = new ArrayList<>();
        for (int i=0; i<50; i++){

            Swordsman swordsman = new Swordsman("LXH"+i, "top_level"+i);
            list.add(swordsman);
        }
        return list;
    }
}
