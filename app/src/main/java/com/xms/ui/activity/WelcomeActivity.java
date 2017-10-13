package com.xms.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.xms.R;
import com.xms.constants.InterfaceDefinition;
import com.xms.utils.PreferencesUtil;


/**
 * 欢迎页面
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class WelcomeActivity extends Activity {

    SharedPreferences preferences = null;

    Boolean WelcomeState = null;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
        setContentView(R.layout.act_welcome);

        // 通知栏背景变透明
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        WelcomeState = (Boolean) PreferencesUtil.get(this,
                InterfaceDefinition.PreferencesUser.WELCOME_STATE, false);
        handler.sendEmptyMessageDelayed(0, 2000);
    }


    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            if (WelcomeState) {
                Intent intent = new Intent(WelcomeActivity.this,
                        MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.my_scale_action,
                        R.anim.my_alpha_action);
                finish();
            } else {
                Intent to = new Intent(WelcomeActivity.this, GuideActivity.class);
                startActivity(to);
                finish();
            }
        }
    };
    public static long test(Long mlong){
        return mlong;
    }
    public static void main(String[] args){
        Long l = null;
        test(l);
    }
}
