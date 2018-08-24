package com.tnt.watchhome.ui.fragment;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tnt.watchhome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSettingsFragment extends Fragment {

    private static final String TAG = "QuickSettingsFragment";

    private static final int MUTE_MODE = AudioManager.RINGER_MODE_SILENT ;

    private Context mContext ;

    private Button mAirPlaneMode ;
    private Button mMuteMode ;
    private Button mWifiMode ;
    private Button mNotification ;
    private QuickSettingListener listener ;

    private int mAudioMode = AudioManager.RINGER_MODE_NORMAL ;

    private boolean mIsNotifyEnable  = true ;

    private boolean mIsAirplane = false ;




    private WifiManager mWifiManager ;
    private AudioManager mAudioManager ;


    public QuickSettingsFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.mContext = context ;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mContext) mContext = getActivity() ;
        listener = new QuickSettingListener() ;
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE) ;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        checkSelfPermission();
        observeWifiSwitch() ;


    }
    private void checkSelfPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE},
                    123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG,"requestCode = "+requestCode ) ;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view ;
        view = inflater.inflate(R.layout.fragment_quick_settings, container, false);
        if (null == view) return null;
        mAirPlaneMode = view.findViewById(R.id.airplane) ;
        mMuteMode = view.findViewById(R.id.mute) ;
        mWifiMode = view.findViewById(R.id.wifi) ;
        mNotification = view.findViewById(R.id.notification) ;
        if (null == listener || null == mAirPlaneMode || null == mMuteMode || null == mWifiMode || null == mNotification) return null ;

        mAirPlaneMode.setOnClickListener(listener);
        mMuteMode.setOnClickListener(listener);
        mWifiMode.setOnClickListener(listener);
        mNotification.setOnClickListener(listener);
        return view ;
    }

    @Override
    public void onStart() {
        Log.i(TAG,"onStart========") ;
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG,"onResume=========") ;
        initState();
        super.onResume();
    }

    private void initState() {
        mAudioMode = mAudioManager.getRingerMode() ;
        Log.i(TAG,"mAudioMode="+mAudioMode) ;
        if (mAudioMode == MUTE_MODE) {
            mMuteMode.setBackground(getResources().getDrawable(R.drawable.ic_mute_on,null));
        }else {
            mMuteMode.setBackground(getResources().getDrawable(R.drawable.ic_mute_off,null));
        }
    }

    class QuickSettingListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.airplane:
                    mIsAirplane = !mIsAirplane ;
                    refreshAirplane() ;
                    break ;
                case R.id.mute:
                    Log.i(TAG,"Audiomode = "+mAudioMode) ;
                    if (mAudioMode == MUTE_MODE) {
                        UnMuteAudio();
                        mAudioMode = AudioManager.RINGER_MODE_NORMAL;
                        refreshMute() ;
                        Log.i(TAG,"set mode to normal") ;
                    }else if (mAudioMode == AudioManager.RINGER_MODE_NORMAL){
                        MuteAudio();
                        mAudioMode = AudioManager.RINGER_MODE_SILENT ;
                        refreshMute();
                        Log.i(TAG,"set mode to silent") ;
                    }
                    break ;
                case R.id.wifi:
                    mWifiManager.setWifiEnabled(!mWifiManager.isWifiEnabled()) ;
                    break ;
                case R.id.notification:
                    mIsNotifyEnable = !mIsNotifyEnable ;
                    refreshNotification();
                    break ;
                    default:
                        break ;
            }
        }
    }
    public void MuteAudio(){
        if (null == mAudioManager) return ;

        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG,"set mute") ;
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        } else {
            mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_RING, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }*/
    }

    public void UnMuteAudio(){
        if (null == mAudioManager)return;

        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Log.i(TAG,"set unmute") ;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG,"set unmute") ;
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_RING, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }*/
    }

    public void refreshWifi() {
        Drawable wifiState = mWifiManager.isWifiEnabled()? getResources().getDrawable(R.drawable.ic_wifi_on,null):
                getResources().getDrawable(R.drawable.ic_wifi_off,null) ;
        mWifiMode.setBackground(wifiState);

    }
    public void refreshMute() {
        Drawable muteDrawable = mAudioMode == MUTE_MODE ? getResources().getDrawable(R.drawable.ic_mute_on,null):
                getResources().getDrawable(R.drawable.ic_mute_off,null) ;
        mMuteMode.setBackground(muteDrawable);
    }

    public void refreshNotification() {
        Drawable notificationDrawable = mIsNotifyEnable? getResources().getDrawable(R.drawable.ic_notification_on,null):
                getResources().getDrawable(R.drawable.ic_notification_off,null) ;
        mNotification.setBackground(notificationDrawable);
    }
    public void refreshAirplane() {
        Drawable airPlaneDrawable = mIsAirplane? getResources().getDrawable(R.drawable.ic_airplanemode_on,null):
                getResources().getDrawable(R.drawable.ic_airplanemode_off,null) ;
        mAirPlaneMode.setBackground(airPlaneDrawable);
    }


    private void observeWifiSwitch(){
        Receiver receiver ;
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        receiver = new Receiver() ;
        mContext.registerReceiver(receiver, filter);
    }

    class Receiver extends  BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState){
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.i(TAG,"wifi disabled") ;
                    refreshWifi() ;
                    break ;
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.i(TAG,"wifi enabled") ;
                    refreshWifi() ;
                    break ;
                    default:
                        break ;
            }
        }
    }

}
