package com.tnt.watchhome.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.tnt.watchhome.Constants.Constants;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.ViewPagerFragmentAdapter;
import com.tnt.watchhome.ui.fragment.AppListFragment;
import com.tnt.watchhome.ui.fragment.VoiceAssistantFragment;
import com.tnt.watchhome.ui.fragment.WatchFragment;
import com.tnt.watchhome.widget.HomeDrawerLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WatchFragment.OnFragmentInteractionListener {

    private static final String TAG = "watchface";


    private ViewPager mViewPager ;
    private AppListFragment mAppListFragment ;
    private WatchFragment mWatchFragment ;


    private FragmentManager mFragmentManager ;
    private ViewPagerFragmentAdapter mPagerAdapter ;
    private VoiceAssistantFragment mVoiceAssistanFragment;
    private UpdateHandler mUpdateHandler ;
    private List<Fragment> mFragmentList = new ArrayList<>() ;


    static class UpdateHandler extends Handler {
        private WeakReference<MainActivity> activity ;

        private UpdateHandler(MainActivity mainActivity){
            activity = new WeakReference<>(mainActivity) ;
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity =activity.get() ;
            if (null == mainActivity) return ;
            mainActivity.updatePage(msg.what) ;

            super.handleMessage(msg);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initFragments() ;
        initViewPager();
        mUpdateHandler = new UpdateHandler(this) ;

    }
    private void initView () {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_main);

    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.main_viewpager);
        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1);
        //Animation animation = AnimationUtils.loadAnimation(this,R.anim.heads_up_enter) ;
        //mViewPager.setAnimation(animation);
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
    public void onFragmentInteraction(int gestureDirection) {
        Log.i(TAG,"onFragmentInteraction = watch interaction"+gestureDirection) ;

        mUpdateHandler.sendEmptyMessage(gestureDirection) ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"MainActivity onTouchEvent event "+event.toString()) ;
        return super.onTouchEvent(event);
    }

    public List<Fragment> getFragmentList(){
        return mFragmentList ;
    }


    public void updatePage(int state){
        if (state == Constants.SCROOL_DOWN) {
            //Intent inent = new Intent(this,TopActivity.class) ;
           // startActivity(inent);
            Log.i(TAG,"SCROOL_DOWN activity ====") ;
        }else if (state == Constants.SCROOL_UP) {
            Log.i(TAG,"====") ;
        }
    }


}
