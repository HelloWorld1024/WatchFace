package com.tnt.watchhome.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import java.util.Collections;
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

    private WearRecyclerViewAdapter mAdapter ;

    private WearableLinearLayoutManager mWearLinearLayoutManager ;

    private List<AppInfo> mListAppData ;


    private int initData() {
        mListAppData = getAppInfos();
        mAdapter = new WearRecyclerViewAdapter(mListAppData, new WearRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(mListAppData.get(position).getIntent());
            }
        }) ;
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<AppInfo> getAllAppInfos(){
        PackageManager pm = getActivity().getPackageManager();
        List<PackageInfo>  packgeInfos = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        List<AppInfo>  appInfos = new ArrayList<>();

        for(PackageInfo packageInfo : packgeInfos){
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,drawable);
            appInfos.add(appInfo);
            Log.i(TAG,"appName = "+appName +"  packageName = "+packageName) ;
        }
        return appInfos;
    }

    public List<AppInfo> getAppInfos() {
        PackageManager pm  = getActivity().getPackageManager() ;
        List<AppInfo> appInfos = new ArrayList<>();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER) ;
        List<ResolveInfo> resolvInfos = pm.queryIntentActivities(intent,PackageManager.MATCH_ALL) ;

        String activityName,appName ,packageName ;
        Drawable drawable ;
        AppInfo appInfo ;
        Intent  launchIntent ;

        if (appInfos.size()>0) {
            appInfos.clear();
        }
        Collections.sort(resolvInfos,new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo resolveInfo : resolvInfos) {

            activityName = resolveInfo.activityInfo.name ;
            appName = resolveInfo.loadLabel(pm).toString();
            packageName = resolveInfo.activityInfo.packageName ;
            drawable = resolveInfo.loadIcon(pm) ;


            launchIntent = new Intent() ;
            launchIntent.setComponent(new ComponentName(packageName,
                    activityName));
            appInfo = new AppInfo(appName,packageName,drawable,launchIntent);
            Log.i(TAG,"appName = "+appName +"  packageName = "+packageName +" activity = "+activityName) ;
            appInfos.add(appInfo);
        }
        return appInfos ;
    }




}
