package com.customview.mvvmdemo;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customview.R;
import com.customview.databinding.ItemMvvmRecyclerBinding;
import com.customview.jsHtml.fragment.BigImageFragment;

import java.util.List;

/**
 * Created by 简言 on 2019/3/3  20:31.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.mvvmdemo
 * Description :
 */
public class SwordsmanAdapter extends RecyclerView.Adapter<SwordsmanAdapter.SwordsmanViewHolder> {

    private List<Swordsman> mList;

    public SwordsmanAdapter(List<Swordsman> mList){

        this.mList = mList;
    }

    @NonNull
    @Override
    public SwordsmanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMvvmRecyclerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_mvvm_recycler, parent, false);
        return new SwordsmanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SwordsmanViewHolder holder, int position) {

        Swordsman swordsman = mList.get(position);
        holder.getBinding().setSwordsman(swordsman);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SwordsmanViewHolder extends RecyclerView.ViewHolder {
        private final ItemMvvmRecyclerBinding binding;

        public SwordsmanViewHolder(ItemMvvmRecyclerBinding binding) {
          super(binding.getRoot());
          this.binding = binding;
        }

        public ItemMvvmRecyclerBinding getBinding(){
            return binding;
        }
    }
}
