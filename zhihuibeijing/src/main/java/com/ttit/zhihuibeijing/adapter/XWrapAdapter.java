package com.ttit.zhihuibeijing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JW.S on 2020/10/6 6:20 AM.
 */
public class XWrapAdapter extends RecyclerView.Adapter {
    private View mHeadView;                 //头布局
    private View mFootView;                 //脚布局
    private RecyclerView.Adapter mAdapter;  //正常的适配器

    public XWrapAdapter(View mHeandView, View mFootView, RecyclerView.Adapter mAdapter) {
        this.mHeadView = mHeandView;
        this.mFootView = mFootView;
        this.mAdapter = mAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == RecyclerView.INVALID_TYPE) {
            //头布局
            return new HeadViewHolder(mHeadView);
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            //脚布局
            return new FootViewHolder(mFootView);
        }
        //正常布局
        return mAdapter.onCreateViewHolder(viewGroup, viewType);
    }

    /**
     * 绑定数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        //头
        if (position == 0) {
            return;
        }
        //正常布局
        int adjposition = position - 1;
        int adapterCount = mAdapter.getItemCount();
        if (adjposition < adapterCount) {
            mAdapter.onBindViewHolder(viewHolder, adjposition);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        //position = 0视为头布局类型
        if (position == 0) {
            return RecyclerView.INVALID_TYPE;
        }
        //postion-1 < adapterCount:视为正常数据类型
        int adjPosition = position - 1;
        int adapterCount = mAdapter.getItemCount();
        if (adjPosition < adapterCount) {
            return mAdapter.getItemViewType(position);
        }
        return RecyclerView.INVALID_TYPE - 1;
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View mHeadView) {
            super(mHeadView);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View mFootView) {
            super(mFootView);
        }
    }
}
