package com.yc.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yc.weather.bean.CityInfo;
import com.yc.weather.bean.HourItem;
import com.yc.weather.interfaces.StaticValue;
import com.yc.weather.view.IndexScrollView;
import com.yc.weather.view.SecondTipsView;
import com.yc.weather.view.SevenDaysView;
import com.yc.weather.view.TodayView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SecondTipsView home_stv;
    private TodayView todayView;
    private IndexScrollView scrollView;
    private String data = "{\"airinfo\":{\"air_aqi\":100,\"air_quality\":\"良\"},\"cityinfo\":{\"areaid\":\"101010100\",\"city\":\"北京\",\"city_en\":\"beijing\",\"weatherid\":0},\"ftime\":\"1558594620000\",\"hourinfo\":[{\"date\":\"2019-05-23\",\"hour\":\"15:00\",\"img\":\"00\",\"temp\":35,\"weather\":\"晴\",\"wind_dir\":\"西南风\",\"wind_power\":\"3级\"},{\"date\":\"2019-05-23\",\"hour\":\"16:00\",\"img\":\"00\",\"temp\":36,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-23\",\"hour\":\"17:00\",\"img\":\"00\",\"temp\":36,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-23\",\"hour\":\"18:00\",\"img\":\"00\",\"temp\":35,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-23\",\"hour\":\"19:00\",\"img\":\"00\",\"temp\":33,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-23\",\"hour\":\"20:00\",\"img\":\"00\",\"temp\":31,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-23\",\"hour\":\"21:00\",\"img\":\"00\",\"temp\":30,\"weather\":\"晴\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-23\",\"hour\":\"22:00\",\"img\":\"01\",\"temp\":29,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-23\",\"hour\":\"23:00\",\"img\":\"01\",\"temp\":28,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"00:00\",\"img\":\"01\",\"temp\":26,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"01:00\",\"img\":\"01\",\"temp\":25,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"02:00\",\"img\":\"01\",\"temp\":24,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"03:00\",\"img\":\"01\",\"temp\":23,\"weather\":\"多云\",\"wind_dir\":\"东南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-24\",\"hour\":\"04:00\",\"img\":\"01\",\"temp\":22,\"weather\":\"多云\",\"wind_dir\":\"东南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"05:00\",\"img\":\"01\",\"temp\":21,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"06:00\",\"img\":\"01\",\"temp\":22,\"weather\":\"多云\",\"wind_dir\":\"西风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"07:00\",\"img\":\"01\",\"temp\":23,\"weather\":\"多云\",\"wind_dir\":\"西风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"08:00\",\"img\":\"01\",\"temp\":25,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"09:00\",\"img\":\"01\",\"temp\":26,\"weather\":\"多云\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"10:00\",\"img\":\"01\",\"temp\":28,\"weather\":\"多云\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-24\",\"hour\":\"11:00\",\"img\":\"01\",\"temp\":30,\"weather\":\"多云\",\"wind_dir\":\"南风\",\"wind_power\":\"1-2级\"},{\"date\":\"2019-05-24\",\"hour\":\"12:00\",\"img\":\"01\",\"temp\":33,\"weather\":\"多云\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-24\",\"hour\":\"13:00\",\"img\":\"01\",\"temp\":34,\"weather\":\"多云\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":\"2019-05-24\",\"hour\":\"14:00\",\"img\":\"01\",\"temp\":34,\"weather\":\"多云\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"}],\"isCacheData\":false,\"isDefaultCity\":false,\"isLocationCity\":false,\"lifeinfo\":[{\"des\":\"较舒适\",\"img\":\"01\",\"title\":\"舒适度指数\",\"type\":\"co\"},{\"des\":\"热\",\"img\":\"02\",\"title\":\"穿衣指数\",\"type\":\"ct\"},{\"des\":\"少发\",\"img\":\"03\",\"title\":\"感冒指数\",\"type\":\"gm\"},{\"des\":\"中\",\"img\":\"04\",\"title\":\"污染物指数\",\"type\":\"pl\"},{\"des\":\"适宜\",\"img\":\"05\",\"title\":\"旅游指数\",\"type\":\"tr\"},{\"des\":\"很强\",\"img\":\"06\",\"title\":\"紫外线指数\",\"type\":\"uv\"},{\"des\":\"较适宜\",\"img\":\"07\",\"title\":\"洗车指数\",\"type\":\"xc\"},{\"des\":\"较适宜\",\"img\":\"08\",\"title\":\"运动指数\",\"type\":\"yd\"}],\"nowtime\":\"2019-05-23 15:01:02\",\"recommended_clothing\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\",\"suninfo\":{\"sunrise\":\"04:52\",\"sunset\":\"19:30\"},\"warninginfo\":[{\"alarmtype\":\"17\",\"grade\":\"yellow\",\"message\":\"黄色高温预警\",\"stat\":true,\"validate\":true}],\"weatherinfo\":{\"humidity\":\"17\",\"img\":\"00\",\"skintemp\":\"35\",\"temp\":\"35\",\"temp_max\":\"37\",\"temp_min\":\"21\",\"weather\":\"晴\",\"wind_dir\":\"西南风\",\"wind_power\":\"3级\"},\"weekinfo\":[{\"date\":1558454400,\"img_day\":\"00\",\"img_night\":\"00\",\"temp_day\":34,\"temp_night\":17,\"weather_day\":\"晴\",\"weather_night\":\"晴\",\"week\":\"昨天\",\"wind_dir\":\"无持续风向\",\"wind_power\":\"1-2级\"},{\"date\":1558540800,\"img_day\":\"00\",\"img_night\":\"01\",\"temp_day\":37,\"temp_night\":21,\"weather_day\":\"晴\",\"weather_night\":\"多云\",\"week\":\"今天\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":1558627200,\"img_day\":\"01\",\"img_night\":\"00\",\"temp_day\":35,\"temp_night\":21,\"weather_day\":\"多云\",\"weather_night\":\"晴\",\"week\":\"周五\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"},{\"date\":1558713600,\"img_day\":\"01\",\"img_night\":\"07\",\"temp_day\":35,\"temp_night\":22,\"weather_day\":\"多云\",\"weather_night\":\"小雨\",\"week\":\"周六\",\"wind_dir\":\"南风\",\"wind_power\":\"1-2级\"},{\"date\":1558800000,\"img_day\":\"07\",\"img_night\":\"01\",\"temp_day\":25,\"temp_night\":17,\"weather_day\":\"小雨\",\"weather_night\":\"多云\",\"week\":\"周日\",\"wind_dir\":\"北风\",\"wind_power\":\"3-4级\"},{\"date\":1558886400,\"img_day\":\"00\",\"img_night\":\"00\",\"temp_day\":28,\"temp_night\":16,\"weather_day\":\"晴\",\"weather_night\":\"晴\",\"week\":\"周一\",\"wind_dir\":\"北风\",\"wind_power\":\"4-5级\"},{\"date\":1558972800,\"img_day\":\"00\",\"img_night\":\"00\",\"temp_day\":30,\"temp_night\":16,\"weather_day\":\"晴\",\"weather_night\":\"晴\",\"week\":\"周二\",\"wind_dir\":\"西南风\",\"wind_power\":\"1-2级\"},{\"date\":1559059200,\"img_day\":\"01\",\"img_night\":\"02\",\"temp_day\":31,\"temp_night\":17,\"weather_day\":\"多云\",\"weather_night\":\"阴\",\"week\":\"周三\",\"wind_dir\":\"南风\",\"wind_power\":\"3-4级\"}]}";
    private SevenDaysView sevenDaysView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_stv = findViewById(R.id.home_stv);
        todayView = findViewById(R.id.pager_todayView);
        scrollView = findViewById(R.id.pager_scorllView);
        scrollView.setTodayView(todayView);
        sevenDaysView = findViewById(R.id.pager_sdvFutrue);

        CityInfo cityInfo = JSON.parseObject(data, CityInfo.class);
        List<HourItem> items = new ArrayList<>();
        for (CityInfo.Hourinfo hinfo : cityInfo.hourinfo) {
            items.add(new HourItem(hinfo.wind_power, hinfo.hour, hinfo.temp, StaticValue.getWeatherImg(hinfo.img, hinfo.hour), 0/**空气质量，int*/));
        }
        todayView.setUpDataLeft(new TodayView.upDataLeft() {
            @Override
            public void onUpdata(int maxTemp, int minTemp, int tempBaseTop, int tempBaseBottom, int baseline) {
                home_stv.setData(tempBaseTop, tempBaseBottom, baseline, maxTemp, minTemp);
            }
        });
        todayView.setHourItem(items);
        sevenDaysView.initData(cityInfo.weekinfo);
    }
}
