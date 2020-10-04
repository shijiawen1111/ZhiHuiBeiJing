package com.ttit.zhihuibeijing.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ttit.zhihuibeijing.base.NewsCenterContentTabPager;
import com.ttit.zhihuibeijing.bean.NewsCenterBean;
import com.ttit.zhihuibeijing.utils.Constant;

import java.util.List;

/**
 * Created by JW.S on 2020/10/4 8:12 PM.
 */
public class NewsCenterTabVPAdapter extends PagerAdapter {
    private List<NewsCenterBean.NewsCenterNewsTabBean> tabBeanList;
    private List<NewsCenterContentTabPager> views;

    public NewsCenterTabVPAdapter(List<NewsCenterContentTabPager> views, List<NewsCenterBean.NewsCenterNewsTabBean> tabBeanList) {
        this.views = views;
        this.tabBeanList = tabBeanList;
    }

    @Override
    public int getCount() {
        return views != null ? views.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = views.get(position).view;
        container.addView(view);
        //进行数据的加载
        NewsCenterContentTabPager tabPager = views.get(position);
        // /10007/list_1.json 需要在前面进行路径的拼接
        String url = Constant.HOST + tabBeanList.get(position).url;
        tabPager.loadNetData(url);
        return tabPager.view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabBeanList.get(position).title;
    }
}
