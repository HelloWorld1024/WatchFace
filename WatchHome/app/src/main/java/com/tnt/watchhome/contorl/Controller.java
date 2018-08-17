package com.tnt.watchhome.contorl;

import android.content.Context;
import android.util.Log;

public class Controller {

    private  static final String TAG = "Controller" ;
    private  Controller mController  ;
    private Context mContext ;

    public Controller (Context context) {
        mContext = context ;
    }


    public void itemSelected(Object obj) {
        Log.i(TAG," item selected ") ;

    }


}
