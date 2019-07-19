package com.customview.lettercontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.customview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity {

    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.iv_words)
    IndexView ivWords;
    @BindView(R.id.lv_main)
    ListView lvMain;
    private ArrayList<Person> persons = new ArrayList<>();
    private IndextAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initData();
        init();
    }

    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));
        persons.add(new Person("刘一峰"));
        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }

    private void init() {
        //ListView
        adapter = new IndextAdapter();
        lvMain.setAdapter(adapter);

        //左面侧边栏
        ivWords.setOnLetterChangeListener(new IndexView.OnLetterChangeListener() {
            @Override
            public void letterChange(int position, String letter) {
                tvWord.setVisibility(View.VISIBLE);
                tvWord.setText(letter);
                //这哥们要写在后面才实现，也就是隐藏会按顺序执行
                if (position == ivWords.Text_Gone) {
                    tvWord.setVisibility(View.GONE);
                }else{
                    for (int i=0; i<persons.size(); i++){
                        String listWord = persons.get(i).getPinyin().substring(0, 1);
                        if (listWord.equals(letter)){
                            lvMain.setSelection(i);
                            return;  //找到第一个，就立即返回
                        }
                    }
                }


            }
        });

    }

    class IndextAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Person getItem(int position) {
            return persons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ContactsActivity.this).inflate(R.layout.index_item, parent, false);
                holder.nameTv = convertView.findViewById(R.id.tv_word_list_body);
                holder.topIconTv = convertView.findViewById(R.id.tv_word_list);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /**
             * 1.集合中的元素是按排序26个字母排序之后得到的，默认先设置第一个item
             * 2.因为position总是大于0的，当position=0时显示topIcon
             * 3.当当前的首字母与上一个的首字母不想同的时候，进行下一个字母
             */
            String word = getItem(position).getPinyin().substring(0, 1); //拿到A
            if (position == 0){
                //第一个item执行这里
                holder.topIconTv.setVisibility(View.VISIBLE);
            }else{
                //从第二个item开始执行这里
                String preWord = getItem(position - 1).getPinyin().substring(0, 1);
                if (!word.equals(preWord)){
                    holder.topIconTv.setVisibility(View.VISIBLE);

                }else{
                    holder.topIconTv.setVisibility(View.GONE);
                }
            }
            holder.topIconTv.setText(word); //设置就是A
            holder.nameTv.setText(getItem(position).getName());
            return convertView;
        }

        class ViewHolder {
            TextView topIconTv;
            TextView nameTv;
        }
    }
}
