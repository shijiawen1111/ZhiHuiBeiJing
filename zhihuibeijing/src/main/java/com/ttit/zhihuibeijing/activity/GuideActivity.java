package com.ttit.zhihuibeijing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.adapter.GuideVpAdpter;
import com.ttit.zhihuibeijing.utils.Constant;
import com.ttit.zhihuibeijing.utils.MyLogger;
import com.ttit.zhihuibeijing.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JW.S on 2020/9/26 7:10 PM.
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "GuideActivity";
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.bt_start)
    Button mBtnStart;
    @BindView(R.id.container_gray_point)
    LinearLayout mLLContainerGrayPoint;
    @BindView(R.id.red_point)
    View mRedPoint;

    //向导图片
    private int[] imgs = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private List<ImageView> views;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViewPager();
        initGrayPoint();
    }

    private void initViewPager() {
        views = new ArrayList<>();
        for (int resId : imgs) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(resId);
            views.add(iv);
        }
        mVp.setAdapter(new GuideVpAdpter(views));
        //设置页面的滑动监听
        mVp.addOnPageChangeListener(this);
    }

    //动态的创建灰色的点
    private void initGrayPoint() {
        for (int resId : imgs) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_gray_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            params.rightMargin = 20;
            mLLContainerGrayPoint.addView(view, params);
        }
    }

    //小灰点之间的宽度
    private int width = 0;

    //position:当前滑动页面的下标    positionOffset：页面的滑动比率    positionOffsetPixels：页面滑动的实际像素
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (width == 0) {
            width = mLLContainerGrayPoint.getChildAt(1).getLeft() - mLLContainerGrayPoint.getChildAt(0).getLeft();
            MyLogger.i(TAG, "width:" + width);
        }
        //修改小红点与相对布局的左边距
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
        params.leftMargin = (int) (position * width + width * positionOffset);
        mRedPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == imgs.length - 1) {
            //显示按钮
            mBtnStart.setVisibility(View.VISIBLE);
        } else {
            //隐藏按钮
            mBtnStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //给开始体验按钮设置点击事件
    public void onClick(View view) {
        //进入主界面
        SPUtils.saveBoolean(this, Constant.KEY_HAS_GUIDE, true);
        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
