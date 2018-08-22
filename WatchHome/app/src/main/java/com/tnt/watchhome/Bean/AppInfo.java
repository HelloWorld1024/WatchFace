package com.tnt.watchhome.Bean;


import android.graphics.drawable.Drawable;

public class AppInfo {

    int imageId ;
    int appTitleId ;



    String mAppName ;
    String mAappPackageName ;
    Drawable mAdrawable ;

    public AppInfo(String mAppName, String mAappPackageName, Drawable mAdrawable) {
        this.mAppName = mAppName;
        this.mAappPackageName = mAappPackageName;
        this.mAdrawable = mAdrawable;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public String getAappPackageName() {
        return mAappPackageName;
    }

    public void setAappPackageName(String mAappPackageName) {
        this.mAappPackageName = mAappPackageName;
    }

    public Drawable getAdrawable() {
        return mAdrawable;
    }

    public void setAdrawable(Drawable mAdrawable) {
        this.mAdrawable = mAdrawable;
    }

    public AppInfo(){


    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "mAappPackageName='" + mAappPackageName + '\'' +
                '}';
    }





    public AppInfo(int imageId, int titleId){
        this.imageId = imageId ;
        this.appTitleId = titleId ;
    }

    public void setImageId (int imageId) {
        this.imageId = imageId ;
    }

    public void setAppTitleId(int appTitleId){
        this.appTitleId = appTitleId ;
    }

    public int getImageId(){
        return imageId ;
    }

    public int getAppTitleId(){
        return appTitleId ;
    }





}
