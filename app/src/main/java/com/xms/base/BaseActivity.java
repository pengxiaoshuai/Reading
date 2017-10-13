package com.xms.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xms.R;
import com.xms.grobal.AppManager;
import com.xms.utils.SystemStatusManager;

import butterknife.ButterKnife;


/**
 * Activity基类
 *
 * @author Only You
 * @version 1.0
 * @date 2016年1月10日
 */
public abstract class BaseActivity extends FragmentActivity {

	public abstract int getContentViewId();

	View mTitleView = null;

	public abstract void initData();

	protected ImageButton mImgvForLeft = null;

	protected TextView mTvForTitle = null;

	protected ImageButton mImgvForRight = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(getContentViewId());
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		//一体化标题栏
		setTranslucentStatus();
		//初始化ButterKnife的数据
		ButterKnife.bind(this);
		//初始化activity数据
		initData();
	}

	/**
	 * 设置Title栏蓝色
	 */
	protected void setTitle() {
		mTitleView = findViewById(R.id.common_title_layout);
		mImgvForLeft = (ImageButton) findViewById(R.id.common_title_left);
		mImgvForRight = (ImageButton) findViewById(R.id.common_title_right);
		mTvForTitle = (TextView) findViewById(R.id.common_title_tv);
	}

	/**
     * 一体化标题栏
     */
	protected void setTranslucentStatus(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemStatusManager tintManager = new SystemStatusManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.them_color);
	}

	/**
	 * 跳转到下一个Activity
	 *
	 * @param class1
	 */
	public void gotoActivity(Class class1) {
		Intent i = new Intent(this, class1);
		startActivity(i);
	}

	/**
	 * 获得状态栏高度
	 *
	 * @return
	 */
	public int getStatusBarHeight() {
		Rect rectangle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
		int statusBarHeight = rectangle.top;
		if (statusBarHeight == 0) {
			int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
			if (resourceId > 0) {
				statusBarHeight = getResources().getDimensionPixelSize(resourceId);
			}
		}
		return statusBarHeight;
	}

	/**
	 * 传递数据并跳转到下一Activity
	 *
	 * @param bundle
	 * @param class1
	 */
	public void gotoActivity(Bundle bundle, Class class1) {
		Intent i = new Intent(this, class1);
		i.putExtras(bundle);
		startActivity(i);
	}

	/**
	 * 传递数据并跳转到下一Activity,并且有返回数据
	 *
	 * @param bundle
	 * @param class1
	 * @param requestCode
	 */
	public void gotoActivityForResult(Bundle bundle, Class class1,
									  int requestCode) {
		Intent i = new Intent(this, class1);
		i.putExtras(bundle);
		startActivityForResult(i, requestCode);
	}

	public void gotoActivityForResult(Class class1, int requestCode) {
		Intent i = new Intent(this, class1);
		startActivityForResult(i, requestCode);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	public void onDestory(Class context) {
		AppManager.getAppManager().finishActivity(context);
	}

	/**
	 * 隐藏键盘
	 *
	 * @param context
	 */
	public static void hiddenKeyboard(Activity context) {
		if (context.getCurrentFocus() != null) {
			// 焦点不为空
			InputMethodManager inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (inputMethodManager.isActive(context.getCurrentFocus())) {
				// 隐藏键盘
				context.getCurrentFocus().clearFocus();
				inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * 显示键盘
	 *
	 * @param context
	 */
	public static void showKeyboard(Activity context) {
		if (context.getCurrentFocus() != null) {
			// 焦点不为空
			InputMethodManager inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (inputMethodManager.isActive(context.getCurrentFocus())) {
				// 键盘未显示
				inputMethodManager.showSoftInput(context.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
			}
		}
	}
}

