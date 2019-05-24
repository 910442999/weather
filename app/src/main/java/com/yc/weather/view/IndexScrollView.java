package com.yc.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.yc.weather.utils.DisplayUtils;


/**
 * Created by zhaojie on 2017/4/17.
 */
public class IndexScrollView extends HorizontalScrollView {

    private TodayView todayView;
    private Context c;

    public IndexScrollView(Context context) {
        this(context, null);
    }

    public IndexScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offset = computeHorizontalScrollOffset();
        int maxOffset = computeHorizontalScrollRange() - DisplayUtils.getScreenWidth(getContext().getApplicationContext());
        if (todayView != null) {
            todayView.setScrollOffset(offset, maxOffset);
        }
    }

    public void setTodayView(TodayView todayView) {
        this.todayView = todayView;
    }
}
