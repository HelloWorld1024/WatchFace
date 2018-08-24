package com.tnt.watchhome.Bean;


import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {


    String label  ;
    String packageName ;
    Drawable icon;
    Intent intent;

    public AppInfo(String mAppName, String mAappPackageName, Drawable mDrawable) {
        this.label = mAppName;
        this.packageName = mAappPackageName;
        this.icon = mDrawable;
    }

    public AppInfo(String label, String packageName, Drawable icon, Intent intent){
        this.label = label ;
        this.packageName=packageName ;
        this.icon = icon ;
        this.intent = intent ;

    }
    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getAppName() {
        return this.label;
    }

    public void setAppName(String mAppName) {
        this.label = mAppName;
    }

    public String getappPackageName() {
        return this.packageName;
    }

    public void setAppPackageName(String mAappPackageName) {
        this.packageName = mAappPackageName;
    }

    public Drawable getDrawable() {
        return icon;
    }

    public void setDrawable(Drawable mAdrawable) {
        this.icon = mAdrawable;
    }

    public AppInfo(){


    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "mAappPackageName='" + packageName + '\'' +
                '}';
    }



}
