package com.tnt.watchhome.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentList = list;

    }


    @Override
    public Fragment getItem(int position) {
        if (null == mFragmentList)return null ;
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (null == mFragmentList)return 0 ;
        return mFragmentList.size();
    }
}
