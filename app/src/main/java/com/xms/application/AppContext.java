package com.xms.application;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class AppContext extends Application {

	/**整个应用全局可访问数据集合**/
	private static Map<String, Object> gloableData = new HashMap<String, Object>();

	public static final int NETTYPE_WIFI = 0x01;

	public static final int NETTYPE_CMWAP = 0x02;

	public static final int NETTYPE_CMNET = 0x03;

	protected static Context context;


	/** 屏幕宽度 */
	public static int screenWidth;
	/** 屏幕高度 */
	public static int screenHeight;
	/** 缓存路径 */
	public static String cachePath;


	public static SharedPreferences sp;


	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		//初始化imageloader
		initImageLoader(this);
	//	getScreenSize();//对手机版本有要求 低版本手机不接受这里的方法 会崩溃
		// 初始化图片加载
		// 数据库操作对象
		// 异常处理
		sp = getSharedPreferences("meiya", 0);
	}

	/**
	 * 获取屏幕宽高 void
	 */
	private void getScreenSize() {

		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels; // 1024
		screenHeight = dm.heightPixels; // 720
		float density = dm.density; // 1.0
		float densityDpi = dm.densityDpi; // 160.0
	}


	public static Context getContext() {
		return context;
	}


	/**
	 * 初始化ImageLoader
	 *
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// 获取缓存图片目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPriority(Thread.MAX_PRIORITY - 1)
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// 设置图片下载和显示的工作队列排序
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCacheExtraOptions(400, 800)
				.threadPoolSize(3)
				.diskCacheSize(50 * 1024 * 1024)
				// .memoryCacheSize(1 * 1024 * 1024)
				// .memoryCache(new FIFOLimitedMemoryCache())
				// .memoryCacheSize(3 * 1024 * 1024)
				// .memoryCacheExtraOptions(480, 800) // default = device screen
				// dimensions
				// .diskCacheExtraOptions(320, 480, null)
				.imageDownloader(
						new BaseImageDownloader(context, 10 * 1000, 30 * 1000))
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 获取App安装包信息
	 *
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检测网络是否可用
	 *
	 * @return
	 */
	public static boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 *
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (extraInfo != null) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	public String getRunningActivityName() {
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		return runningActivity;
	}

	/**
	 * 检测app是否在后台运行
	 *
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				/*
				 * BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000
				 * PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
				Log.i(context.getPackageName(), "此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 对外提供Application
	 * @return
	 */
	public static Context gainContext() {
		return context;
	}
}
