package com.ttit.zhihuibeijing.fragment;


import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.activity.HomeActivity;
import com.ttit.zhihuibeijing.adapter.NewsCenterTabVPAdapter;
import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.base.BaseLoadNetDataOperator;
import com.ttit.zhihuibeijing.base.NewsCenterContentTabPager;
import com.ttit.zhihuibeijing.bean.NewsCenterBean;
import com.ttit.zhihuibeijing.utils.Constant;
import com.ttit.zhihuibeijing.utils.MyToast;
import com.viewpagerindicator.TabPageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JW.S on 2020/9/26 1:17 PM.
 */
public class NewsCenterFragment extends BaseFragment implements BaseLoadNetDataOperator {
    private NewsCenterBean newsCenterBean;


    private static final String TAG = "NewsCenterTableFragment";
    private TabPageIndicator mTid;
    private ImageButton mIb;
    private ViewPager mVp;
    private List<NewsCenterContentTabPager> views;

    @Override
    public void initTitle() {
        setIbMenuDisplayState(true);
        setIbPicTypeDisplayState(false);
        setTitle("新闻中心");
    }

    @Override
    public View createContent() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.newscenter_content, (ViewGroup) getView(), false);
        mTid = view.findViewById(R.id.tabpagerindicator);
        mIb = view.findViewById(R.id.ib_next);
        mVp = view.findViewById(R.id.vp_newscenter_content);
        //点击箭头，切换到下一页
        mIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取ViewPager当前显示页面的下标
                int currentItem = mVp.getCurrentItem();

                if (currentItem != newsCenterBean.data.get(0).children.size() - 1) {
                    mVp.setCurrentItem(currentItem + 1);
                }
            }
        });
        //初始化ViewPager
        initViewPager();
        return view;
    }

    private void initViewPager() {
        //保存ViewPager显示每个条目的集合
        views = new ArrayList<>();
        //获取每个item的数据
        for(NewsCenterBean.NewsCenterNewsTabBean tabBean:newsCenterBean.data.get(0).children){
            NewsCenterContentTabPager tabPager = new NewsCenterContentTabPager(getContext());
            views.add(tabPager);
        }
        //创建适配器
        NewsCenterTabVPAdapter adapter = new NewsCenterTabVPAdapter(views, newsCenterBean.data.get(0).children);
        //设置适配器
        mVp.setAdapter(adapter);
        //让TabPagerIndicator和ViewPager进行联合
        mTid.setViewPager(mVp);

        //让新闻中心第一个子tab的轮播图开始切换
        views.get(0).startSwitch();
        //给ViewPager设置页面切换监听
        //注意：ViewPager和TabPagerIndicator配合使用，监听只能设置给TabPagerIndicator
        mTid.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                //当前的开始切换，其他的tab停止切换
                for (int i = 0; i < views.size(); i++) {
                    NewsCenterContentTabPager tabPager = views.get(i);
                    if(position == i){
                        //选中页
                        tabPager.startSwitch();
                    }else{
                        //未选中页
                        tabPager.stopSwitch();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void loadNetData() {
        final String newscenterUrl = Constant.NEWSCENTER_URL;
        OkHttpUtils.get()
                .url(newscenterUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getActivity(), "获取新闻中心数据失败!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }
                });
    }

    private void processData(String response) {
        Gson gson = new Gson();
        newsCenterBean = gson.fromJson(response, NewsCenterBean.class);
        ((HomeActivity) getActivity()).setNewsCenterSMenuList(newsCenterBean.data);
        //创建布局
        View view = createContent();
        //加载布局
        addView(view);
    }
}
