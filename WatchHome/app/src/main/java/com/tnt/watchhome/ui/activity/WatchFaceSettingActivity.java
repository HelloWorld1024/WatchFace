package com.tnt.watchhome.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tnt.watchhome.Bean.WatchFaceInfo;
import com.tnt.watchhome.Constants.Constants;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.WatchFaceListAdapter;
import com.tnt.watchhome.ui.listener.ItemClickListener;

import java.util.ArrayList;

public class WatchFaceSettingActivity extends Activity {

    private static final String TAG = "watchfacesetting";

    private WatchFaceListAdapter mAdapter ;
    private ArrayList<WatchFaceInfo> mData ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_watchface_setting);

        if (-1 == init()) {
            Log.e(TAG,"init failed error ");
        }

    }


    private int init() {
        RecyclerView  WatchfaceThemeList = findViewById(R.id.watchface_list) ;
        if (null == WatchfaceThemeList) return  -1 ;
        mData = getWatchFaceinfo() ;
        if (null == mData) return -1 ;

        mAdapter = new WatchFaceListAdapter(mData, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG,"start to change watch face =======") ;
                WatchFaceInfo watchFaceInfo =mAdapter.getItem(position) ;
                if (null != watchFaceInfo){

                    Intent intent = new Intent() ;
                    intent.putExtra(Constants.WATCH_NAME,watchFaceInfo.getWatchfaceName());
                    intent.putExtra(Constants.WATCH_ID,watchFaceInfo.getId());
                    setResult(RESULT_OK,intent);

                    finish();
                }


            }
        }) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        WatchfaceThemeList.setLayoutManager(linearLayoutManager);
        WatchfaceThemeList.setAdapter(mAdapter);

        return 0 ;
    }

    private ArrayList<WatchFaceInfo> getWatchFaceinfo(){
        mData = new ArrayList<>() ;

        Drawable watchIcon  ;
        String watchName ;
        WatchFaceInfo watchFaceInfo ;
        watchIcon = getResources().getDrawable(R.drawable.icon_2,null);
        watchName = getResources().getString(R.string.watch_name_luxury);
        watchFaceInfo = new WatchFaceInfo(watchName,watchIcon);
        watchFaceInfo.setId(Constants.WATCH_FACE_TWO);
        mData.add(watchFaceInfo);

        watchIcon = getResources().getDrawable(R.drawable.icon_1,null);
        watchName = getResources().getString(R.string.watch_name_simple);
        watchFaceInfo = new WatchFaceInfo(watchName,watchIcon);
        watchFaceInfo.setId(Constants.WATHC_FACE_ONE);
        mData.add(watchFaceInfo);

        return mData ;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }



}
