package com.tnt.watchhome.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tnt.watchhome.Bean.AppItem;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.ApplistAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class AppListFragment extends Fragment {
    private static final String TAG = "watch";

    private ListView mApplist ;
    private View mView ;

    private static final List<AppItem> mAppItems= new ArrayList<AppItem>();


    private int initData() {
        int resImage[]={R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard
        ,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard} ;


        int textTitle[] ={R.string.app_sport_name,R.string.app_phone_name,R.string.app_mms_name,R.string.app_music_name,R.string.app_setpcount_name,
        R.string.app_heartrate,R.string.app_sleep_name,R.string.app_map_name,R.string.app_weather_name,R.string.app_tools_name,R.string.app_settings_name};

        Log.i(TAG,"image len = "+resImage.length + "text len ="+textTitle.length) ;

        if (resImage.length == textTitle.length) {
            for (int i = 0 ; i < resImage.length;i++) {
                AppItem appItem = new AppItem(resImage[i],textTitle[i]);
                mAppItems.add(appItem);
            }
        }
        return 0 ;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AppListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData() ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.fragment_app_list, container, false);
        }
        mApplist = mView.findViewById(R.id.list_item) ;
        mApplist.setAdapter(new ApplistAdapter(mAppItems));


        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
