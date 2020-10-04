package com.ttit.zhihuibeijing.fragment;


import android.view.View;

import com.ttit.zhihuibeijing.base.BaseFragment;
import com.ttit.zhihuibeijing.base.BaseLoadNetDataOperator;

/**
 * Created by JW.S on 2020/9/26 1:17 PM.
 */
public class GovaffairsFragment extends BaseFragment implements BaseLoadNetDataOperator {

    @Override
    public void initTitle() {
        setIbMenuDisplayState(true);
        setIbPicTypeDisplayState(true);
        setTitle("政务");
    }

    @Override
    public View createContent() {
        return null;
    }

    @Override
    public void loadNetData() {

    }
}
