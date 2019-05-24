package com.yc.weather;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


/**
 * Created by zhaojie on 2017/5/4.
 * <p>
 * 项目的基础Application，启动会默认加载它
 */

public class BaseApplication extends MultiDexApplication {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

}
