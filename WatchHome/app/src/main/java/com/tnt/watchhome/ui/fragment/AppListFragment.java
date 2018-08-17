package com.tnt.watchhome.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnt.watchhome.Bean.AppItem;
import com.tnt.watchhome.R;
import com.tnt.watchhome.contorl.Controller;
import com.tnt.watchhome.ui.adapter.WearRecyclerViewAdapter;
import com.tnt.watchhome.ui.listener.CustomScrollingLayoutCallback;
import com.tnt.watchhome.widget.WearableLinearLayoutManager;
import com.tnt.watchhome.widget.WearableRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class AppListFragment extends Fragment {
    private static final String TAG = "watch";

    private View mView ;
    private WearableRecyclerView mWearableRecyclerView ;

    private Controller mController ;

    private WearRecyclerViewAdapter mAdapter ;

    private WearableLinearLayoutManager mWearLinearLayoutManager ;

    private static final List<AppItem> mAppItems= new ArrayList<AppItem>();
    static final int[] resImage={R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard
            ,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard,R.drawable.bg_watcherboard} ;

    private  static final int[] textTitle ={R.string.app_sport_name,R.string.app_phone_name,R.string.app_mms_name,R.string.app_music_name,R.string.app_setpcount_name,
            R.string.app_heartrate,R.string.app_sleep_name,R.string.app_map_name,R.string.app_weather_name,R.string.app_tools_name,R.string.app_settings_name};

    static {
        if (resImage.length == textTitle.length) {
            for (int i = 0 ; i < resImage.length;i++) {
                AppItem appItem = new AppItem(resImage[i],textTitle[i]);
                mAppItems.add(appItem);
            }
        }
    }
    private int initData() {
        Log.i(TAG,"image len = "+resImage.length + "text len ="+textTitle.length) ;
        mAdapter = new WearRecyclerViewAdapter(mAppItems,mController) ;
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
        if (null == mView || null == mWearLinearLayoutManager) {
            mView = inflater.inflate(R.layout.fragment_app_list, container, false);
            CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback(getActivity()) ;
            mWearLinearLayoutManager = new WearableLinearLayoutManager(getContext(),customScrollingLayoutCallback) ;
        }
        mWearableRecyclerView = mView.findViewById(R.id.wear_recyclerview) ;
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);
        mWearableRecyclerView.setLayoutManager(mWearLinearLayoutManager);
        mWearableRecyclerView.setAdapter(mAdapter);




        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);
        mWearableRecyclerView.setBezelFraction(0.5f);
        mWearableRecyclerView.setScrollDegreesPerScreen(90);

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mController = new Controller(context) ;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null ;
    }




}
