package com.yc.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.yc.weather.BaseApplication;


/**
 * Created by ZhaoJie on 2016/9/27.
 */
public class DisplayUtils {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((pxValue * 160) / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

    public static int dip2px(float dipValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

    /**
     * 获取屏幕宽高
     */
    public static int[] getDefaultDisplayMetrics(Context c) {
        int[] i = new int[2];
        i[0] = c.getResources().getDisplayMetrics().widthPixels;
        i[1] = c.getResources().getDisplayMetrics().heightPixels;
        return i;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    //隱藏軟鍵盤
    public static void hiddenInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (context instanceof Activity) {
                imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }
}

