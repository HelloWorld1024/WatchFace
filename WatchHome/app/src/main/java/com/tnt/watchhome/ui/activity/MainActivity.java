package com.tnt.watchhome.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.ViewPagerFragmentAdapter;
import com.tnt.watchhome.ui.fragment.AppListFragment;
import com.tnt.watchhome.ui.fragment.VoiceAssistantFragment;
import com.tnt.watchhome.ui.fragment.WatchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WatchFragment.OnFragmentInteractionListener {

    private static final String TAG = "watch";

    private ViewPager mViewPager ;
    private AppListFragment mAppListFragment ;
    private WatchFragment mWatchFragment ;

    private FragmentManager mFragmentManager ;
    private ViewPagerFragmentAdapter mPagerAdapter ;
    private VoiceAssistantFragment mVoiceAssistanFragment;


    private List<Fragment> mFragmentList = new ArrayList<Fragment>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initFragments() ;
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.heads_up_enter) ;
        mViewPager.setAnimation(animation);
    }

    private int initFragments() {
        mVoiceAssistanFragment = new VoiceAssistantFragment();
        mWatchFragment = new WatchFragment();
        mAppListFragment = new AppListFragment();
        mFragmentList.add(mVoiceAssistanFragment);
        mFragmentList.add(mWatchFragment);
        mFragmentList.add(mAppListFragment);

        return  0  ;
    }




    @Override
    public void onFragmentInteraction(Bundle bundle) {
        Log.i(TAG,"onFragmentInteraction = watch interaction") ;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG,"event "+event.toString()) ;
        return super.onTouchEvent(event);
    }
}
