package com.ttit.zhihuibeijing.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ttit.zhihuibeijing.bean.NewsCenterTabBean;

import java.util.List;

/**
 * Created by JW.S on 2020/10/4 9:37 PM.
 */
public class SwitchImageVPAdapter extends PagerAdapter {
    private List<NewsCenterTabBean.TopnewsBean> topNewsBeanList;
    private List<ImageView> imageViews;

    public SwitchImageVPAdapter(List<ImageView> imageViews, List<NewsCenterTabBean.TopnewsBean> topNewsBeanList) {
        this.imageViews = imageViews;
        this.topNewsBeanList = topNewsBeanList;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView view = imageViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
