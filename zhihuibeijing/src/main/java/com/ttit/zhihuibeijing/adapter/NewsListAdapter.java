package com.ttit.zhihuibeijing.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.activity.NewsDetailActivity;
import com.ttit.zhihuibeijing.bean.NewsCenterTabBean;
import com.ttit.zhihuibeijing.utils.Constant;
import com.ttit.zhihuibeijing.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JW.S on 2020/10/6 12:06 AM.
 */
public class NewsListAdapter extends RecyclerView.Adapter {
    private List<NewsCenterTabBean.NewsBean> news;
    private Context context;

    public NewsListAdapter(Context context, List<NewsCenterTabBean.NewsBean> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, viewGroup, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final NewsCenterTabBean.NewsBean newsBean = news.get(position);
        final ViewHolder mHolder = (ViewHolder) viewHolder;
        Picasso.with(context).load(newsBean.listimage).into(mHolder.ivIcon);
        mHolder.tvTitle.setText(newsBean.title);
        mHolder.tvTime.setText(newsBean.pubdate);
        String readNewsContent = SPUtils.getString(context, Constant.KEY_HAS_READ, "");
        //刷新新闻列表时，判断是否已经查看过
        if (readNewsContent.contains(newsBean.id)){
            mHolder.tvTitle.setTextColor(Color.GRAY);
        }else {
            mHolder.tvTitle.setTextColor(Color.BLACK);
        }
        //条目点击事件
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到新闻详情界面
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("url", newsBean.url);
                context.startActivity(intent);
                //存储该条新闻的唯一标识：
                String id = newsBean.id;
                //存储在哪里？Sp   File   DB（）
                String readNews = SPUtils.getString(context, Constant.KEY_HAS_READ, "");
                if (!readNews.contains(id)){
                    String value = readNews + "," + id;
                    //存储
                    SPUtils.saveString(context, Constant.KEY_HAS_READ, value);
                    //刷新界面
//                    notifyDataSetChanged();
                    mHolder.tvTitle.setTextColor(Color.GRAY);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return news != null ? news.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
