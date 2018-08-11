package com.tnt.watchhome.Bean;


public class AppItem {

    int imageId ;
    int appTitleId ;

    public AppItem(){

    }

    public AppItem(int imageId,int titleId){
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
