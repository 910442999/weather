package com.yc.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.yc.weather.R;
import com.yc.weather.bean.HourItem;
import com.yc.weather.utils.DisplayUtils;
import com.yc.weather.view.bessel.BesselUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojie on 2017/4/17.
 */
public class TodayView extends View {
    private int ITEM_SIZE = 24;  //24小时
    private static final int tipsWidth = DisplayUtils.dip2px(0);
    private static final int ITEM_WIDTH = DisplayUtils.dip2px(50); //每个Item的宽度
    private static final int MARGIN_LEFT_ITEM = DisplayUtils.dip2px(8) + tipsWidth; //左边预留宽度
    private static final int MARGIN_RIGHT_ITEM = MARGIN_LEFT_ITEM; //右边预留宽度

    private static final int windyBoxAlpha = 80;
    private static final int bottomTextHeight = DisplayUtils.dip2px(20);
    private int mHeight, mWidth;
    private int tempBaseTop;  //温度折线的上边Y坐标
    private int tempBaseBottom; //温度折线的下边Y坐标
    private Paint bitmapPaint, windyBoxPaint, linePaint, pointPaint, dashLinePaint;
    private TextPaint textPaint;
    //边距，包括左边及右边，传入类型为Dip，用于调节跟随距离;
    private int mMargin = 41;
    //点的大小
    private int pointSize = DisplayUtils.dip2px(3);

    private List<HourItem> listItems = new ArrayList<>();
    private int maxScrollOffset = 0;//滚动条最长滚动距离
    private int scrollOffset = 0; //滚动条偏移量
    private int currentItemIndex = 0; //当前滚动的位置所对应的item下标
    private int currentWeatherRes = -1;
    private int curentIcon;
    private int maxTemp;
    private int minTemp;
    private int maxWindy;
    private int minWindy;
    private int baseline;
    private upDataLeft upDataLeft;
    private Context mContext;

    public TodayView(Context context) {
        this(context, null);
    }

    public TodayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TodayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setHourItem(List<HourItem> items) {
        if (items == null || items.size() == 0) {
            Toast.makeText(mContext, "获取24小时天气失败，请尝试刷新以重新获得", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listItems = items;
        ITEM_SIZE = items.size();
        //选出最大最小温度及风力
        HourItem hi = items.get(0);
        maxTemp = minTemp = hi.temperature;
        for (int i = 0; i < ITEM_SIZE; i++) {
            HourItem item = listItems.get(i);
            maxTemp = Math.max(item.temperature, maxTemp);
            minTemp = Math.min(item.temperature, minTemp);
        }
        //开始设置数据
        for (int i = 0; i < ITEM_SIZE; i++) {
            HourItem item = listItems.get(i);
            int left = MARGIN_LEFT_ITEM + i * ITEM_WIDTH;
            int right = left + ITEM_WIDTH - 3;
            int top = (mHeight - bottomTextHeight - DisplayUtils.dip2px(20));
            int bottom = mHeight - bottomTextHeight;
            Rect rect = new Rect(left, top, right, bottom);
            PointF point = calculateTempPoint(left, right, item.temperature);
            item.windyBoxRect = rect;
            item.tempPoint = point;
        }
        postInvalidate();
    }

    private void init() {
        mWidth = MARGIN_LEFT_ITEM + MARGIN_RIGHT_ITEM + ITEM_SIZE * ITEM_WIDTH;
        mHeight = DisplayUtils.dip2px(getContext(), 180); //暂时先写死
        tempBaseTop = (mHeight - bottomTextHeight) / 4;
        tempBaseBottom = (mHeight - bottomTextHeight) * 2 / 3;
        initPaint();
    }

    private void initPaint() {
        pointPaint = new Paint();
        pointPaint.setColor(0xfffcbe32);
        pointPaint.setAntiAlias(true);
        pointPaint.setTextSize(DisplayUtils.dip2px(getContext(), 4));

        linePaint = new Paint();
        linePaint.setColor(new Color().WHITE);
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);

        dashLinePaint = new Paint();
        dashLinePaint.setColor(new Color().WHITE);
        PathEffect effect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        dashLinePaint.setPathEffect(effect);
        dashLinePaint.setStrokeWidth(1);
        dashLinePaint.setAntiAlias(true);
        dashLinePaint.setStyle(Paint.Style.STROKE);

        windyBoxPaint = new Paint();
        windyBoxPaint.setTextSize(1);
        windyBoxPaint.setColor(0xaaffffff);
        windyBoxPaint.setAlpha(windyBoxAlpha);
        windyBoxPaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setTextSize(DisplayUtils.dip2px(getContext(), 12));
        textPaint.setColor(new Color().WHITE);
        textPaint.setAntiAlias(true);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
    }

    private PointF calculateTempPoint(int left, int right, int temp) {
        float minHeight = tempBaseTop;
        float maxHeight = tempBaseBottom;
        float tempY = maxHeight - (temp - minTemp) * 1.0f / (maxTemp - minTemp) * (maxHeight - minHeight);
        PointF point = new PointF((left + right) / 2f, tempY);
        return point;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        List<Float> tempsPoints = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++) {
            HourItem item = listItems.get(i);
            Rect rect = item.windyBoxRect;
            PointF point = item.tempPoint;
            //画风力的box和提示文字
            onDrawBox(canvas, rect, i);
            //画表示天气图片
            int res = item.res;
            if (res != -1 && i != currentItemIndex && res != curentIcon) {
                curentIcon = res;
                Drawable drawable = ContextCompat.getDrawable(getContext(), res);
                drawable.setBounds((int) point.x - DisplayUtils.dip2px(getContext(), 10),
                        (int) point.y - DisplayUtils.dip2px(getContext(), 25),
                        (int) point.x + DisplayUtils.dip2px(getContext(), 10),
                        (int) point.y - DisplayUtils.dip2px(getContext(), 5));
                drawable.draw(canvas);
            }
            onDrawText(canvas, i);
            tempsPoints.add(item.tempPoint.x * 1.0f);
            tempsPoints.add(item.tempPoint.y * 1.0f);
        }
        onDrawLine(tempsPoints, canvas);
        //底部水平的白线
        //        linePaint.setColor(new Color().WHITE);
        //        canvas.drawLine(0, mHeight - bottomTextHeight, mWidth, mHeight - bottomTextHeight, linePaint);
        //        中间温度的虚线
        Path path1 = new Path();
        path1.moveTo(MARGIN_LEFT_ITEM, tempBaseTop);
        path1.quadTo(mWidth - MARGIN_RIGHT_ITEM, tempBaseTop, mWidth - MARGIN_RIGHT_ITEM, tempBaseTop);
        canvas.drawPath(path1, dashLinePaint);
        Path path2 = new Path();
        path2.moveTo(MARGIN_LEFT_ITEM, tempBaseBottom);
        path2.quadTo(mWidth - MARGIN_RIGHT_ITEM, tempBaseBottom, mWidth - MARGIN_RIGHT_ITEM, tempBaseBottom);
        canvas.drawPath(path2, dashLinePaint);
        for (int i = 0; i < listItems.size(); i++) {
            //画温度的点
            onDrawTemp(canvas, i);
        }
        //画左边的文字
        drawLeftTempText();
        canvas.restore();
    }

    private void onDrawTemp(Canvas canvas, int i) {
        HourItem item = listItems.get(i);
        if (currentItemIndex == i) {
            //计算提示文字的运动轨迹
            int Y = (int) getTempBarY();
            //画出背景图片
            int top = Y - DisplayUtils.dip2px(26);
            int bottom = Y - DisplayUtils.dip2px(8);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.hour_24_float);
            drawable.setBounds(getScrollBarX(),
                    Y - DisplayUtils.dip2px(28),
                    getScrollBarX() + ITEM_WIDTH,
                    Y - DisplayUtils.dip2px(4));
            drawable.draw(canvas);
            //画天气
            int res = findCurrentRes(i);
            if (res != -1) {
                Drawable drawTemp = ContextCompat.getDrawable(getContext(), res);
                drawTemp.setBounds(getScrollBarX() + ITEM_WIDTH / 2 + (ITEM_WIDTH / 2 - DisplayUtils.dip2px(getContext(), 18)) / 2,
                        top,
                        getScrollBarX() + ITEM_WIDTH - (ITEM_WIDTH / 2 - DisplayUtils.dip2px(getContext(), 18)) / 2,
                        bottom);
                drawTemp.draw(canvas);

            }
            //画出温度提示
            int offset = ITEM_WIDTH / 2;
            if (res == -1)
                offset = ITEM_WIDTH;
            Rect targetRect = new Rect(getScrollBarX(), top
                    , getScrollBarX() + offset, bottom);
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(item.temperature + "°", targetRect.centerX(), baseline, textPaint);
        }
    }

    private int findCurrentRes(int i) {
        if (listItems.get(i).res != -1)
            return listItems.get(i).res;
        for (int k = i; k >= 0; k--) {
            if (listItems.get(k).res != -1)
                return listItems.get(k).res;
        }
        return -1;
    }

    //画底部风力的BOX
    private void onDrawBox(final Canvas canvas, final Rect rect, int i) {
        canvas.save();
        // 新建一个矩形
        RectF boxRect = new RectF(rect);
        HourItem item = listItems.get(i);
        //是否在当前时间选中时改变样式
        if (i == currentItemIndex) {
            windyBoxPaint.setAlpha(windyBoxAlpha + 70);
            canvas.drawRoundRect(boxRect, 8, 8, windyBoxPaint);
        } else {
            windyBoxPaint.setAlpha(windyBoxAlpha);
            canvas.drawRoundRect(boxRect, 8, 8, windyBoxPaint);
        }
        //画出box上面的风力提示文字
        //文字跟随
        //        Rect targetRect = new Rect(getScrollBarX(), rect.top, getScrollBarX() + ITEM_WIDTH, rect.top + DisplayUtils.dip2px(20));
        //文字始终存在
        Rect targetRect = rect;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(TextUtils.isEmpty(item.windy) ? "0级" : item.windy, targetRect.centerX(), baseline, textPaint);
        canvas.restore();
    }

    //温度的折线,为了看着平滑，加了贝塞尔曲线
    private void onDrawLine(List<Float> points, Canvas canvas) {
        linePaint.setColor(new Color().WHITE);
        linePaint.setStrokeWidth(4);
        BesselUtils.drawBessel(points, canvas, linePaint, pointPaint);
    }

    //最下面的当前时间
    private void onDrawText(Canvas canvas, int i) {
        //文字居中
        Rect rect = listItems.get(i).windyBoxRect;
        Rect targetRect = new Rect(rect.left, rect.bottom, rect.right, rect.bottom + bottomTextHeight);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        textPaint.setTextAlign(Paint.Align.CENTER);
        String text = listItems.get(i).time;
        canvas.drawText(text, targetRect.centerX(), baseline, textPaint);
    }


    public void drawLeftTempText() {
        //        画最左侧的文字
        //        textPaint.setTextAlign(Paint.Align.LEFT);
        //        canvas.drawText(maxTemp + "°", scrollOffset, tempBaseTop, textPaint);
        //        canvas.drawText(minTemp + "°", scrollOffset, tempBaseBottom, textPaint);
        //        canvas.drawText("风力", scrollOffset, baseline, textPaint);
        if (upDataLeft != null) {
            upDataLeft.onUpdata(maxTemp, minTemp, tempBaseTop, tempBaseBottom, baseline);
        }
    }

    //设置scrollerView的滚动条的位置，通过位置计算当前的时段
    public void setScrollOffset(int offset, int maxScrollOffset) {
        this.maxScrollOffset = maxScrollOffset + DisplayUtils.dip2px(getContext(), mMargin);//+是否加上padding或margin值
        this.scrollOffset = offset;
        int index = calculateItemIndex(offset);
        currentItemIndex = index;
        invalidate();
    }

    //通过滚动条偏移量计算当前选择的时刻
    private int calculateItemIndex(int offset) {
        //        Log.d(TAG, "maxScrollOffset = " + maxScrollOffset + "  scrollOffset = " + scrollOffset);
        int x = getScrollBarX();
        int sum = MARGIN_LEFT_ITEM - ITEM_WIDTH / 2;
        for (int i = 0; i < ITEM_SIZE; i++) {
            sum += ITEM_WIDTH;
            if (x < sum)
                return i;
        }
        return ITEM_SIZE - 1;
    }

    private int getScrollBarX() {
        int x = (ITEM_SIZE - 1) * ITEM_WIDTH * scrollOffset / (maxScrollOffset == 0 ? 1 : maxScrollOffset);
        x = x + MARGIN_LEFT_ITEM;
        return x;
    }

    //计算温度提示文字的运动轨迹
    private float getTempBarY() {
        int x = getScrollBarX();
        int sum = MARGIN_LEFT_ITEM;
        PointF startPoint = null, endPoint;
        int i;
        for (i = 0; i < ITEM_SIZE; i++) {
            sum += ITEM_WIDTH;
            if (x < sum) {
                startPoint = listItems.get(i).tempPoint;
                break;
            }
        }
        if (i + 1 >= ITEM_SIZE || startPoint == null)
            return listItems.get(ITEM_SIZE - 1).tempPoint.y;
        endPoint = listItems.get(i + 1).tempPoint;

        Rect rect = listItems.get(i).windyBoxRect;
        int y = (int) (startPoint.y + (x - rect.left) * 1.0 / ITEM_WIDTH * (endPoint.y - startPoint.y));
        return y;
    }

    public interface upDataLeft {
        void onUpdata(int maxTemp, int minTemp, int tempBaseTop, int tempBaseBottom, int baseline);
    }

    public void setUpDataLeft(upDataLeft dataLeft) {
        this.upDataLeft = dataLeft;
    }
}