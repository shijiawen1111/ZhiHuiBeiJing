package com.ttit.zhihuibeijing.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.adapter.HomeMenuAdaper;
import com.ttit.zhihuibeijing.adapter.HomeVPFragmentAdapter;
import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.base.BaseLoadNetDataOperator;
import com.ttit.zhihuibeijing.bean.NewsCenterBean;
import com.ttit.zhihuibeijing.fragment.GovaffairsFragment;
import com.ttit.zhihuibeijing.fragment.HomeFragment;
import com.ttit.zhihuibeijing.fragment.NewsCenterFragment;
import com.ttit.zhihuibeijing.fragment.SettingFragment;
import com.ttit.zhihuibeijing.fragment.SmartServiceFragment;
import com.ttit.zhihuibeijing.utils.MyLogger;
import com.ttit.zhihuibeijing.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_newscenter)
    RadioButton mRbNewScenter;
    @BindView(R.id.rb_smartservice)
    RadioButton mRbSmartService;
    @BindView(R.id.rb_govaffairs)
    RadioButton mRbGovaffairs;
    @BindView(R.id.rb_setting)
    RadioButton mRbSetting;
    @BindView(R.id.rg_tab)
    RadioGroup mRgTba;
    public SlidingMenu mSlidingMenu;
    private List<Fragment> fragments;

    private List<NewsCenterBean.NewsCenterMenuBean> newsCenterMenuBeanLists;
    //侧滑菜单的数据
    private HomeMenuAdaper menuAdapter;

    public void setNewsCenterSMenuList(List<NewsCenterBean.NewsCenterMenuBean> newsCenterMenuBeanList) {
        this.newsCenterMenuBeanLists = newsCenterMenuBeanList;
        menuAdapter.setNewsCenterMenuBeanList(newsCenterMenuBeanLists);
        MyLogger.d(TAG, String.valueOf(newsCenterMenuBeanLists.size()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initEvent();
        initVp();
        initSlidingMenu();
        initMenuRecyclerView();
    }

    private void initMenuRecyclerView() {
        //从SlidingMenu中找到recyclerview
        RecyclerView mMenuRV = mSlidingMenu.findViewById(R.id.rv_menu);
        //设置布局管理器
        mMenuRV.setLayoutManager(new LinearLayoutManager(this));
        //创建适配器给recyclerview进行数据绑定
        menuAdapter = new HomeMenuAdaper(this, null);
        mMenuRV.setAdapter(menuAdapter);
    }

    private void initSlidingMenu() {
        //创建侧滑菜单对象
        mSlidingMenu = new SlidingMenu(this);
        //设置菜单从左边滑出
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        //设置侧滑菜单，默认不可以滑出
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //设置侧滑菜单滑出后，主界面的宽度
        mSlidingMenu.setBehindOffset(1000);
        //以内容的形式添加到Activity
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧滑菜单的布局
        mSlidingMenu.setMenu(R.layout.activity_home_menu);
    }

    private void initEvent() {
        mRgTba.setOnCheckedChangeListener(this);
    }

    private void initVp() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsCenterFragment());
        fragments.add(new SmartServiceFragment());
        fragments.add(new GovaffairsFragment());
        fragments.add(new SettingFragment());
        HomeVPFragmentAdapter adapter = new HomeVPFragmentAdapter(getSupportFragmentManager(), fragments);
        mVp.setAdapter(adapter);

        //给ViewPager设置页面滑动改变监听
        mVp.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRbHome.setChecked(true);
                break;
            case 1:
                mRbNewScenter.setChecked(true);
                break;
            case 2:
                mRbSmartService.setChecked(true);
                break;
            case 3:
                mRbGovaffairs.setChecked(true);
                break;
            case 4:
                mRbSetting.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int item = 0;
        switch (checkedId) {
            case R.id.rb_home:
                item = 0;
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                break;
            case R.id.rb_newscenter:
                item = 1;
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                break;
            case R.id.rb_smartservice:
                item = 2;
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                break;
            case R.id.rb_govaffairs:
                item = 3;
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                break;
            case R.id.rb_setting:
                item = 4;
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                break;
        }
        //ViewPager切换到对应的页面
        mVp.setCurrentItem(item, false);//false 不需要Viewpager页面切换的时候有滑动的动画

        //加载网络数据的入口
        BaseFragment baseFragment = (BaseFragment) fragments.get(item);
        if (baseFragment instanceof BaseLoadNetDataOperator) {
            ((BaseLoadNetDataOperator) baseFragment).loadNetData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //获取当前选中的TabFragment
    public BaseFragment getCurrentTabFragment() {
        int currentItem = mVp.getCurrentItem();
        BaseFragment baseFragment = (BaseFragment) fragments.get(currentItem);
        return baseFragment;
    }
}
