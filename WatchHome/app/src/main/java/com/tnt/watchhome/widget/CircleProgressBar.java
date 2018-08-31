package com.tnt.watchhome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.tnt.watchhome.R;

/**
 * TODO: document your custom view class.
 */
public class CircleProgressBar extends View {

    private static final String TAG = "CircleProgressBar" ;

    private int mCenterX ;
    private int mCenterY ;
    private int mWidth ;
    private int mHeight ;

    private int mBackgroundStrokeWidth = 5 ;
    private float mStartAngle = 240 ;


    public CircleProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleProgressBar, defStyle, 0);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawArc()
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension() ;
    }
}
