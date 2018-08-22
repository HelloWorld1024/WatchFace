package com.tnt.watchhome.ui.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnt.watchhome.Bean.AppInfo;
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


    private int initData() {
        mAdapter = new WearRecyclerViewAdapter(getAppInfos(),mController) ;
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

    public List<AppInfo> getAppInfos(){
        PackageManager pm = getActivity().getPackageManager();
        List<PackageInfo>  packgeInfos = pm.getInstalledPackages(PackageManager.MATCH_SYSTEM_ONLY);
        List<AppInfo>  appInfos = new ArrayList<AppInfo>();

    	/* 获取应用程序的名称，不是包名，而是清单文件中的labelname
			String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
			appInfo.setAppName(str_name);
    	 */
        for(PackageInfo packageInfo : packgeInfos){
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }




}
