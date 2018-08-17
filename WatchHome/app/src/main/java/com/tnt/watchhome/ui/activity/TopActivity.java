package com.tnt.watchhome.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.tnt.watchhome.Constants.Constants;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.ViewPagerFragmentAdapter;
import com.tnt.watchhome.ui.fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

public class TopActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final String TAG = "TopActivity";


    private View mView ;
    private ViewPager mTopViewPager ;

    private TopFragment mTopCenterFragment ;
    private FragmentManager mFragmentManager ;
    private List<Fragment> mFragmentList ;
    private ViewPagerFragmentAdapter mAdapter ;

    private GestureDetector mGestureDetector ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (initView() != 0) {
            Log.i(TAG,"init failed") ;
            return  ;
        }
        initFragment() ;
        mGestureDetector = new GestureDetector(this,this) ;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"TopActivity onTouchEvent") ;
       // mGestureDetector.onTouchEvent(event) ;
       // mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    private void initFragment () {
        mFragmentManager = getSupportFragmentManager() ;
        mFragmentList = new ArrayList<>() ;
        mAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList) ;
        mTopCenterFragment = new TopFragment() ;
        mFragmentList.add(mTopCenterFragment) ;
        mTopViewPager.setAdapter(mAdapter);
        mTopViewPager.setCurrentItem(0);
    }

    private int initView () {
        mView = getLayoutInflater().inflate(R.layout.activity_top,null);
        if (null == mView) {
            return -1 ;
        }
        setContentView(R.layout.activity_top);
        mTopViewPager = mView.findViewById(R.id.top_viewpager) ;
        if (null == mTopViewPager) {
            return -1 ;
        }
        return 0 ;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float y1 ,y2   ;
        y1 = e1.getY() ;
        y2 = e2.getY() ;

        int direction = 0 ;

        if (y1 - y2 > Constants.SCROLL_DISTANCE) {
            Log.i(TAG,"up=========") ;
            direction = Constants.SCROOL_UP ;
            finish();

        }else if(y2 -y1 > Constants.SCROLL_DISTANCE) {
            Log.i(TAG,"down =========") ;
            direction = Constants.SCROOL_DOWN ;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
