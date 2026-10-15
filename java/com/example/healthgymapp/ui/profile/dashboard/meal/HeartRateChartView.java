package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class HeartRateChartView extends View {

    private Paint linePaint, pointPaint, fillPaint, gridPaint;
    private float[] currentPoints = {
            50f, 120f, 90f, 100f, 130f, 110f, 170f, 115f, 210f, 85f, 250f, 95f, 290f, 60f, 330f, 110f, 370f, 90f
    };

    public HeartRateChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#059669"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4f);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.parseColor("#059669"));
        pointPaint.setStyle(Paint.Style.FILL);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.parseColor("#E6F4EA"));
        fillPaint.setStyle(Paint.Style.FILL);

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#E2E8F0"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1.5f);
        gridPaint.setPathEffect(new android.graphics.DashPathEffect(new float[]{10f, 10f}, 0f));
    }

    // Hàm cập nhật dữ liệu khi đổi tab
    public void setData(float[] points) {
        this.currentPoints = points;
        invalidate(); // Vẽ lại biểu đồ
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) return;

        // Vẽ các đường lưới ngang mờ
        for (int i = 1; i <= 3; i++) {
            float y = (height / 4f) * i;
            canvas.drawLine(40f, y, width - 20f, y, gridPaint);
        }

        float[] scaledPoints = new float[currentPoints.length];
        float maxVal = 180f;

        for (int i = 0; i < currentPoints.length; i += 2) {
            scaledPoints[i] = 40f + (currentPoints[i] / 400f) * (width - 60f);
            scaledPoints[i + 1] = height - (currentPoints[i + 1] / maxVal) * (height - 30f) - 10f;
        }

        // Vẽ vùng mờ phía dưới đường biểu đồ
        Path fillPath = new Path();
        fillPath.moveTo(scaledPoints[0], height - 10f);
        for (int i = 0; i < scaledPoints.length; i += 2) {
            fillPath.lineTo(scaledPoints[i], scaledPoints[i + 1]);
        }
        fillPath.lineTo(scaledPoints[scaledPoints.length - 2], height - 10f);
        fillPath.close();
        canvas.drawPath(fillPath, fillPaint);

        // Vẽ đường gấp khúc chính
        Path linePath = new Path();
        linePath.moveTo(scaledPoints[0], scaledPoints[1]);
        for (int i = 2; i < scaledPoints.length; i += 2) {
            linePath.lineTo(scaledPoints[i], scaledPoints[i + 1]);
        }
        canvas.drawPath(linePath, linePaint);

        // Vẽ các chấm tròn tại mỗi điểm
        for (int i = 0; i < scaledPoints.length; i += 2) {
            canvas.drawCircle(scaledPoints[i], scaledPoints[i + 1], 5f, pointPaint);
            Paint innerWhite = new Paint(pointPaint);
            innerWhite.setColor(Color.WHITE);
            canvas.drawCircle(scaledPoints[i], scaledPoints[i + 1], 2f, innerWhite);
        }
    }
}