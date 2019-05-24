package com.yc.weather.bean;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojie on 2017/8/17.
 */

//首页一页的城市Info
public class CityInfo {

    public String nowtime;
    public String ftime;
    public Weatherinfo weatherinfo;
    public Cityinfo cityinfo;
    public Airinfo airinfo;
    public String recommended_clothing = "";
    public Suninfo suninfo;
    public Boolean isCacheData = false;
    public Boolean isLocationCity = false;
    public Boolean isDefaultCity = false;
    public List<WarningInfo> warninginfo = new ArrayList<>();
    public List<Lifeinfo> lifeinfo = new ArrayList<>();
    public List<Weekinfo> weekinfo = new ArrayList<>();
    public List<Hourinfo> hourinfo = new ArrayList<>();

    //当前城市
    public static class Cityinfo {
        public String areaid;
        public String city;
        public String city_en;
        public int weatherid;
    }

    //当前城市天气
    public static class Weatherinfo {
        public String weather = "0";
        public String img;
        public String temp = "0";
        public String temp_min = "0";
        public String temp_max = "0";
        public String humidity = "0";
        public String wind_dir = "0";
        public String wind_power = "0";
        public String skintemp = "0";
    }

    //空气质量
    public static class Airinfo {
        public int air_aqi;
        public String air_quality = "0";
    }

    //天气预警
    public static class WarningInfo {
        public boolean stat;
        public String grade = "";
        public String message = "";
        public String alarmtype = "";

        public boolean isValidate() {
            return stat == true && !TextUtils.isEmpty(grade) && !TextUtils.isEmpty(message);
        }
    }

    //生活指数
    public static class Lifeinfo {
        public String type = "";
        public String img = "";// = ""="";
        public String title = "";// = "紫外线指数";
        public String des = "";// = "很强";
    }

    //未来一周天气
    public static class Weekinfo {
        public long date;// = "1487253663729";
        public String week = "";// = "昨天";
        public String wind_power = "";// ="";// = "微风";
        public String wind_dir = "";// = "南风";
        public String weather_day = "";// = "阴";
        public String img_day = "";// = "02";
        public int temp_day;// = "27";
        public String weather_night = "";// = "晴";
        public String img_night = "";// = "00";
        public int temp_night;// = "17";
    }

    //24小时天气
    public static class Hourinfo {
        public String date = "";// = "2016-1-1";
        public String hour = "";// = "00";
        public String weather = "";// = "晴";
        public String img = "";// = "00";
        public int temp;// = 3;
        public String wind_power = "";// = "微风";
        public String wind_dir = "";// = "3级";
    }

    //日出日落
    public static class Suninfo {
        public String sunrise = "";
        public String sunset = "";
    }
}
