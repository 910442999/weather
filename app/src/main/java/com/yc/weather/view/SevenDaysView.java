package com.yc.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yc.weather.bean.CityInfo.Weekinfo;
import com.yc.weather.interfaces.StaticValue;
import com.yc.weather.utils.DisplayUtils;
import com.yc.weather.utils.TimeUtils;
import com.yc.weather.view.bessel.BesselUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojie on 2017/6/6.
 */

public class SevenDaysView extends View {

    public List<Weekinfo> datas = new ArrayList<>();

    public SevenDaysView(Context context) {
        this(context, null);
    }

    public SevenDaysView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SevenDaysView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mContext = context;
        initView();
    }

    private Context mContext;
    private int mTopMargin = DisplayUtils.dip2px(20);
    private int mTextMargin = DisplayUtils.dip2px(10);

    private int mTextSize_l = DisplayUtils.dip2px(14);
    private int mTextSize_m = DisplayUtils.dip2px(12);
    private int mTextSize_s = DisplayUtils.dip2px(10);
    //线条颜色
    private int mColorDay = Color.argb(255, 180, 150, 0);
    private int mColorDay_dark = Color.argb(100, 180, 150, 0);
    private int mColorNight = Color.argb(255, 80, 170, 255);
    private int mColorNight_dark = Color.argb(100, 80, 170, 255);
    //线粗细
    private int mLineWidth = DisplayUtils.dip2px(3);
    //item数量
    private int itemSize = 7;

    private int mSimpleItemWidth = DisplayUtils.dip2px(80);

    private float LineHeight = DisplayUtils.dip2px(160);

    private int drawableSize = DisplayUtils.dip2px(26);

    private List<Float> p_d_list = new ArrayList<>();
    private List<Float> p_n_list = new ArrayList<>();

    //顶部
    private float mTop_Top = mTopMargin;
    //顶部View最大高度
    private float mTop_Height = mTop_Top + mTopMargin * 4;
    //中间折线图的顶部
    private float mCenter_Top = mTop_Height + mTextMargin;
    //中间折线图最大高度
    private float mCenter_Height = mCenter_Top + LineHeight;
    //底部控件的高度
    private float mBottom_Top = mCenter_Height + mTextMargin;
    //底部控件的最大高度
    private float mBottom_Height = mBottom_Top + mTop_Height;//和顶部高度一致
    //整个控件的宽、高度
    private int ViewWidth = mSimpleItemWidth * (itemSize + 1);
    //最高、最低温
    private int maxTemp, minTemp;

    private Paint mTextPaint;
    private Paint mLinePaint_d;
    private Paint mLinePaint_n;
    private Paint mPointPaint;
    private Paint mStrokPaint;

    private void initView() {
        mTextPaint = new Paint();
        mLinePaint_d = new Paint();
        mLinePaint_n = new Paint();
        mPointPaint = new Paint();
        mStrokPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mLinePaint_d.setAntiAlias(true);
        mLinePaint_n.setAntiAlias(true);
        mLinePaint_d.setDither(true);
        mLinePaint_n.setDither(true);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(0xEEDDDDDD);
        mStrokPaint.setAntiAlias(true);
        mStrokPaint.setStyle(Paint.Style.FILL);
        mStrokPaint.setColor(0x15000000);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    //为View添加数据
    public void initData(List<Weekinfo> datas) {
        this.datas = datas;
        itemSize = datas.size();
        //将所有的气温装一起，选出最大和最小
        List<Integer> temps = new ArrayList<>();
        for (Weekinfo info : datas) {
            temps.add(info.temp_day);
            temps.add(info.temp_night);
        }
        int wi = temps.get(0);
        maxTemp = minTemp = wi;
        for (int i = 0; i < temps.size(); i++) {
            int item = temps.get(i);
            maxTemp = Math.max(item, maxTemp);
            minTemp = Math.min(item, minTemp);
        }
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                Rect rect = new Rect(mSimpleItemWidth * i, 1, mSimpleItemWidth * (i + 1), (int) mBottom_Height);
                if (i < itemSize && i % 2 == 0) {
                    canvas.drawRect(rect, mStrokPaint);
                }
            }
            p_d_list.clear();
            p_n_list.clear();
            for (int i = 0; i < datas.size(); i++) {
                Rect rect = new Rect(mSimpleItemWidth * i, 1, mSimpleItemWidth * (i + 1), (int) mBottom_Height);
                initLocationValue(i);
                drawTop(canvas, i, rect);
                drawLineText(canvas, i, rect);
                drawBottom(canvas, i, rect);
            }
            BesselUtils.drawBessel(p_d_list, canvas, mLinePaint_d, mPointPaint);
            BesselUtils.drawBessel(p_n_list, canvas, mLinePaint_n, mPointPaint);
        }
        canvas.restore();
    }

    private void drawTop(Canvas canvas, int i, Rect rect) {
        Weekinfo info = datas.get(i);
        if (info == null)
            return;
        int itemWidth = rect.centerX();
        float simpleItem_h = (mTop_Height - mTop_Top) / 4f;
        canvas.drawText(info.week, itemWidth, simpleItem_h, mTextPaint);
        mTextPaint.setTextSize(mTextSize_m);
        mTextPaint.setColor(Color.GRAY);
        canvas.drawText(TimeUtils.getCurrentDay(info.date * 1000), itemWidth, simpleItem_h * 2, mTextPaint);
        mTextPaint.setTextSize(mTextSize_l);
        mTextPaint.setColor(i == 0 ? Color.GRAY : 0xEEFEFEFE);
        canvas.drawText(info.weather_day, itemWidth, simpleItem_h * 3, mTextPaint);
        Drawable drawable = ContextCompat.getDrawable(mContext, StaticValue.getWeatherImg(info.img_day, "12:00"));
        drawable.setBounds(rect.centerX() - drawableSize / 2,
                (int) (simpleItem_h * 3f + mTextMargin),
                rect.centerX() + drawableSize / 2,
                ((int) (simpleItem_h * 3f + drawableSize + mTextMargin)));
        drawable.draw(canvas);
    }

    /**
     * 画线的提示文字，方法内的mTopMargin为线距离上下的距离，必须统一修改
     */
    private void drawLineText(Canvas canvas, int i, Rect rect) {
        Weekinfo info = datas.get(i);
        int itemWidth = rect.centerX();
        float maxHeight = mCenter_Height - mCenter_Top - mTopMargin * 4;
        float height_d = maxHeight - (info.temp_day - minTemp) * 1.0f / (maxTemp - minTemp) * maxHeight;
        float height_n = maxHeight - (info.temp_night - minTemp) * 1.0f / (maxTemp - minTemp) * maxHeight;
        //设置起点和终点，加上边境的距离padding
        Point p_d = new Point(itemWidth, (int) (height_d + mCenter_Top + mTopMargin * 2));
        Point p_n = new Point(itemWidth, (int) (height_n + mCenter_Top + mTopMargin * 2));
        //画温度提示文字
        mTextPaint.setTextSize(mTextSize_s);
        canvas.drawText(info.temp_day + "°", p_d.x, p_d.y - mLineWidth * 2, mTextPaint);
        canvas.drawText(info.temp_night + "°", p_n.x, p_n.y + mTextSize_s + mLineWidth * 2, mTextPaint);
        //画线
        p_d_list.add(p_d.x * 1f);
        p_d_list.add(p_d.y * 1f);
        p_n_list.add(p_n.x * 1f);
        p_n_list.add(p_n.y * 1f);
    }

    //画底部
    private void drawBottom(Canvas canvas, int i, Rect rect) {
        Weekinfo info = datas.get(i);
        if (info == null)
            return;
        int itemWidth = rect.centerX();
        float simpleItem_h = (mBottom_Height - mBottom_Top - mTop_Top) / 4f;
        Drawable drawable = ContextCompat.getDrawable(mContext, StaticValue.getWeatherImg(info.img_night, "00:00"));
        int drawableTop = (int) (mBottom_Top - mTop_Top / 2);
        int drawableBottom = ((int) (drawableSize + mBottom_Top - mTop_Top / 2));
        drawable.setBounds(rect.centerX() - drawableSize / 2, drawableTop, rect.centerX() + drawableSize / 2, drawableBottom);
        drawable.draw(canvas);
        mTextPaint.setTextSize(mTextSize_l);
        canvas.drawText(info.weather_night, itemWidth, simpleItem_h + drawableBottom, mTextPaint);
        mTextPaint.setTextSize(mTextSize_m);
        mTextPaint.setColor(Color.GRAY);
        canvas.drawText(info.wind_dir, itemWidth, simpleItem_h * 2 + drawableBottom, mTextPaint);
        canvas.drawText(info.wind_power, itemWidth, simpleItem_h * 3 + drawableBottom, mTextPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ViewWidth, (int) mBottom_Height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //初始化画笔信息
    private void initLocationValue(int i) {
        mTextPaint.setColor(i == 0 ? Color.GRAY : 0xEEFEFEFE);
        mTextPaint.setTextSize(mTextSize_l);
        mLinePaint_d.setColor(i == 0 ? mColorDay_dark : mColorDay);
        mLinePaint_d.setStyle(Paint.Style.STROKE);
        mLinePaint_d.setStrokeWidth(3);
        mLinePaint_n.setColor(i == 0 ? mColorNight_dark : mColorNight);
        mLinePaint_n.setStrokeWidth(3);
        mLinePaint_n.setStyle(Paint.Style.STROKE);
    }
}
