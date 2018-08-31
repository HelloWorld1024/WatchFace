package com.tnt.watchhome.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.tnt.watchhome.R;
import com.tnt.watchhome.ui.adapter.ViewPagerFragmentAdapter;
import com.tnt.watchhome.ui.view.CustomWatchFace;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchFragment extends Fragment  {

    private static final String TAG = "watchFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE= "arg_page";
    private static final String ARG_TITLE = "arg_title";

    private View mView ;
    private View mWatchContent ;

    private ViewPager mTopViewPager ;
    private ViewPagerIndicator mViewPagerIndicator ;

    private OnFragmentInteractionListener mListener;


    private QuickSettingsFragment mQuickSettingsFragment ;
    private TopMusicFragment mTopMusicFragment ;
    private TopNotificationFragment mTopNotificationFragment ;
    private List<Fragment> mTopFragmentsList = new ArrayList<>() ; ;

    private ViewPagerFragmentAdapter mFragmentAdapter ;

    private  LongClickListener mWatchFaceChangeListener ;

    private int mScrollDirection ;

    public WatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page Parameter 1.
     * @param page Parameter 2.
     * @return A new instance of fragment WatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WatchFragment newInstance(int page, String title) {
        WatchFragment fragment = new WatchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments() ;
        mFragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager(),mTopFragmentsList) ;
        mWatchFaceChangeListener = new LongClickListener() ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"onCreateView===") ;

            mView = inflater.inflate(R.layout.fragment_watch, container, false);
            mWatchContent = mView.findViewById(R.id.watchface_content) ;
            //mWatch.setFragment(this);
            mTopViewPager = mView.findViewById(R.id.top_viewpager) ;
            mViewPagerIndicator = mView.findViewById(R.id.view_pager_indicator) ;
            mTopViewPager.setAdapter(mFragmentAdapter);
            mTopViewPager.setCurrentItem(1);
            mViewPagerIndicator.setViewPager(mTopViewPager) ;

        return mView ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"onActivityCreated===") ;
        mWatchContent.setOnLongClickListener(mWatchFaceChangeListener);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach") ;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initFragments() {
        mQuickSettingsFragment = new QuickSettingsFragment() ;
        mTopMusicFragment = new TopMusicFragment() ;
        mTopNotificationFragment = new TopNotificationFragment() ;

        mTopFragmentsList.add(mTopMusicFragment) ;
        mTopFragmentsList.add(mQuickSettingsFragment) ;
        mTopFragmentsList.add(mTopNotificationFragment);

    }

    class LongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            Log.i(TAG,"change watch face "+v.toString()) ;

            return true;
        }

    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int gestureDirection);
    }


    public void setDirection(int direction) {
        mScrollDirection = direction ;
        notifyDirection() ;
    }
    public int getScrollDirection(){
        return mScrollDirection ;
    }
    public void notifyDirection() {
        if (null == mListener)return ;
        mListener.onFragmentInteraction(mScrollDirection);
    }


}
