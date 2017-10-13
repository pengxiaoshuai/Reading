package com.xms.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xms.MySelfViewWidget.ViewPagerTopitem;
import com.xms.R;
import com.xms.base.BaseActivity;
import com.xms.ui.fragment.VpSimpleFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/6/19.
 */

public class ViewpagerTopBar extends BaseActivity{

    @BindView(R.id.activity_viewpager_topbar_top)
    ViewPagerTopitem mtop;
    @BindView(R.id.activity_viewpager_topbar_viewpager)
    ViewPager mviewpager;
    private List<Fragment> mlist;
    private FragmentPagerAdapter madapter;
    private List<String> mdatas = Arrays.asList("内容1","内容2","内容3","内容4","内容5","内容6"
            ,"内容7");
    @Override
    public int getContentViewId(){
        return R.layout.activity_viewpager_topbar;
    }

    @Override
    public void initData(){
        mlist = new ArrayList<>();
        for(String data: mdatas){
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mlist.add(fragment);
        }
        madapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlist.get(position);
            }

            @Override
            public int getCount() {
                return mlist.size();
            }
        };

        mviewpager.setAdapter(madapter);
        mtop.setTabItemTitles(mdatas);
        mtop.setViewPager(mviewpager,0);
    }
}



