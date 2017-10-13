package com.xms.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.xms.application.AppContext;

/**
 *	Toast工具类
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class ToastUtil {
	private static Toast toast = null;

	private static int LENGTH_SHORT = Toast.LENGTH_SHORT;
	public static void TextToast(String msg) {

		if (toast != null) {
			toast.cancel();
		}
		// 创建一个Toast提示消息
		toast = Toast.makeText(AppContext.getContext(), msg, LENGTH_SHORT);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 220);
		// 显示消息
		toast.show();
	}

}