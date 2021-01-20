package com.turdroid.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.qmuiteam.qmui.recyclerView.QMUISwipeAction;
import com.qmuiteam.qmui.recyclerView.QMUISwipeViewHolder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseSwipeAdapter<T> extends RecyclerView.Adapter<QMUISwipeViewHolder> {

    protected List<T> mData;
    protected Context context;
    public List<QMUISwipeAction> actions = new ArrayList<>();
    public BaseSwipeAdapter(Context context, List<T> mData){
        this.mData = mData;
        this.context = context;
        QMUISwipeAction.ActionBuilder builder = new QMUISwipeAction.ActionBuilder()
                .textSize(QMUIDisplayHelper.sp2px(context, 14))
                .textColor(Color.WHITE)
                .paddingStartEnd(AdaptScreenUtils.pt2Px(20.0f));
        initActions(builder);
    }

    protected abstract int getLayoutResId();

    protected abstract void initActions(QMUISwipeAction.ActionBuilder builder);

    @NonNull
    @Override
    public QMUISwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(), parent, false);
        QMUISwipeViewHolder vh = new QMUISwipeViewHolder(view);
        for (QMUISwipeAction action : actions) {
            vh.addSwipeAction(action);
        }
        return vh;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(@Nullable List<T> list) {
        mData.clear();
        if(list != null){
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void remove(int pos){
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void prepend(@NonNull List<T> items){
        mData.addAll(0, items);
        notifyDataSetChanged();
    }

    public void append(@NonNull List<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }
}
