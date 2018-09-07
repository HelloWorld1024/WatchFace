package com.tnt.watchhome.Bean;

import android.graphics.drawable.Drawable;

public class WatchFaceInfo {

    String watchfaceName ;
    Drawable watchfaceIcon ;
    int id ;

    public WatchFaceInfo(String watchfaceName, Drawable watchfaceIcon) {
        this.watchfaceName = watchfaceName;
        this.watchfaceIcon = watchfaceIcon;
    }


    public int getId() {
        return id ;
    }
    public void setId(int id ){
        this.id = id ;
    }

    public String getWatchfaceName() {
        return watchfaceName;
    }

    public void setWatchfaceName(String watchfaceName) {
        this.watchfaceName = watchfaceName;
    }

    public Drawable getWatchfaceIcon() {
        return watchfaceIcon;
    }

    public void setWatchfaceIcon(Drawable watchfaceIcon) {
        this.watchfaceIcon = watchfaceIcon;
    }

    public String toString () {
        return watchfaceName ;
    }
}
