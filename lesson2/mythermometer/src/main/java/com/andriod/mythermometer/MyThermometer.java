package com.andriod.mythermometer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyThermometer extends View {
    private final int MAX_TEMPERATURE = 50;
    private final int MIN_TEMPERATURE = -50;

    private final int TEMPERATURE_OFFSET_Y = 5;
    private final int TEMPERATURE_OFFSET_X = 2;

    private final int BAR_WIDTH = 30;
    private final int BAR_OFFSET_X = 30;
    private final float BAR_PERCENT = 0.8f;

    private final int SCALE_OFFSET_X = 10;
    private final int SCALE_WIDTH = 20;


    private int height;
    private int width;
    private int barHeight;
    private int barWidth;

    private int colorBackground;
    private int colorScale;
    private int colorBar;
    private int colorHot;
    private int colorCold;
    private int colorTemperature;
    private float value = 0;

    private Color hot;
    private Color cold;

    private Paint paintBackground;
    private Paint paintScale;
    private Paint paintBar;
    private Paint paintTemperature;

    private final RectF rectBody = new RectF();
    private final Rect rectBar = new Rect();
    private final Rect rectTemperature = new Rect();

    private float lineX;
    private final float[] linesY = new float[5];

    private float changingValue = 5;

    public MyThermometer(Context context) {
        super(context);

        initAttr(context, null, 0, 0);
        init();
    }

    public MyThermometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs, 0, 0);
        init();
    }

    public MyThermometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs, defStyleAttr, 0);
        init();
    }

    public MyThermometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttr(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyThermometer, defStyleAttr, defStyleRes);

        value = typedArray.getFloat(R.styleable.MyThermometer_term_value, 0);
        colorBackground = typedArray.getColor(R.styleable.MyThermometer_term_color_background, Color.WHITE);
        colorScale = typedArray.getColor(R.styleable.MyThermometer_term_color_scale, Color.GRAY);
        colorBar = typedArray.getColor(R.styleable.MyThermometer_term_color_bar, Color.GRAY);
        colorHot = typedArray.getColor(R.styleable.MyThermometer_term_color_hot, Color.RED);
        colorCold = typedArray.getColor(R.styleable.MyThermometer_term_color_cold, Color.BLUE);

        typedArray.recycle();
    }

    private void init() {
        paintBackground = new Paint();
        paintBackground.setColor(colorBackground);
        paintBackground.setStyle(Paint.Style.FILL);

        paintScale = new Paint();
        paintScale.setColor(colorScale);
        paintScale.setStyle(Paint.Style.FILL);

        paintBar = new Paint();
        paintBar.setColor(colorBar);
        paintBar.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w - getPaddingStart() - getPaddingEnd();
        height = h - getPaddingTop() - getPaddingBottom();

        barHeight = (int) (height * BAR_PERCENT);
        barWidth = BAR_WIDTH;

        int offset = (height - barHeight) / 2;

        rectBody.set(
                getPaddingStart(),
                getPaddingTop(),
                getPaddingStart() + width,
                getPaddingTop() + height);

        rectBar.set(
                (int) rectBody.left + BAR_OFFSET_X,
                (int) rectBody.top + offset,
                (int) rectBody.left + BAR_OFFSET_X + barWidth,
                (int) rectBody.top + offset + barHeight);

        lineX = rectBar.right + SCALE_OFFSET_X;

        float scaleStep = (rectBar.bottom - rectBar.top - 2 * TEMPERATURE_OFFSET_Y) / (float) (linesY.length - 1);
        for (int i = 0; i < linesY.length; i++)
            linesY[i] = (int) (rectBar.bottom - TEMPERATURE_OFFSET_Y - scaleStep * i);

        changeTemperature();
    }

    private void changeTemperature() {
        if (value > MAX_TEMPERATURE) value = MAX_TEMPERATURE;
        if (value < MIN_TEMPERATURE) value = MIN_TEMPERATURE;

        float percent = (value - MIN_TEMPERATURE) / (MAX_TEMPERATURE - MIN_TEMPERATURE);
        colorTemperature = getTemperatureColor(percent);

        paintTemperature = new Paint();
        paintTemperature.setColor(colorTemperature);
        paintTemperature.setStyle(Paint.Style.FILL);

        int temperatureHeight = (int) ((barHeight - TEMPERATURE_OFFSET_Y * 2) * percent);
        rectTemperature.set(
                rectBar.left + TEMPERATURE_OFFSET_X,
                rectBar.bottom - TEMPERATURE_OFFSET_Y - temperatureHeight,
                rectBar.right - TEMPERATURE_OFFSET_X,
                rectBar.bottom - TEMPERATURE_OFFSET_Y);
    }

    private int getTemperatureColor(float percent) {
//      int color = (int) (colorHot * percent + colorCold * (1 - percent));

        int red = (int) (Color.red(colorHot) * percent + Color.red(colorCold) * (1 - percent));
        int green = (int) (Color.green(colorHot) * percent + Color.green(colorCold) * (1 - percent));
        int blue = (int) (Color.blue(colorHot) * percent + Color.blue(colorCold) * (1 - percent));

        return Color.rgb(red, green, blue);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(rectBody, paintBackground);

        canvas.drawRect(rectBar, paintBar);
        canvas.drawRect(rectTemperature, paintTemperature);

        for (int i = 0; i < linesY.length; i++)
            canvas.drawLine(lineX, linesY[i], lineX + SCALE_WIDTH, linesY[i], paintScale);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int Action = event.getAction();

        if (Action == MotionEvent.ACTION_DOWN) {

            value += changingValue;
            if (value >= MAX_TEMPERATURE || value <= MIN_TEMPERATURE)
                changingValue = -changingValue;

            changeTemperature();

            invalidate();

            return true;
        }
        return false;
    }
}
