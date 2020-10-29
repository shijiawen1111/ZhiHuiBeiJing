package com.ttit.zhihuibeijing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.activity.HomeActivity;
import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.bean.NewsCenterBean;
import com.ttit.zhihuibeijing.fragment.NewsCenterFragment;
import com.ttit.zhihuibeijing.utils.MyLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JW.S on 2020/9/30 7:43 PM.
 */
public class HomeMenuAdaper extends RecyclerView.Adapter {
    private static final String TAG = "HomeMenuAdaper";
    private Context context;

    private List<NewsCenterBean.NewsCenterMenuBean> newsCenterMenuBeanList;
    //默认选中的条目下标
    private int selectedPosition;

    public HomeMenuAdaper(Context context, List<NewsCenterBean.NewsCenterMenuBean> newsCenterMenuBeanList) {
        this.context = context;
        this.newsCenterMenuBeanList = newsCenterMenuBeanList;
    }

    public void setNewsCenterMenuBeanList(List<NewsCenterBean.NewsCenterMenuBean> newsCenterMenuBeanList) {
        this.newsCenterMenuBeanList = newsCenterMenuBeanList;
        //刷新显示
        notifyDataSetChanged();
        MyLogger.d(TAG, String.valueOf(newsCenterMenuBeanList.size()));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, viewGroup, false);
        ViewHolder mMenuViewHolder = new ViewHolder(view);
        return mMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final NewsCenterBean.NewsCenterMenuBean newsCenterMenuBean = newsCenterMenuBeanList.get(i);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvMenuTitle.setText(newsCenterMenuBean.title);
        //选中
        if (selectedPosition == i) {
            holder.ivArrow.setImageResource(R.drawable.menu_arr_select);
            holder.tvMenuTitle.setTextColor(Color.RED);
        } else {//未选中
            holder.ivArrow.setImageResource(R.drawable.menu_arr_normal);
            holder.tvMenuTitle.setTextColor(Color.WHITE);
        }

        //处理条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //条目点击事件
            @Override
            public void onClick(View v) {
                if (selectedPosition != i) {
                    selectedPosition = i;
                    //刷新界面
                    notifyDataSetChanged();
                    //修改对应tab页面的标题
                    BaseFragment baseFragment = ((HomeActivity) context).getCurrentTabFragment();
                    baseFragment.setTitle(newsCenterMenuBean.title);
                    if (baseFragment instanceof NewsCenterFragment){
                        NewsCenterFragment newsCenterFragment = (NewsCenterFragment) baseFragment;
                        //切换内容
                        newsCenterFragment.switchContent(i);
                    }
                }
                //关闭侧滑菜单
                ((HomeActivity) context).mSlidingMenu.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsCenterMenuBeanList != null ? newsCenterMenuBeanList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;
        @BindView(R.id.tv_menu_title)
        TextView tvMenuTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
