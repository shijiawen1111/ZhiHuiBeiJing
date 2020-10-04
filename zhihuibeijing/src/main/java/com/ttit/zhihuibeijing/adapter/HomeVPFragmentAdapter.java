package com.ttit.zhihuibeijing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by JW.S on 2020/9/26 1:23 PM.
 */
public class HomeVPFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public HomeVPFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
