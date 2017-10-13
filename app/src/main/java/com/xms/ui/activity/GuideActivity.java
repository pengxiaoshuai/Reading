package com.xms.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xms.R;
import com.xms.constants.InterfaceDefinition;
import com.xms.utils.PreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页面
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class GuideActivity extends Activity {

    @BindView(R.id.mViewPager)
     ViewPager mViewPager;

    @BindView(R.id.lines)
     LinearLayout lines;


    @BindView(R.id.imgone)
     ImageView mimg1;
    @BindView(R.id.imgtwo)
     ImageView mimg2;
    @BindView(R.id.imgthree)
     ImageView mimg3;

    private List<ImageView> mlist;
    private List<View> data = null;

    // 存放引导页图片
    private int[] pping = {R.mipmap.yd01, R.mipmap.yd02, R.mipmap.yd03};

    // 引导页最后一页按钮
    @BindView(R.id.mButton)
     TextView mView;

    // 点击按钮跳转主页面
    @OnClick({R.id.mButton})
     void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButton:
                // 第一次进入
                PreferencesUtil.put(this, InterfaceDefinition.PreferencesUser.WELCOME_STATE, true);
                Intent to = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(to);
                finish();
                break;
            default:
                break;
        }
    }
    //    @OnClick(R.id.common_dialog_cancle)
//    void finishA(View view) {
//        switch (view.getId()){
//            R.id.common_dialog_cancle:
//            break;
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_guide);
        ButterKnife.bind(this);
        // 通知栏背景变透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initDate();
        checkimg(0);
    }

    private void initDate() {
        data = new ArrayList<View>();
        mlist=new ArrayList<>();
        mlist.add(mimg1);
        mlist.add(mimg2);
        mlist.add(mimg3);
        for (int i = 0; i < pping.length; i++) {
            ImageView View = new ImageView(this);
            View.setBackgroundResource(pping[i]);
            data.add(View);
        }
        MyViewpager myViewpager = new MyViewpager(data);
        mViewPager.setAdapter(myViewpager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                // 判断按钮显隐藏
                if (arg0 == 2) {
                    mView.setVisibility(View.VISIBLE);
                } else {
                    mView.setVisibility(View.GONE);
                }
                checkimg(arg0);
            }
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }
    private void checkimg(int check){
        for (int i = 0; i < mlist.size(); i++) {
            mlist.get(i).setImageResource(R.mipmap.dian1);
        }
        mlist.get(check).setImageResource(R.mipmap.dian2);
    }
    class MyViewpager extends PagerAdapter {

        List<View> views;

        public MyViewpager(List<View> views) {
            this.views = views;
        }

        public int getCount() {
            return views.size();
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
