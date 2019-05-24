package com.yc.weather.interfaces;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;


import com.yc.weather.BaseApplication;
import com.yc.weather.R;
import com.yc.weather.utils.TimeUtils;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by zhaojie on 2017/4/19.
 */

public class StaticValue {

    /**
     * staticValues
     */

    public static final String ISGRAYENVIRO = "is_gray_enviro";

    //有夜间模式的图标ID
    public static final String[] nightIcon = {"00", "01", "03", "13", "102", "103", "301", "405", "406"};
    public static final String SUNRISE = "sunrise";
    public static final String SUNSET = "sunset";


    //获取对应key的天气本地图片,24小时制
    public static int getWeatherImg(String key, String cur) {
        if (TextUtils.isEmpty(key))
            return R.drawable.ico_none;
        String title = "a";
        for (String s : nightIcon) {
            if (s.equals(key)) {
                title = "n";
                break;
            }
        }
        return getResId(title + key, R.drawable.class);
    }

    //获取对应key的天气本地图片,当前时间制
    public static int getWeatherImg(String key) {
        if (TextUtils.isEmpty(key))
            return R.drawable.ico_none;
        String title = "a";
        for (String s : nightIcon) {
            if (s.equals(key)) {
                title = "n";
                break;
            }
        }
        return getResId(title + key, R.drawable.class);
    }

    //获取对应key的天气本地图片,当前时间制
    public static int getLifeImg(String key) {
        if (TextUtils.isEmpty(key))
            return R.drawable.ico_none;
        String title = "life";
        return getResId(title + key, R.drawable.class);
    }


    public static String getApplicationName(Context c) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        String applicationName = "";
        try {
            packageManager = c.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(c.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if (packageManager != null && applicationInfo != null) {
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        }
        return applicationName;
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getWarningColor(String code) {
        int color = 0;
        switch (code) {
            case "orange":
                color = 0xfff67223;
                break;
            case "blue":
                color = 0xff496ce2;
                break;
            case "yellow":
                color = 0xfff5a623;
                break;
            case "red":
                color = 0xffe92a2a;
                break;
        }
        return color;
    }
}
