package com.ttit.zhihuibeijing.fragment;


import android.view.View;

import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.base.BaseLoadNetDataOperator;

/**
 * Created by JW.S on 2020/9/26 1:17 PM.
 */
public class SettingFragment extends BaseFragment implements BaseLoadNetDataOperator {

    @Override
    public void initTitle() {
        setIbMenuDisplayState(false);
        setIbPicTypeDisplayState(false);
        setTitle("设置");
    }

    @Override
    public View createContent() {
        return null;
    }

    @Override
    public void loadNetData() {

    }
}
