package com.ttit.zhihuibeijing.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ttit.zhihuibeijing.R;
import com.ttit.zhihuibeijing.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by JW.S on 2020/9/27 2:06 PM.
 */
public abstract class BaseFragment extends Fragment {
    @BindView(R.id.ib_menu)
    ImageButton mIbMenu;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ib_pic_type)
    ImageButton mIbPicType;
    @BindView(R.id.container)
    FrameLayout mContainer;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_base, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
    }

    //初始化标题   让每个子类去进行实现
    public abstract void initTitle();

    //设置Menu的显示状态
    public void setIbMenuDisplayState(boolean isShow) {
//        mIbMenu.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mIbMenu.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //设置PicType的显示状态
    public void setIbPicTypeDisplayState(boolean isShow) {
        mIbPicType.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //设置标题内容
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    //创建内容
    public abstract View createContent();

    //向容器里面添加内容
    public void addView(View view) {
        //清空原来的内容
        mContainer.removeAllViews();
        //添加内容
        mContainer.addView(view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ib_menu)
    public void onViewClicked() {
        //对于侧滑菜单进行切换
        //目标：获取SlidingMenu -->MainActivity
        ((HomeActivity) getActivity()).mSlidingMenu.toggle();
    }
}
