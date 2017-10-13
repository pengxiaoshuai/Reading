package com.xms.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.xms.R;
import com.xms.adapter.ImageAdapter;
import com.xms.base.BaseFragment;
import com.xms.widget.CircleFlowIndicator;
import com.xms.widget.ViewFlow;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 首页第二个fragment
 * Created by dell on 2017/2/24.
 */

public class Tab2Fragment extends BaseFragment {
    private View mRootView;
    private ImageAdapter imageAdapter;
    private ArrayList<String> mimgs;

    @BindView(R.id.vf)
    ViewFlow mFv;  //无限轮播的viewpager
    @BindView(R.id.cfi)
    CircleFlowIndicator mCfi; //viewpager底部小圆点
    @Override
    protected View initView(LayoutInflater inflater) {
        mRootView=inflater.inflate(R.layout.fragment_tab2,null);
        return mRootView;
    }
    //初始化数据用的
    @Override
    public void initData(){
        if (!isFirstLoad()){//这样可以避免每次切换到这个Fragment里都实现加载
            return;
        }
        init();
        setTitle();
        mImgvForLeft.setVisibility(View.INVISIBLE);
        mTvForTitle.setText("第二个");
        setFirstLoad(false);//这个可以放在网络访问数据成功后 避免多次加载
    }

    /**
     * 初始化无限轮播的viewpager
     */
    private void init() {
        mimgs=new ArrayList<>();
        mimgs.add("http://120.203.228.227/store/HU91YLM.jpg");
        mimgs.add("http://120.203.228.227/store/HU9AV58.jpg");
        mimgs.add("http://120.203.228.227/store/HU9A4PV.jpg");
        imageAdapter = new ImageAdapter(mContext, mimgs);
        // 给ViewFlow添加适配器
        mFv.setAdapter(imageAdapter);
        // 给ViewFlow设置缓冲数量，实际图片的张数
        mFv.setmSideBuffer(mimgs.size());
        // 将圆点指示器添加到ViewFlow中
        mFv.setFlowIndicator(mCfi);
        // 设置时间的跨度（轮播图片的间隔）
        mFv.setTimeSpan(1000);
        // 设置初始位置
        mFv.setSelection(0);
        // 启动自动播放
        mFv.startAutoFlowTimer();
    }
}
