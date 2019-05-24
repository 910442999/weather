package com.yc.weather.view.bessel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.List;

/**
 * Created by zhaojie on 2017/8/25.
 */

public class BesselUtils {

    /**
     * 计算控制点
     *
     * @param points
     * @param result
     * @param index1
     * @param index2
     * @param multiplier
     */
    private static void calc(List<Float> points, Point result, int index1, int index2, final float multiplier) {
        float p1x = points.get(index1);
        float p1y = points.get(index1 + 1);
        float p2x = points.get(index2);
        float p2y = points.get(index2 + 1);
        float diffX = p2x - p1x;
        float diffY = p2y - p1y;
        result.setX(p1x + (diffX * multiplier));
        result.setY(p1y + (diffY * multiplier));
    }

    /**
     * 重新设置点的位置，为曲线上的位置
     *
     * @param mPathMeasure
     * @param pointsList
     */
    private static void reSetPointWithPath(PathMeasure mPathMeasure, List<Float> pointsList) {
        int length = (int) mPathMeasure.getLength();
        int pointsLength = pointsList.size();
        float[] coords = new float[2];
        for (int b = 0; b < length; b++) {
            mPathMeasure.getPosTan(b, coords, null);
            double prevDiff = Double.MAX_VALUE;
            boolean ok = true;
            for (int j = 0; j < pointsLength && ok; j += 2) {
                double diff = Math.abs(pointsList.get(j) - coords[0]);
                if (diff < 1) {
                    pointsList.set(j + 1, coords[1]);
                    prevDiff = diff;
                }
                ok = prevDiff > diff;
            }
        }
    }

    public static void drawBessel(List<Float> points, Canvas canvas, Paint linePaint, Paint pointPaint) {
        // 贝塞尔曲线
        Path p = new Path();
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        float xp = points.get(0);
        float yp = points.get(1);
        // 设置第一个点开始
        p.moveTo(xp, yp);
        int length = points.size();
        // 设置第一个控制点33%的距离
        float mFirstMultiplier = 0.3f;
        // 设置第二个控制点为66%的距离
        float mSecondMultiplier = 1 - mFirstMultiplier;

        for (int b = 0; b < length; b += 2) {
            int nextIndex = b + 2 < length ? b + 2 : b;
            int nextNextIndex = b + 4 < length ? b + 4 : nextIndex;
            // 设置第一个控制点
            calc(points, p1, b, nextIndex, mSecondMultiplier);
            // 设置第二个控制点
            p2.setX(points.get(nextIndex));
            p2.setY(points.get(nextIndex + 1));
            // 设置第三个控制点
            calc(points, p3, nextIndex, nextNextIndex, mFirstMultiplier);
            // 最后一个点就是赛贝尔曲线上的点
            p.cubicTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
            // 画点
        }
        PathMeasure mPathMeasure;
        mPathMeasure = new PathMeasure(p, false);
        // 设置为线
        reSetPointWithPath(mPathMeasure, points);
        canvas.drawPath(p, linePaint);
        if (pointPaint != null)
            for (int k = 0; k < points.size() - 1; k += 2) {
                canvas.drawCircle(points.get(k), points.get(k + 1), 5, pointPaint);
            }
    }
}