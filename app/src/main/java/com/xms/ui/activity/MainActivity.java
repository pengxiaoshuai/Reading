package com.xms.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.xms.R;
import com.xms.application.AppContext;
import com.xms.base.BaseActivity;
import com.xms.grobal.AppManager;
import com.xms.ui.fragment.Tab1Fragment;
import com.xms.ui.fragment.Tab2Fragment;
import com.xms.ui.fragment.Tab3Fragment;
import com.xms.utils.ToastUtil;

import butterknife.BindView;


/**
 * 主Activity
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class MainActivity extends BaseActivity {

//    @BindView(R.id.rb1)
//    RadioButton mRbt1;
//
//    @BindView(R.id.rb2)
//    RadioButton mRbt2;
//
//    @BindView(R.id.rb3)
//    RadioButton mRbt3;

    @BindView(R.id.act_main_ftb)
    FragmentTabHost mFragmentTabHost;

    @BindView(R.id.group)
    RadioGroup mGrop;

    boolean isExit;// 标记是否退出

    private AppContext mAppContext;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mAppContext = (AppContext) getApplicationContext();
        ToastUtil.TextToast("登陆成功");
        initFragmentTabHost();
    }

    //初始化首页数据
    private void initFragmentTabHost() {
        mFragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.act_main_fl);
        String Tab1 = getResources().getString(R.string.tab_1);
        String Tab2 = getResources().getString(R.string.tab_2);
        String Tab3 = getResources().getString(R.string.tab_3);
        mFragmentTabHost.addTab(
                mFragmentTabHost.newTabSpec(Tab1).setIndicator(Tab1),
                Tab1Fragment.class, null);
        mFragmentTabHost.addTab(
                mFragmentTabHost.newTabSpec(Tab2).setIndicator(Tab2),
                Tab2Fragment.class, null);
        mFragmentTabHost.addTab(
                mFragmentTabHost.newTabSpec(Tab3).setIndicator(Tab3),
                Tab3Fragment.class, null);
        mGrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        mFragmentTabHost.setCurrentTab(0);
                        break;
                    case R.id.rb2:
                        mFragmentTabHost.setCurrentTab(1);
                        break;
                    case R.id.rb3:
                        mFragmentTabHost.setCurrentTab(2);
                        break;

                    default:
                        break;

                }
            }
        });
        mFragmentTabHost.setCurrentTab(0);
    }

    //
//    @OnClick(R.id.common_dialog_cancle)
//    void finishA(View view) {
//        switch (view.getId()){
//            R.id.common_dialog_cancle:
//            break;
//        }
//    }
    // 重写onkeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    // 退出方法
    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.TextToast("再按一次退出程序");
            mhandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppManager.getAppManager().AppExit(MainActivity.this);
        }

    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}

