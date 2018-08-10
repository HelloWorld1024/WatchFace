package com.tnt.watchhome.Bean;

import android.widget.ImageView;
import android.widget.TextView;

public class AppItem {
    ImageView image ;
    TextView appTitle ;

    int imageId ;
    int appTitleId ;

    public void setImage(ImageView image ){
        this.image = image ;
    }
    public void setImageId (int imageId) {
        this.imageId = imageId ;
    }

    public void setAppTitle(TextView tv){
        this.appTitle = tv ;
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
