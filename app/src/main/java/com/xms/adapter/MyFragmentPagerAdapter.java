package com.xms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * fragment切换适配器
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    String[] mTitle;
    
    List<Fragment> mFragments = null;
    
    public MyFragmentPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> Fragments)
    {
        super(fm);
        mTitle = titles;
        mFragments = Fragments;
    }
    
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitle[position];
    }
    
    @Override
    public int getCount()
    {
        return mTitle.length;
    }
    
    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }
}
