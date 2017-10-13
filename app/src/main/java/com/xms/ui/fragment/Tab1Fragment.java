package com.xms.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.xms.R;
import com.xms.base.BaseFragment;
import com.xms.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页第一个fragment
 * Created by dell on 2017/2/24.
 */

public class Tab1Fragment extends BaseFragment {
    private View mRootView;

    @BindView(R.id.common_title_left)
    ImageButton mbtn;

    @Override
    protected View initView(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.fragment_tab1, null);
        return mRootView;
    }

    //初始化数据用的
    @Override
    public void initData() {
        setTitle();
        mImgvForLeft.setVisibility(View.INVISIBLE);
        mTvForTitle.setText("第一个");
    }

    //事件监听
    @OnClick(R.id.fragment_tab_text)
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.fragment_tab_text:
                ToastUtil.TextToast("事件监听ok");
                break;
            default:
                break;
        }
    }

}
