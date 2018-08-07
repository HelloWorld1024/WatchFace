package com.tnt.watchhome.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.tnt.watchhome.R;

/**
 * TODO: document your custom view class.
 */
public class CustomWatchFace extends View {
    private static final  String TAG = "watchface";


    private Drawable mDial ;
    private Drawable mHourHand ;
    private Drawable mMinHand ;
    private Drawable mSecondHand ;
    private Drawable mWatchBlackPoint ;

    private Bitmap mBackgroundBitmap;

    private float mHours ;
    private float mMinutes ;
    private float mSeconds ;


    private int mWidth;
    private int mHeight;
    private float mCenterX;
    private float mCenterY;
    private float mScale = 1;
    private float mRadius ;


    private Paint mBackgroundPaint ;



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

        Log.i(TAG,"onMeasure") ;

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
        Log.i(TAG,"onSizeChanged w = "+w + "h = "+h + "oldw = "+oldw + "oldh ="+oldh) ;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"onDraw") ;
       /* int dialWidth = mDial.getIntrinsicWidth() ;
        int dialHeight = mDial.getIntrinsicHeight() ;
        if (mWidth < dialWidth || mHeight < dialHeight) {
            float scale = Math.min(mWidth/dialWidth,mHeight/dialHeight);
            canvas.save();
            canvas.scale(scale,scale,mWidth/2,mHeight/2);

        }
        Log.i(TAG,"onDraw width = "+mWidth + "dialwidth = "+dialWidth) ;
        mDial.setBounds(0,0,
                mWidth,mHeight);
        mDial.draw(canvas);
        canvas.save();*/


        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mBackgroundPaint);

       // canvas.rotate(mHour / 12.0F * 360.0F, mWidth/2, mHeight/2);

        int hourHandwidth = mHourHand.getIntrinsicWidth() ;
        int hourhandHeight = mHourHand.getIntrinsicHeight() ;
        mHourHand.setBounds(mWidth/2 -hourHandwidth/2,mHeight/2-hourhandHeight/2,
                mWidth/2+hourHandwidth/2,mHeight/2+hourhandHeight/2) ;
        mHourHand.draw(canvas);
        canvas.restore() ;




        




    }

    private void drawBackground() {

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
