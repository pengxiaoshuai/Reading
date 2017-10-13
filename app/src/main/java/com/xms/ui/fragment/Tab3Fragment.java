package com.xms.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.xms.R;
import com.xms.base.BaseFragment;
import com.xms.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页第三个fragment
 * Created by dell on 2017/2/24.
 */

public class Tab3Fragment extends BaseFragment {
    private View mRootView;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> mFragmentData;

    @BindView(R.id.viewpager)
     ViewPager mViewPager;

    @BindView(R.id.tab)
     PagerSlidingTabStrip mTab;
    @Override
    protected View initView(LayoutInflater inflater) {
        mRootView=inflater.inflate(R.layout.fragment_tab3,null);
        return mRootView;
    }
    //初始化数据用的
    @Override
    public void initData() {
        setTitle();
        mTvForTitle.setText("第三个fragment");
        mImgvForLeft.setVisibility(View.INVISIBLE);
        initview();//初始化滑动导航栏
        setTabsValue();//初始化导航栏滑动轴的颜色和字体颜色
    }

    /**
     * 初始化滑动导航栏
     */
    private void initview(){
        mFragmentData = new ArrayList<>();
        mFragmentData.add(new TabStrip1Fragment());
        mFragmentData.add(new TabStrip2Fragment());
        mFragmentData.add(new TabStrip3Fragment());
        fragmentPagerAdapter = new FragmentPagerAdapter(
                getChildFragmentManager()) {

            private final String[] titles = { "recyclerview", "gridview样式", "瀑布流" };

            @Override
            public int getCount() {
                return mFragmentData.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragmentData.get(arg0);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTab.setViewPager(mViewPager);
    }

    /**
     * 初始化导航栏滑动轴的颜色和字体颜色
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        mTab.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTab.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTab.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
                        .getDisplayMetrics()));
        // 设置Tab Indicator的高度
        mTab.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                        .getDisplayMetrics()));
        // 设置Tab标题文字的大小
        mTab.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, getResources()
                        .getDisplayMetrics()));
        // 设置Tab Indicator的颜色
        mTab.setIndicatorColor(Color.parseColor("#40AEDB"));
        // 设置选中Tab文字的颜色
        mTab.setSelectedTextColor(Color.parseColor("#40AEDB"));
        // 取消点击Tab时的背景色
        mTab.setTabBackground(0);
    }
}
