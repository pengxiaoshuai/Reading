package com.xms.ui.activity;

import android.util.Log;
import android.view.View;

import com.xms.R;
import com.xms.base.BaseActivity;
import com.xms.utils.ToastUtil;

/**
 * Created by dell on 2017/4/7.
 */

public class MySelfView_Volume extends BaseActivity{
    @Override
    public int getContentViewId() {
        return R.layout.act_myselfview1;
    }

    @Override
    public void initData() {

    }
    public void nihao(View view){
        ToastUtil.TextToast("你好");
        Log.e("00000","00000");
    }
    public void zaijian(View view){
        ToastUtil.TextToast("再见");
        Log.e("00000","00001");
    }
}
