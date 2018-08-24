package com.tnt.watchhome.contorl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Controller {

    private  static final String TAG = "Controller" ;
    private  Controller mController  ;
    private Context mContext ;

    public Controller (Context context) {
        mContext = context ;
    }


    public void itemSelected(Intent intent) {
        Log.i(TAG," item selected ") ;
        if (null == mContext) return ;
        mContext.startActivity(intent);
    }


}
