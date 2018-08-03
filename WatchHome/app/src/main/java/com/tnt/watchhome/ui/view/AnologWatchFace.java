package com.tnt.watchhome.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.support.v7.graphics.Palette;
import android.view.WindowManager;

import com.tnt.watchhome.R;

import java.util.Calendar;


/**
 * TODO: document your custom view class.
 */
public class AnologWatchFace extends View {
    private static final String TAG = "watchface";

    private static final float CENTER_GAP_AND_CIRCLE_RADIUS = 4f;

    private static final int SHADOW_RADIUS = 6;

    private static final double ROUND = 2 * Math.PI ;
    private static final double QUARTER = Math.PI/4 ;

    private float mCenterX;
    private float mCenterY;
    private float mSecondHandLength;
    private float mMinuteHandLength;
    private float mHourHandLength;

    private float mHourDegreeLength ;
    private float mSecondDegreeLength ;


    private float mRadius ;


    private float mHourLineStrockWidth ;
    private float mMinLineStrockWidth ;
    private float mSecondLineStrockWidth ;


    private int mSize ;
    private int mWidth ;
    private int mHeight ;

    private int mBorderColor ;

    private int mBackgroundColor ;

    private int mTextColor;

    private int mTextSize ;

    private int mHourHandColor ;

    private int mMinHandColor ;

    private int mSecondHandColor ;



    /* Colors for all hands (hour, minute, seconds, ticks) based on photo loaded. */

    private int mWatchHandHighlightColor;
    private int mWatchHandShadowColor;


    private Calendar mCalendar ;
    private Paint mHourPaint;
    private Paint mMinutePaint;
    private Paint mSecondPaint;
    private Paint mTickAndCirclePaint;
    private Paint mBackgroundPaint;

    private Paint mTextPaint ;

    private Bitmap mBackgroundBitmap;
    private Bitmap mGrayBackgroundBitmap;

    private boolean mAmbient;
    private boolean mLowBitAmbient;
    private boolean mBurnInProtection;


    //default values (color  background)
    private static final int DEFAULT_BACKGOURN_COLOR = Color.BLACK ;


    public AnologWatchFace(Context context) {
        super(context);
        init(context,null, 0);
    }

    public AnologWatchFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public AnologWatchFace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }

    private void init(Context context , AttributeSet attrs, int defStyle) {
        mCalendar = Calendar.getInstance() ;
        initDefaultValue();
        initializeBackground() ;
        initializeWatchFace() ;

        initAttributeSet(context,attrs,defStyle,0);

    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
       // int newSpec = MeasureSpec.makeMeasureSpec(mSize,MeasureSpec.getMode(Math.min(widthMeasureSpec,heightMeasureSpec)));
        //Log.i(TAG,"onMessure  width = "+widthMeasureSpec + " height = "+heightMeasureSpec) ;
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

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
        calculateLengths();
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"test") ;

        /* google default
        final float seconds = (mCalendar.get(Calendar.SECOND) + mCalendar.get(Calendar.MILLISECOND) / 1000f);
        final float secondsRotation = seconds * 6f;
        final float minutesRotation = mCalendar.get(Calendar.MINUTE) * 6f;
        final float hourHandOffset = mCalendar.get(Calendar.MINUTE) / 2f;
        final float hoursRotation = (mCalendar.get(Calendar.HOUR) * 30) + hourHandOffset;
        */
        float millis = mCalendar.get(Calendar.MILLISECOND) / 1000f;
        float second = (mCalendar.get(Calendar.SECOND) + millis) / 60f;
        float minute = (mCalendar.get(Calendar.MINUTE) + second) / 60f;
        float hour = (mCalendar.get(Calendar.HOUR) + minute) / 12f;

        Log.i(TAG,"millis ="+millis +" second"+second+" minute = "+minute+"hour = "+hour) ;
        Log.i(TAG,"radiu"+mRadius +"") ;
        drawDegreeLine(canvas);
        drawHourNumbers(canvas);
        drawHand(canvas,mHourPaint,mHourHandLength,hour);
        drawHand(canvas,mMinutePaint,mMinuteHandLength,minute);
        drawHand(canvas,mSecondPaint,mSecondHandLength,second);

    }
    private void drawWatchCircle(){

    }

    private void drawDegreeLine(Canvas canvas) {
        int i ;
        canvas.translate(mWidth * 1.0f / 2 , mHeight * 1.0f / 2 );
        mTextPaint.setStrokeWidth(2f);
        for(i = 1 ; i < 13 ; i++){
            canvas.save() ;
            canvas.drawLine(0,mRadius,0,mRadius -mHourDegreeLength,mTextPaint);
            canvas.rotate(30);

        }
        mTextPaint.setStrokeWidth(1.0f);
        for (i =0 ; i < 60 ; i++){
            canvas.save();
            if(i % 5 !=0){
                canvas.drawLine(0,mRadius,0,mRadius -mSecondDegreeLength,mTextPaint);
            }
            canvas.rotate(6);
        }

    }

    private void drawHourNumbers(Canvas canvas){
        for (int i =0 ; i < 12 ; i++){
            canvas.save() ;
            String number = 6 + i < 12 ? String.valueOf(6+i):(6+i)>12 ? String.valueOf(i-6) : "12" ;
            canvas.translate(0,mRadius*5.5f/7);
            canvas.rotate(-i*30);
            canvas.drawText(number,0,0 ,mTextPaint);
            canvas.restore();
            canvas.rotate(30);

        }



    }



    //draw hand path .choose to use
    private int[] getPointerCoordinates(int pointerLength) {
        int y = (int) (pointerLength * 3.0f / 4);
        int x = (int) (y * Math.tan(Math.PI / 180 * 5));
        return new int[]{-x, y, 0, pointerLength, x, y};
    }

    private void drawHand(Canvas canvas,Paint paint
                          ,float lenth,float round){
        double radians = (round - QUARTER ) * ROUND ;
        canvas.drawLine(0, 0,
                (float)Math.cos(radians)*lenth,
                (float)Math.sin(radians)*lenth,
                paint);
    }



    private void drawHandGoogleDefault(Canvas canvas,float rotation,
                                       float handLength,Paint paint){
        canvas.save() ;
        canvas.rotate(rotation,mCenterX,mCenterY);
        canvas.drawLine(mCenterX,mCenterY - CENTER_GAP_AND_CIRCLE_RADIUS,
                mCenterX,
                mCenterY -handLength,
                paint);


    }

    //test later
    private void drawCircleTick(Canvas canvas){
        canvas.save() ;
        canvas.drawCircle(
                mCenterX,
                mCenterY,
                CENTER_GAP_AND_CIRCLE_RADIUS,
                mTickAndCirclePaint);

    }




    private void initDefaultValue() {
        mAmbient = false ;
        mBorderColor = Color.BLACK;
        mBackgroundColor = Color.GRAY ;
        mSecondHandColor = Color.RED ;
        mMinHandColor = Color.BLACK;
        mHourHandColor = Color.BLACK ;
        mTextColor = Color.BLACK ;
        mTextSize = 22 ;
        mHourLineStrockWidth = 5f ;
        mMinLineStrockWidth = 3f ;
        mSecondLineStrockWidth = 2f ;

        mWidth = getWidth() ;
        mHeight = getHeight() ;
        mRadius = mWidth /2 ;

        mCenterX = getWidth() /2 ;
        mCenterY = getHeight() / 2;

        Log.i(TAG,"radius = "+mRadius) ;

        mWidth = getScreenWidth()[0] ;
        mHeight = getScreenWidth()[1] ;
        mCenterX = mWidth/2  ;
        mCenterY = mHeight /2 ;
        mSize = mWidth ;
        mRadius = mWidth /2 ;
        mRadius = 100 ;

        Log.i(TAG,"radius = "+mRadius +"width = "+mWidth) ;



    }
    private void initAttributeSet(Context context,AttributeSet attrs,int defStyle,int defStyleRes){
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.AnologWatchFace,defStyle,defStyleRes);

        mSize = typedArray.getDimensionPixelSize(R.styleable.AnologWatchFace_viewSize,mSize) ;
        mBorderColor = typedArray.getColor(R.styleable.AnologWatchFace_borderColor,mBorderColor);
        mBackgroundColor = typedArray.getColor(R.styleable.AnologWatchFace_backgroundColor,mBackgroundColor);
        mSecondHandColor = typedArray.getColor(R.styleable.AnologWatchFace_secondlineColor,mSecondHandColor);
        mMinHandColor = typedArray.getColor(R.styleable.AnologWatchFace_minutelineColor,mMinHandColor);
        mHourHandColor = typedArray.getColor(R.styleable.AnologWatchFace_hourlineColor,mHourHandColor) ;

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.AnologWatchFace_textSize,mTextSize) ;
        mTextColor = typedArray.getColor(R.styleable.AnologWatchFace_textColor,mTextColor) ;
        mHourLineStrockWidth = typedArray.getDimensionPixelSize(R.styleable.AnologWatchFace_hourlinePaintStrockwidth,(int)mHourLineStrockWidth);
        mMinLineStrockWidth = typedArray.getDimensionPixelSize(R.styleable.AnologWatchFace_minutelinePaintStrockwidth,(int)mMinLineStrockWidth) ;
        mSecondLineStrockWidth = typedArray.getDimensionPixelSize(R.styleable.AnologWatchFace_secondlinePaintStrockwidth,(int)mSecondLineStrockWidth);
        typedArray.recycle();
    }

    private void initializeBackground() {
        mBackgroundPaint = new Paint() ;
        mBackgroundPaint.setColor(DEFAULT_BACKGOURN_COLOR);
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg_watcherboard) ;

        /*Extracts colors from background image to improve watchface style*/
        Palette.from(mBackgroundBitmap).generate(new Palette.PaletteAsyncListener(){

            @Override
            public void onGenerated(Palette palette) {
                if(null != palette){
                    mWatchHandHighlightColor = palette.getVibrantColor(Color.RED) ;
                    mHourHandColor = palette.getLightVibrantColor(Color.WHITE) ;
                    mWatchHandShadowColor = palette.getDarkMutedColor(Color.BLACK) ;
                    updateWatchHandStyle() ;
                }
            }
        });
    }

    private void initializeWatchFace(){
        /* Set defaults for colors */
        mWatchHandHighlightColor = Color.RED;
        mWatchHandShadowColor = Color.BLACK;

        mHourPaint = new Paint();
        mHourPaint.setColor(mHourHandColor);
        mHourPaint.setStrokeWidth(mHourLineStrockWidth);
        mHourPaint.setAntiAlias(true);
        mHourPaint.setStrokeCap(Paint.Cap.ROUND);
        mHourPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);

        mMinutePaint = new Paint();
        mMinutePaint.setColor(mMinHandColor);
        mMinutePaint.setStrokeWidth(mMinLineStrockWidth);
        mMinutePaint.setAntiAlias(true);
        mMinutePaint.setStrokeCap(Paint.Cap.ROUND);
        mMinutePaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);

        mSecondPaint = new Paint();
        mSecondPaint.setColor(mWatchHandHighlightColor);
        mSecondPaint.setStrokeWidth(mSecondLineStrockWidth);
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecondPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);

        mTickAndCirclePaint = new Paint();
        mTickAndCirclePaint.setColor(mMinHandColor);
        mTickAndCirclePaint.setStrokeWidth(mHourLineStrockWidth);
        mTickAndCirclePaint.setAntiAlias(true);
        mTickAndCirclePaint.setStyle(Paint.Style.STROKE);
        mTickAndCirclePaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);

        mTextPaint = new Paint() ;
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(2);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);

    }

    private void updateWatchHandStyle() {
        if (mAmbient) {
            mHourPaint.setColor(Color.WHITE);
            mMinutePaint.setColor(Color.WHITE);
            mSecondPaint.setColor(Color.WHITE);
            mTickAndCirclePaint.setColor(Color.WHITE);

            mHourPaint.setAntiAlias(false);
            mMinutePaint.setAntiAlias(false);
            mSecondPaint.setAntiAlias(false);
            mTickAndCirclePaint.setAntiAlias(false);

            mHourPaint.clearShadowLayer();
            mMinutePaint.clearShadowLayer();
            mSecondPaint.clearShadowLayer();
            mTickAndCirclePaint.clearShadowLayer();

        } else {
            mHourPaint.setColor(mHourHandColor);
            mMinutePaint.setColor(mMinHandColor);
            mSecondPaint.setColor(mWatchHandHighlightColor);
            mTickAndCirclePaint.setColor(mMinHandColor);// tick 使用跟minuteHand 同款颜色

            mHourPaint.setAntiAlias(true);
            mMinutePaint.setAntiAlias(true);
            mSecondPaint.setAntiAlias(true);
            mTickAndCirclePaint.setAntiAlias(true);

            mHourPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);
            mMinutePaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);
            mSecondPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);
            mTickAndCirclePaint.setShadowLayer(SHADOW_RADIUS, 0, 0, mWatchHandShadowColor);
        }
    }

    private void calculateLengths() {
        //设置时针长度为半径的1/7
        mHourDegreeLength =  (int)(mRadius * 1.0f / 7);
        // 秒分刻度长度为时刻度长度的一半
        mSecondDegreeLength = (int)(mHourDegreeLength * 1.0f / 2);

        //设置指针的长度
        mHourHandLength =  (int) (mRadius * 1.0 / 2);
        mMinuteHandLength =  mHourHandLength * 1.25f;
        mSecondHandLength =  mHourHandLength * 1.5f;
    }




}
