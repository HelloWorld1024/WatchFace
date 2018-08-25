package com.tnt.watchhome.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tnt.watchhome.R;

public class BatteryView extends View {
    private static final String TAG = "BatteryView";

    private int mMargin = 3;    //电池内芯与边框的距离
    private int mBoder = 2;     //电池外框的宽带
    private int mWidth = 70;    //总长
    private int mHeight = 40;   //总高
    private int mHeadWidth = 0;//6;
    private int mHeadHeight = 0;//10;

    private RectF mMainRect;
    private RectF mHeadRect;
    private float mRadius = 4f;   //圆角
    private float mPower;

    private boolean mIsCharging; //是否在充电

    private boolean mIsVertical =true;


    private void initView() {
        float left , top , right , bottom ;
        if (!mIsVertical) {
            mHeadRect = new RectF(0, (mHeight - mHeadHeight)/2, mHeadWidth, (mHeight + mHeadHeight)/2);
            left = mHeadRect.width();
            top = mBoder;
            right = mWidth-mBoder;
            bottom = mHeight-mBoder;
            mMainRect = new RectF(left, top, right, bottom);
        }else {
            mHeight = 25 ;//电池高度
            mWidth = 15 ;
            mHeadWidth = 8 ;
            mHeadHeight = 3 ;
            mHeadRect = new RectF((mWidth-mHeadWidth)/2,0,(mWidth+mHeadWidth)/2,mHeadHeight) ;

            left = 0 ;
            top = mHeadHeight;
            right = mWidth ;
            bottom = mHeadRect.bottom+mHeight ;

            mMainRect = new RectF(left,top,right,bottom) ;
        }

    }


    public BatteryView(Context context) {
        super(context);
        init(null, 0);
        initView() ;
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        initView() ;
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        initView() ;
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.BatteryView, defStyle, 0);


        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint1 = new Paint();

        //画电池头
        paint1.setStyle(Paint.Style.FILL);  //实心
        paint1.setColor(Color.WHITE);
        canvas.drawRect(mHeadRect, paint1);

        //画外框
        canvas.save() ;
        paint1.setStyle(Paint.Style.STROKE);    //设置空心矩形
        paint1.setStrokeWidth(mBoder);          //设置边框宽度
        paint1.setColor(Color.RED);
        //canvas.drawRoundRect(mMainRect, mRadius, mRadius, paint1);
        canvas.drawRect(mMainRect,paint1) ;
        canvas.restore();


        paint1.setStyle(Paint.Style.STROKE);    //设置空心矩形
        paint1.setStrokeWidth(mBoder);          //设置边框宽度
        paint1.setColor(Color.GREEN);
        canvas.drawCircle(mWidth,mHeight,30,paint1);

        //画电池芯
        Paint paint = new Paint();
        if (mIsCharging) {
            paint.setColor(Color.GREEN);
        } else {
            if (mPower < 0.2) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.WHITE);
            }
        }

        int width , left,right,top,bottom ,height ;
        Rect rect ;
        if (!mIsVertical) {
            width   = (int) (mPower * (mMainRect.width() - mMargin*2));
            left    = (int) (mMainRect.right - mMargin - width);
            right   = (int) (mMainRect.right - mMargin);
            top     = (int) (mMainRect.top + mMargin);
            bottom  = (int) (mMainRect.bottom - mMargin);
            rect = new Rect(left,top,right, bottom);
        }else {
            height = (int)(mPower*(mMainRect.height()-mMargin*2));
            left = (int)(mMainRect.left + mMargin);

            top = (int)(mMainRect.bottom-mMargin - mPower*(mMainRect.height()-mMargin*2));
            right = (int)(mMainRect.right - mMargin) ;
            bottom = (int)(mMainRect.bottom-mMargin-mBoder);
            rect = new Rect(left,top,right,bottom) ;

            Log.i(TAG,"mMainRect left = "+mMainRect.left + "  top = "+mMainRect.top
            +"right = "+mMainRect.right + "  bottom="+mMainRect.bottom) ;



            Log.i(TAG,"rect = left"+rect.left + "top = "+rect.top +" right = "+rect.right+"  bottom = "+rect.bottom) ;

        }
        canvas.drawRect(rect, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth,mHeight);
    }

    private void setPower(float power) {
        mPower = power;
        invalidate();
    }

    private BroadcastReceiver mPowerConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            mIsCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            setPower(((float) level)/scale);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        getContext().registerReceiver(mPowerConnectionReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        getContext().unregisterReceiver(mPowerConnectionReceiver);
        super.onDetachedFromWindow();
    }




}
