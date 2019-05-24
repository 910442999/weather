package com.yc.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yc.weather.utils.DisplayUtils;


/**
 * Created by zhaojie on 2017/6/15.
 */

public class SecondTipsView extends View {

    private Paint txt_paint;
    private int bottom1 = DisplayUtils.dip2px(180), top, bottom;
    private int maxTemp, minTemp;
    private int width = DisplayUtils.dip2px(25);

    public SecondTipsView(Context context) {
        this(context, null, 0);
    }

    public SecondTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        txt_paint = new Paint();
        txt_paint.setAntiAlias(true);
        txt_paint.setColor(Color.WHITE);
        txt_paint.setTextAlign(Paint.Align.CENTER);
        txt_paint.setTextSize(DisplayUtils.dip2px(12));
    }

    public void setData(int top, int bottom, int bottom1, int maxTemp, int minTemp) {
        this.bottom1 = bottom1;
        this.top = top;
        this.bottom = bottom;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawText(maxTemp + "°", width / 2f, top, txt_paint);
        canvas.drawText(minTemp + "°", width / 2f, bottom, txt_paint);
        canvas.drawText("风力", width / 2f, bottom1, txt_paint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, bottom1 + (int) (txt_paint.measureText("风")));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
