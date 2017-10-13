package com.xms.grobal;

import android.os.Environment;

import java.io.File;

/**
 *  文件地址
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class AppConfig
{
    public final static String APP_SDCAR_FOLDER = Environment.getExternalStorageDirectory() + File.separator + "PQX" + File.separator;
    
    public final static String DEFAULT_SAVE_DB_PATH = APP_SDCAR_FOLDER + "db" + File.separator;
    
    public final static String DEFAULT_SAVE_LOG_PATH = APP_SDCAR_FOLDER + "log" + File.separator;
    
    public final static String DEFAULT_SAVE_APK_PATH = APP_SDCAR_FOLDER + "apk" + File.separator;
}
