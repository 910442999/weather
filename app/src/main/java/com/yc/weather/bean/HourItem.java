package com.yc.weather.bean;

import android.graphics.PointF;
import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by zhaojie on 2017/5/7.
 */
public class HourItem implements Serializable {
    public String time; //时间点
    public Rect windyBoxRect; //表示风力的box
    public String windy; //风力
    public int temperature; //温度
    public PointF tempPoint; //温度的点坐标
    public int res = -1; //图片资源(有则绘制)
    public int air = 0; //空气质量等级 :优 良 中 重

    public HourItem(String windy, String time, int temperature, int res, int air) {
        this.windy = windy;
        this.time = time;
        this.temperature = temperature;
        this.res = res;
        this.air = air;
    }
}