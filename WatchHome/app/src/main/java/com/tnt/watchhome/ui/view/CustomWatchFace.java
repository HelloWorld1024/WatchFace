package com.tnt.watchhome.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.tnt.watchhome.R;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * TODO: document your custom view class.
 */
public class CustomWatchFace extends View {
    private static final  String TAG = "watchface";
    private static final int HANDLER_EVENT_UPDATE_TIME = 1;
    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);


    private Drawable mDial ;
    private Drawable mHourHand ;
    private Drawable mMinHand ;
    private Drawable mSecondHand ;
    private Drawable mWatchBlackPoint ;

    private Bitmap mBackgroundBitmap;

    private float mHours ;
    private float mMinutes ;
    private float mSeconds ;
    private String mDay ;
    private String mWeek ;
    private String mDate ;

    private int mTextColor ;
    private float mTextStokeWidth ;

    private float mHourRotateDegrees ;
    private float mMinuRotateDegrees ;
    private float mSecondRotateDegrees ;

    private Calendar mCalendar ;


    private int mWidth;
    private int mHeight;
    private float mScale = 1;
    private float mRadius ;


    private Paint mBackgroundPaint ;
    private Paint mTextPaint ;

    private Runnable mTicker  ;
    private Boolean mTickerStop ;

    private  UpdateTimeHandler mUpdateTimeHandler ;


    public CustomWatchFace(Context context) {
        super(context);
        initAttrs(context,null,0,0);
    }

    public CustomWatchFace(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs,0,0);
        initValue();
    }

    public CustomWatchFace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs,0,0);
        initValue();
    }

    public CustomWatchFace(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context,attrs,0,0);
        initValue() ;
    }

    private void initAttrs(Context context,AttributeSet attrs,int defStyleAttr,int defStyleRes) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomeWatch);
        mDial = typedArray.getDrawable(R.styleable.CustomeWatch_dial);
        mHourHand = typedArray.getDrawable(R.styleable.CustomeWatch_hourHand);
        mMinHand = typedArray.getDrawable(R.styleable.CustomeWatch_minteHand);
        mSecondHand = typedArray.getDrawable(R.styleable.CustomeWatch_secondHand);
        mWatchBlackPoint = typedArray.getDrawable(R.styleable.CustomeWatch_blackPonit);

        if (null == mDial || null == mHourHand || null == mMinHand || null == mSecondHand
                || null == mWatchBlackPoint) {
            Resources localRes = context.getResources() ;
            mDial = localRes.getDrawable(R.drawable.bg_c_watcherboard,null) ;
            mHourHand = localRes.getDrawable(R.drawable.bg_c_hour,null);
            mMinHand = localRes.getDrawable(R.drawable.bg_c_minute,null);
            mSecondHand = localRes.getDrawable(R.drawable.bg_c_second,null);
            mWatchBlackPoint = localRes.getDrawable(R.drawable.bg_c_watch_blackpoint,null);

            mBackgroundBitmap= BitmapFactory.decodeResource(localRes, R.drawable.bg_c_watcherboard);
        }
        mTextColor = typedArray.getColor(R.styleable.CustomeWatch_textcolor,getResources().getColor(R.color.colorGolen,null));
        mTextStokeWidth = typedArray.getDimension(R.styleable.CustomeWatch_textsize,18f);
        typedArray.recycle();

    }

    private void initValue(){
        mWidth=getScreenWidth()[0];
        mHeight=getScreenWidth()[1];
        mRadius=Math.min(mWidth/2,mHeight/2);
        mScale = ((float) mWidth)/(float)mBackgroundBitmap.getWidth();
        Log.i(TAG,"initValue width = "+mWidth + "height = "+mHeight+"  mRadius = "+mRadius+" mScale = "+mScale) ;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.BLACK);
        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.golden,null));
        mCalendar = Calendar.getInstance() ;
        mUpdateTimeHandler = new UpdateTimeHandler(this);



    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG,"onAttachedToWindow") ;
        mBackgroundBitmap = Bitmap.createScaledBitmap(mBackgroundBitmap,
                (int) (mBackgroundBitmap.getWidth() * mScale),
                (int) (mBackgroundBitmap.getHeight() * mScale),
                true);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Log.i(TAG,"onMeasure") ;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec) ;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) ;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int desiredWidth, desiredHeight ;
        if(widthMode == MeasureSpec.EXACTLY){
            desiredWidth = widthSize ;
        }else {
            desiredWidth = (int)mRadius * 2 + getPaddingLeft()+getPaddingRight() ;
            if (widthMode == MeasureSpec.AT_MOST){
                desiredWidth = Math.min(widthSize,desiredWidth) ;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY){
            desiredHeight = heightSize ;
        }else {
            desiredHeight = (int)mRadius*2 + getPaddingTop() + getPaddingBottom() ;
            if (heightMode == MeasureSpec.AT_MOST) {
                desiredHeight = Math.min(heightSize,desiredHeight) ;
            }
        }

        setMeasuredDimension(mWidth=desiredWidth, mHeight=desiredHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.i(TAG,"onSizeChanged w = "+w + "h = "+h + "oldw = "+oldw + "oldh ="+oldh) ;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility==VISIBLE){
            mCalendar.setTimeZone(TimeZone.getDefault());
        }
        updateTimer();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.i(TAG,"onDraw") ;

        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mBackgroundPaint);
        drawDateWeek(canvas);

       // canvas.rotate(mHour / 12.0F * 360.0F, mWidth/2, mHeight/2);
        drawHand(canvas) ;


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mUpdateTimeHandler.removeMessages(HANDLER_EVENT_UPDATE_TIME);
    }

    private void drawDateWeek(Canvas canvas){
        canvas.save();
        //set text style
        Typeface font = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
        mTextPaint.setTypeface(font);
        mTextPaint.setTextSkewX(-0.5f);
        mTextPaint.setColor(mTextColor);

        float len = mTextPaint.measureText(mDate);
        canvas.drawText(mDate,mWidth/2- (int)(len/2),mHeight/2-(int)(mHeight/2*0.6),mTextPaint);

        canvas.restore();

    }



    private void drawHand(Canvas canvas){
        //hour hand
        canvas.save();
        int hourHandwidth = mHourHand.getIntrinsicWidth() ;
        //int hourhandHeight = mHourHand.getIntrinsicHeight() ;
        canvas.rotate(mHourRotateDegrees,mWidth/2,mHeight/2);
        mHourHand.setBounds(mWidth/2-hourHandwidth/2 ,(int)(mHeight/2-mHeight/2*0.8),
                mWidth/2+hourHandwidth/2,(int)(mHeight / 2 +  mHeight/2f*0.25f)) ;
        //canvas.scale(mScale,mScale,mWidth/2,mHeight/2);
        mHourHand.draw(canvas);
        canvas.restore();


        //minute hand
        canvas.save();
        int minuteHandWidth = mMinHand.getIntrinsicWidth() ;
        //int minuteHandHeight = mMinHand.getIntrinsicHeight();
        canvas.rotate(mMinuRotateDegrees,mWidth/2,mHeight/2);
        canvas.scale(mScale,mScale,mWidth/2,mHeight/2);
        mMinHand.setBounds(mWidth/2 -minuteHandWidth/2,(int)(mHeight/2-mHeight/2*0.98),
                mWidth/2+minuteHandWidth/2,(int)(mHeight/2+mHeight/2 *0.15f));
        mMinHand.draw(canvas);
        canvas.restore();




        //second  hand
        canvas.save();
        int secondHandWidth = mSecondHand.getIntrinsicWidth() ;
        //int secondHandHeight = mSecondHand.getIntrinsicHeight();
        canvas.rotate(mSecondRotateDegrees,mWidth/2,mHeight/2);
        canvas.scale(mScale,mScale,mWidth/2,mHeight/2);
        mSecondHand.setBounds(mWidth/2-secondHandWidth/2,(int)(mHeight/2-mHeight/2*0.99f),
                mWidth/2+secondHandWidth/2,(int)(mHeight/2+mHeight/2*0.3f));
        mSecondHand.draw(canvas);
        canvas.restore();

        //middle circle
        canvas.save();
        int centerCircleWidth = mWatchBlackPoint.getIntrinsicWidth();
        int centerCircleHeight = mWatchBlackPoint.getIntrinsicHeight();
        canvas.scale(mScale,mScale,mWidth/2,mHeight/2);
        mWatchBlackPoint.setBounds(mWidth/2-centerCircleWidth/2,mHeight/2-centerCircleHeight/2,
                mWidth/2+centerCircleWidth/2,mHeight/2+centerCircleHeight/2);
        mWatchBlackPoint.draw(canvas);
        canvas.restore();
    }

    private void updateTimer() {

        mUpdateTimeHandler.removeMessages(HANDLER_EVENT_UPDATE_TIME);
        if (shouldTimerBeRunning()){
            mUpdateTimeHandler.sendEmptyMessage(HANDLER_EVENT_UPDATE_TIME);
        }

    }


    static class UpdateTimeHandler extends Handler{

        private WeakReference<CustomWatchFace> mCustomWactchFace ;

        public UpdateTimeHandler(CustomWatchFace watchFace){
            mCustomWactchFace = new WeakReference<CustomWatchFace>(watchFace);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomWatchFace watchFace = mCustomWactchFace.get();
            if (watchFace == null)return ;
            if (msg.what == HANDLER_EVENT_UPDATE_TIME) {
                watchFace.onTimeChanged() ;
                watchFace.invalidate();
                if (watchFace.shouldTimerBeRunning()){
                    long timeMs = System.currentTimeMillis();
                    long delayMs =INTERACTIVE_UPDATE_RATE_MS
                            - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
//                    Log.i(TAG,"delayMs = "+delayMs) ;
                    sendEmptyMessageDelayed(watchFace.HANDLER_EVENT_UPDATE_TIME,delayMs);

                }

            }
            super.handleMessage(msg);
        }
    }


    private boolean shouldTimerBeRunning() {
        return getVisibility()== View.VISIBLE;
    }

    private void onTimeChanged(){
        long now = System.currentTimeMillis();
        mCalendar.setTimeInMillis(now);
        mHours = mCalendar.get(Calendar.HOUR);
        mMinutes = mCalendar.get(Calendar.MINUTE);
        mSeconds = mCalendar.get(Calendar.SECOND);

        mHourRotateDegrees = (mHours+ mMinutes/60f+mSeconds/3600f)/12f*360f ;
        mMinuRotateDegrees = (mMinutes+mSeconds/60f)/60f*360f ;
        mSecondRotateDegrees = (mSeconds)/60f*360f ;

        mDay = String.valueOf(mCalendar.get(Calendar.MONTH)+1)+" "+String.valueOf(mCalendar.get(Calendar.DATE));
        mWeek = String.valueOf(getDayofWeek(mCalendar.get(Calendar.DAY_OF_WEEK))) ;

        mDate = mDay+"  "+mWeek;

//        Log.i(TAG,"hourdegree = "+mHourRotateDegrees+"  mMinuteDegreee = "+mMinuRotateDegrees+" mSecondDegree = "+mSecondRotateDegrees) ;

    }
    private String getDayofWeek(int weekday){
//        Log.i(TAG,"weekday = "+weekday) ;
        switch (weekday){
            case 1:
                mWeek = getContext().getString(R.string.sunday);
                break ;
            case 2:
                mWeek = getContext().getString(R.string.monday);
                break;
            case 3:
                mWeek = getContext().getString(R.string.tuesday);
                break;
            case 4:
                mWeek = getContext().getString(R.string.wednesday);
                break;
            case 5:
                mWeek = getContext().getString(R.string.thursday);
                break;
            case 6:
                mWeek = getContext().getString(R.string.friday);
                break;

            default:
                mWeek = getContext().getString(R.string.saturday);
                break;
        }
        return mWeek;

    }

    // index 0 : width  index 1 : height
    private int[] getScreenWidth() {
        int [] width_height = new int[2];
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics() ;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width_height[0] = displayMetrics.widthPixels ;
        width_height[1] = displayMetrics.heightPixels ;
        Log.i(TAG,"width = "+width_height[0]+" height = "+width_height[1]) ;
        return width_height ;
    }



}
