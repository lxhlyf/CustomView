package com.customview.dispatchtouchevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DispatchActivity extends AppCompatActivity {

    @BindView(R.id.lv2)
    ListView lv2;
    @BindView(R.id.lv1)
    ListView lv1;
    @BindView(R.id.lv3)
    ListView lv3;
    private int ids[] = new int[] { R.drawable.default1, R.drawable.girl1,
            R.drawable.girl2, R.drawable.girl3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ButterKnife.bind(this);

        lv1.setAdapter(new MyAdapter1());
        lv2.setAdapter(new MyAdapter1());
        lv3.setAdapter(new MyAdapter1());
    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return 3000;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView iv = (ImageView) View.inflate(getApplicationContext(),
                    R.layout.lv_item, null);
            int resId = (int) (Math.random() * 4);
            iv.setImageResource(ids[resId]);
            return iv;
        }
    }
}
