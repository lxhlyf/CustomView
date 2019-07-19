package com.customview.slideItemlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideListActivity extends AppCompatActivity {

    @BindView(R.id.slide_list_view)
    ListView slideListView;
    private List<MyBean> myBeans;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        adapter = new MyAdapter();
        slideListView.setAdapter(adapter);
    }

    private void initData() {
        myBeans = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            MyBean myBean = new MyBean();
            myBean.setName("简言" + i);
            myBeans.add(myBean);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myBeans.size();
        }

        @Override
        public MyBean getItem(int position) {
            return myBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(SlideListActivity.this).inflate(R.layout.item_slide, parent, false);
                viewHolder.nameTv = convertView.findViewById(R.id.item_content);
                viewHolder.menuTv = convertView.findViewById(R.id.item_menu);
                viewHolder.slide_layout = convertView.findViewById(R.id.slide_layout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameTv.setText(myBeans.get(position).getName());


            viewHolder.nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SlideListActivity.this, getItem(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.menuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myBeans.remove(position);
                    viewHolder.slide_layout.closeMenu();
                    notifyDataSetChanged();
                }
            });

            //SlideItemLayout slideItemLayout = (SlideItemLayout) convertView;
            viewHolder.slide_layout.setOnStateChangeListener(new MyOnStateChangeListenter());
            return convertView;
        }

        /**
         * 匿名内部类不知道，为什么不行
         */
        private SlideItemLayout slideLayout; //保持可变性
        class MyOnStateChangeListenter implements SlideItemLayout.OnStateChangeListener {

            @Override
            public void onClose(SlideItemLayout layout) {
                if(slideLayout ==layout){
                    slideLayout = null;
                }
            }

            @Override
            public void onDown(SlideItemLayout layout) {
                if(slideLayout != null && slideLayout!=layout){
                    slideLayout.closeMenu();
                }
            }

            @Override
            public void onOpen(SlideItemLayout layout) {
                slideLayout = layout;
            }
        }


        class ViewHolder {
            TextView nameTv;
            TextView menuTv;
            SlideItemLayout slide_layout;
        }
    }

}
