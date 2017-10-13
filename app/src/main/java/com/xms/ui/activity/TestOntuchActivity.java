package com.xms.ui.activity;

import android.util.Log;
import android.view.View;

import com.xms.R;
import com.xms.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by dell on 2017/6/20.
 */

public class TestOntuchActivity extends BaseActivity{
    @Override
    public int getContentViewId() {
        return R.layout.activity_test_ontuch;
    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.activity_test_ontuch_lin,R.id.activity_test_ontuch_text})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.activity_test_ontuch_lin:
                Log.e("0000","1111");
                break;
            case  R.id.activity_test_ontuch_text:
                Log.e("0000","2222");
                break;
            default:
                break;
        }
    }
}
