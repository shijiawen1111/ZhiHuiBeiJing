package com.ttit.zhihuibeijing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.bean.NewsCenterGroupImageViewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JW.S on 2020/10/8 6:58 PM.
 */
public class NewsCenterGroupImageViewAdapter extends RecyclerView.Adapter {
    //保存组图数据
    private List<NewsCenterGroupImageViewBean.NewsCenterGroupImageViewNewsBean> newsCenterGroupImageViewNewsBeans;
    private Context context;

    public NewsCenterGroupImageViewAdapter(List<NewsCenterGroupImageViewBean.NewsCenterGroupImageViewNewsBean> newsCenterGroupImageViewNewsBeans, Context context) {
        this.newsCenterGroupImageViewNewsBeans = newsCenterGroupImageViewNewsBeans;
        this.context = context;
    }

    //创建组图中条目布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newscenter_group_imageview, viewGroup, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NewsCenterGroupImageViewBean.NewsCenterGroupImageViewNewsBean newsCenterGroupImageViewNewsBean = newsCenterGroupImageViewNewsBeans.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        //Picasso主要采用的是图片三级缓存技术来实现
        Picasso.with(context).load(newsCenterGroupImageViewNewsBean.listimage).into(holder.iv);
        holder.tvTitle.setText(newsCenterGroupImageViewNewsBean.title);
    }

    @Override
    public int getItemCount() {
        return newsCenterGroupImageViewNewsBeans != null ? newsCenterGroupImageViewNewsBeans.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
