package com.customview.spanner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.customview.R;
import com.customview.utils.DensityUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpannerActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_dwn_arrow)
    ImageView ivDwnArrow;
    private PopupWindow popupWindow;

    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanner);
        final ListView listView = new ListView(this);
        ButterKnife.bind(this);
        for (int i=0; i<20; i++){
            list.add("吴谨言"+i);
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = list.get(position);
                etInput.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        ivDwnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null){
                    //创建PopupWindow对象
                    popupWindow = new PopupWindow(SpannerActivity.this);
                    //设置宽度和高度
                    popupWindow.setWidth(etInput.getWidth());
                    popupWindow.setHeight(DensityUtil.dip2px(SpannerActivity.this, 200));

                    //设置PopupWindow的内容
                    popupWindow.setContentView(listView);
                    //设置PopupWindow获取焦点
                    popupWindow.setFocusable(true);
                }
                //设置PopupWindow的显示方式, 展开就合起，合起就展开
                popupWindow.showAsDropDown(etInput, 0,0);
            }
        });


    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
          ViewHoler viewHoler;
           if (convertView == null){
               viewHoler = new ViewHoler();
               convertView = LayoutInflater.from(SpannerActivity.this).inflate(R.layout.item_spanner, parent, false);
               viewHoler.iconIv = convertView.findViewById(R.id.icon_user);
               viewHoler.nameTv = convertView.findViewById(R.id.user);
               viewHoler.cancleIv = convertView.findViewById(R.id.cancel);
               convertView.setTag(viewHoler);
           }else {
               viewHoler = (ViewHoler) convertView.getTag();
           }

           viewHoler.nameTv.setText(list.get(position));

           viewHoler.cancleIv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(SpannerActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                   list.remove(position);
                   //要用adapter的实例调用，也可内部调用
                   notifyDataSetChanged(); //getCount getView
                   adapter.notifyDataSetChanged();
               }
           });
            return convertView;
        }

        class ViewHoler{
            ImageView iconIv;
            ImageView cancleIv;
            TextView nameTv;
        }
    }
}
