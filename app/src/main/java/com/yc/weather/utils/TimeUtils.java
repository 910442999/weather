package com.yc.weather.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaojie on 16/12/2.
 */
public class TimeUtils {
    /**
     * 获取当前系统时间
     */
    public static String getDate() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(
                System.currentTimeMillis()));
    }

    public static String getDayTime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    public static String getUpLoadDate() {
        return new SimpleDateFormat("MM月dd日HH:mm").format(new Date(
                System.currentTimeMillis()));
    }

    public static String getMDYDate() {
        return new SimpleDateFormat("MMddyy").format(new Date(
                System.currentTimeMillis()));
    }

    /**
     * 将二进制时间返回为时间格式
     */
    public static String getCurrentTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    /**
     * 将二进制时间返回为时间格式
     */
    public static String getMDTime() {
        return new SimpleDateFormat("MM月dd日").format(new Date(System.currentTimeMillis()));
    }

    /**
     * 将二进制时间返回为时间格式
     */
    public static boolean isDay(String cur, String sunRise, String sunSet) {
        boolean b;
        String nowDate = (TextUtils.isEmpty(cur) ? new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())) : cur).replaceAll(":", "").replaceAll("：", "");
        int calTime_r = Integer.valueOf(sunRise.replaceAll(":", "").replaceAll("：", ""));
        int calTime_s = Integer.valueOf(sunSet.replaceAll(":", "").replaceAll("：", ""));
        int calTime_now = Integer.valueOf(nowDate);
        b = (calTime_now >= calTime_r && calTime_now <= calTime_s);
        return b;
    }

    public static String getCurrentDay(long time) {
        return new SimpleDateFormat("MM/dd").format(new Date(time));
    }

    /**
     * 获取时间差
     */
    public static String disparityTime(Date d1, Date d2) {
        String mdate = "";
        try {
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);
            mdate = ("" + days + "天" + hours + "小时" + minutes + "分");
        } catch (Exception e) {
        }
        return mdate;
    }

    public static String getLieRefreshData() {
        String curTime = new SimpleDateFormat("mm").format(new Date(
                System.currentTimeMillis()));
        int m = (Math.abs(Integer.parseInt(curTime)) % 10);
        String returnTime = "";
        if (m == 0) {
            returnTime = "刚刚更新";
        } else {
            returnTime = "更新于" + m + "分钟前";
        }
        return returnTime;
    }
}
